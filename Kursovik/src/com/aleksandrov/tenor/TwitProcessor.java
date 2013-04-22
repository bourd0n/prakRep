package com.aleksandrov.tenor;

import com.rockymadden.stringmetric.similarity.RatcliffObershelpMetric;
import org.apache.commons.codec.language.DoubleMetaphone;
import pt.tumba.spell.Aspell;
import scala.Option;
import scala.Predef;

import java.io.*;
import java.util.*;

public class TwitProcessor {

    private static final double LEXICAL_SIMILARITY_LIMIT = 0.60;
//    private static final String PYTHON_SCRIPT = "/home/samsung/programs/prak/prakRep/Kursovik/src/test3.py";

    private static final String PYTHON_SCRIPT = "/home/samsung/programs/prak/prakRep/Kursovik/src/nltkanalyzer.py";

    private static final String PATH_TO_SLANG = "/home/samsung/programs/prak/prakRep/Kursovik/src/slang.txt";
    private static final String PATH_TO_ABBREVIATIONS = "/home/samsung/programs/prak/prakRep/Kursovik/src/abr.txt";

    private WordMap slangMap;
    private WordMap abrMap;

    public TwitProcessor() throws FileNotFoundException {
        slangMap = new WordMap(PATH_TO_SLANG, ":");
        abrMap = new WordMap(PATH_TO_ABBREVIATIONS, ",");
    }

    public String processTwit(String twit) throws IOException {
        String[] twitWords = twit.toLowerCase().split(" ");

        Aspell aspell = new Aspell();
        Collection<String> possibleVars = new ArrayList<String>();

        for (int j = 0; j < twitWords.length; j++) {
            StringBuilder sb = new StringBuilder();
            String[] aspellVariants = aspell.find(twitWords[j]);

            if (aspellVariants.length == 1 && aspellVariants[0].equals(twitWords[j])
                    && !twitWords[j].matches(".*\\d.*") && abrMap.find(twitWords[j].trim()) == null) {
                //ok - not OOV
                String slangAbr = slangMap.find(twitWords[j].toLowerCase().trim());

                if (slangAbr != null)
                    possibleVars.add(slangAbr + ",");
                else
                    possibleVars.add(twitWords[j] + ",");
            } else {
                //OOV
                possibleVars.add(processOOV(twitWords[j], aspellVariants));
            }


        }

        StringBuilder sb = new StringBuilder();
        for (String w : possibleVars){
            sb.append(w).append(";");
        }
//        return null;
//        String s = callPython(twit, possibleVars);
//        return s;
        return sb.toString();
    }

    private String callPython(String twit, Collection<String> possibleVars) throws IOException {
       System.out.println("--------");

        List<String> commands = new ArrayList<String>();
        commands.add("python");
        commands.add(PYTHON_SCRIPT);
        commands.add(twit);
        commands.addAll(possibleVars);
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        InputStream stdout = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

        String line;
        String line2 = "";
        while ((line = reader.readLine()) != null) {
            System.out.println("Stdout: " + line);
            line2 = line;
        }
        Date d = new Date();
        System.out.println("Result");
        System.out.println(d);
        System.out.println("line2 " + line2);

        return line2;
    }

    private String processOOV(String twitWord, String[] aspellVariants) {
        System.out.println(" Proccess OOV " + twitWord);

        Collection<String> possibleVariants = new ArrayList<String>();

        CommonTransform commonTransform = new CommonTransform();

        String slang = slangMap.find(twitWord.trim());

        if (slang != null) {
            System.out.println("    Slang " + slang);
            return slang + ",";
        }

        String abr = abrMap.find(twitWord.trim());

        if (abr != null) {
            System.out.println("    Abbr " + abr);
            possibleVariants.add(abr);
        }

        Collection<String> processingVariants = new ArrayList<String>();

        for (String s : aspellVariants) {
//            System.out.println("1 " + s);
            processingVariants.add(s);
        }

        //Common transformations like repeated symbols and numbers
        processingVariants.addAll(commonTransform.getCommonTransformations(twitWord));

        if (!twitWord.matches(".*\\d.*")) {
            DoubleMetaphone dm = new DoubleMetaphone();

            Iterator<String> it = processingVariants.iterator();

            System.out.println("-------- Source dm: " + dm.doubleMetaphone(twitWord));
            Iterator<String> it1 = processingVariants.iterator();
            while (it1.hasNext()) {
                String s = it1.next();
                System.out.println("    " + s);
            }


            while (it.hasNext()) {
                String s = it.next();
                System.out.print(" " + s + " " + dm.doubleMetaphone(s, true));
                if (!dm.isDoubleMetaphoneEqual(s, twitWord, true))
                    it.remove();
            }

//            System.out.println("-");
            it = processingVariants.iterator();

            System.out.println("-----");
            it1 = processingVariants.iterator();
            while (it1.hasNext()) {
                String s = it1.next();
                System.out.println("    " + s);
            }

            RatcliffObershelpMetric rm = RatcliffObershelpMetric.apply();
            Predef.DummyImplicit di = new Predef.DummyImplicit();

            it = processingVariants.iterator();

            while (it.hasNext()) {
                String s = it.next();
                Option<Object> some = rm.compare(twitWord, s, di);
//                System.out.println("* " + s + " sim " + some.get());
                if ((Double) some.get() < LEXICAL_SIMILARITY_LIMIT)
                    it.remove();
            }

            it1 = processingVariants.iterator();
            System.out.println("---");
            while (it1.hasNext()) {
                String s = it1.next();
                System.out.println("    " + s);
            }

        }
//        System.out.println("---");
        StringBuilder sb1 = new StringBuilder();
        possibleVariants.addAll(processingVariants);
        Iterator<String> it = possibleVariants.iterator();
        while (it.hasNext()) {
            String s = it.next();
//            System.out.println(s);
            sb1.append(s).append(",");

        }

        String s = sb1.toString();
        System.out.println("        Res " + s);
        return sb1.toString();
    }


    public String callPythonForFiles(String twitsFile, String varsFile, String outFile) throws IOException {
        System.out.println("--------");

        List<String> commands = new ArrayList<String>();
        commands.add("python");
        commands.add(PYTHON_SCRIPT);
        commands.add(twitsFile);
        commands.add(varsFile);
        commands.add(outFile);
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        InputStream stdout = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

        String line;
        String line2 = "";
        while ((line = reader.readLine()) != null) {
            System.out.println("Stdout: " + line);
            line2 = line;
        }
        Date d = new Date();
        System.out.println("Result");
        System.out.println(d);
        System.out.println("line2 " + line2);

        return line2;
    }
}
