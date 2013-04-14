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
    private static final String PYTHON_SCRIPT = "/home/samsung/programs/prak/prakRep/Kursovik/src/test3.py";

    private SlangMap slangMap;

    public TwitProcessor() throws FileNotFoundException {
        slangMap = new SlangMap();
    }

    public String processTwit(String twit) throws IOException {
        String[] twitWords = twit.toLowerCase().split(" ");

        Aspell aspell = new Aspell();

        //int i = 0;
        Collection<String> possibleVars = new ArrayList<String>();

        for (int j = 0; j < twitWords.length; j++) {
            StringBuilder sb = new StringBuilder();
            String[] aspellVariants = aspell.find(twitWords[j]);

            if (aspellVariants.length == 1 && aspellVariants[0].equals(twitWords[j])
                    && !twitWords[j].matches(".*\\d.*")) {
                //ok - not OOV
                String slangAbr = slangMap.find(twitWords[j].toLowerCase().trim());

                if (slangAbr != null)
                    possibleVars.add(slangAbr + ",");
                else
                    possibleVars.add(twitWords[j] + ",");
//                possibleVars.add(";");
            } else {
                //OOV
                possibleVars.add(processOOV(twitWords[j], aspellVariants));
//                possibleVars.add(";");
            }
        }

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

        Collection<String> possibleVariants = new ArrayList<String>();

        CommonTransform commonTransform = new CommonTransform();

        String slangAbr = slangMap.find(twitWord.trim());

        if (slangAbr != null) {
            return slangAbr + ",";
        }

        for (String s : aspellVariants) {
//            System.out.println("1 " + s);
            possibleVariants.add(s);
        }

        //Common transformations like repeated symbols and numbers
        possibleVariants.addAll(commonTransform.getCommonTransformations(twitWord));

        if (!twitWord.matches(".*\\d.*")) {
            DoubleMetaphone dm = new DoubleMetaphone();
            String sourceDM = dm.doubleMetaphone(twitWord);


            Iterator<String> it = possibleVariants.iterator();

            while (it.hasNext()) {
                String s = it.next();
//                System.out.println(s);
                if (!dm.isDoubleMetaphoneEqual(s, twitWord))
                    it.remove();
            }

//            System.out.println("-");
            it = possibleVariants.iterator();

            while (it.hasNext()) {
                String s = it.next();
//                System.out.println(s);
            }

            RatcliffObershelpMetric rm = RatcliffObershelpMetric.apply();
            Predef.DummyImplicit di = new Predef.DummyImplicit();

            it = possibleVariants.iterator();

            while (it.hasNext()) {
                String s = it.next();
                Option<Object> some = rm.compare(twitWord, s, di);
//                System.out.println("* " + s + " sim " + some.get());
                if ((Double) some.get() < LEXICAL_SIMILARITY_LIMIT)
                    it.remove();
            }

        }
//        System.out.println("---");
        StringBuilder sb1 = new StringBuilder();
        Iterator<String> it = possibleVariants.iterator();
        while (it.hasNext()) {
            String s = it.next();
//            System.out.println(s);
            sb1.append(s).append(",");

        }

        return sb1.toString();
    }
}
