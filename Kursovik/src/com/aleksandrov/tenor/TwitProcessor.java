package com.aleksandrov.tenor;

import com.rockymadden.stringmetric.similarity.RatcliffObershelpMetric;
import org.apache.commons.codec.language.DoubleMetaphone;
import pt.tumba.spell.Aspell;
import scala.Option;
import scala.Predef;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwitProcessor {

    private static final double LEXICAL_SIMILARITY_LIMIT = 0.60;
//    private static final String PYTHON_SCRIPT = "/home/samsung/programs/prak/prakRep/Kursovik/src/test3.py";

    private static final String PYTHON_SCRIPT = "/home/samsung/programs/prak/prakRep/Kursovik/src/nltkanalyzer.py";

    public static final String PATH_TO_SLANG = "/home/samsung/programs/prak/prakRep/Kursovik/src/slang.txt";
    public static final String PATH_TO_ABBREVIATIONS = "/home/samsung/programs/prak/prakRep/Kursovik/src/abr.txt";

    private WordMap slangMap;
    private WordMap abrMap;

    public TwitProcessor(boolean withExceptions) throws FileNotFoundException {
        if (withExceptions) {
            slangMap = new WordMap(PATH_TO_SLANG, ":");
            abrMap = new WordMap(PATH_TO_ABBREVIATIONS, ",");
        } else {
            slangMap = new WordMap();
            abrMap = new WordMap();
        }
    }

    public String processTwit(String twit) throws IOException {
        //delete urls
        Pattern p = Pattern.compile("(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?");
        Matcher m = p.matcher(twit);
        m.replaceAll("");

        //delete punctuation
        twit = twit.replaceAll("[\\p{Punct}&&[^\\-@#_']]", " ");

        String[] twitWords = twit.toLowerCase().split(" ");

        twitWords = preprocess(twitWords);
        Aspell aspell = new Aspell();
        Collection<String> possibleVars = new ArrayList<String>();
        String type = "";
        for (int j = 0; j < twitWords.length; j++) {
            if (twitWords[j] != null && !twitWords[j].isEmpty()) {

                System.out.println(twitWords[j]);
                //            if (!twitWords[j].matches("\\p{Punct}")) {
                if (twitWords[j].startsWith("@") || twitWords[j].startsWith("#")) {
                    type = "NO";
                    possibleVars.add(twitWords[j] + "*" + type);
                } else {
                    String[] aspellVariants = null;
//                        if (!twitWords[j].matches(".*\\d.*"))
                    if (!twitWords[j].matches(".*\\d+.*") && !twitWords[j].matches("\\p{Punct}")) {
                        System.out.println("Aspell find");
                        aspellVariants = aspell.find(twitWords[j]);
                    }

                    System.out.println("twitWords[j] " + twitWords[j] + " Array" + Arrays.toString(aspellVariants) + " abrSize " + (abrMap.find(twitWords[j].trim()) == null ? "null" : abrMap.find(twitWords[j].trim()).size()));

                    if (!twitWords[j].matches(".*\\d+.*") //&& abrMap.find(twitWords[j].trim()) == null
                            && aspellVariants != null && aspellVariants.length == 1 && aspellVariants[0].equals(twitWords[j])) {
                        //ok - not OOV
                        ArrayList<String> slangAbr = slangMap.find(twitWords[j].toLowerCase().trim());

                        ArrayList<String> abrs = abrMap.find(twitWords[j].toLowerCase().trim());

                        if (slangAbr != null && !slangAbr.isEmpty()) {
                            type = "OOV";
                            StringBuilder sb = new StringBuilder();
                            for (String s : slangAbr) {
                                sb.append(s).append("*");
                            }
//                                possibleVars.add(slangAbr + "*" + type);
                            sb.append(type);
                            possibleVars.add(sb.toString());
                        } else if (abrs != null && !abrs.isEmpty()) {
                            type = "OOV";
                            StringBuilder sb = new StringBuilder();
                            for (String s : abrs) {
                                sb.append(s).append("*");
                            }
//                                possibleVars.add(slangAbr + "*" + type);
                            sb.append(type);
                            possibleVars.add(sb.toString());
                        } else {
                            type = "IV";
                            possibleVars.add(twitWords[j] + "*" + type);
                        }
                        //} else if (!twitWords) {
                    } else if (aspellVariants != null && aspellVariants.length >= 1 && twitWords[j].toLowerCase().equals(aspellVariants[0].toLowerCase())) {
                        type = "IV";
                        possibleVars.add(twitWords[j] + "*" + type);
                    } else if (twitWords[j].matches("\\d{2,}")) {
                        //number
                        type = "NO";
                        possibleVars.add(twitWords[j] + "*" + type);
                    } else if (!twitWords[j].matches("\\p{Punct}")) {
                        //OOV
                        type = "OOV";
                        System.out.println("processOOV");
                        possibleVars.add(processOOV(twitWords[j], aspellVariants) + type);
                    }

                    //                }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String w : possibleVars) {
//            sb.append(w).append("~");
            sb.append(w).append("~");
        }
//        return null;
//        String s = callPython(twit, possibleVars);
//        return s;
        return sb.toString();
    }

    private String[] preprocess(String[] twitWords) {
        List<String> list = Arrays.asList(twitWords);
        list.removeAll(new ArrayList<String>() {{
            add("*");
            add("~");
        }});

        return twitWords;
    }

    private String processOOV(String twitWord, String[] aspellVariants) {
        System.out.println(" Proccess OOV " + twitWord);

        Collection<String> possibleVariants = new ArrayList<String>();

        CommonTransform commonTransform = new CommonTransform();

        ArrayList<String> slang = slangMap.find(twitWord.trim());

        if (slang != null && !slang.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String slangWord : slang) {
                System.out.println("    Slang " + slangWord);
                sb.append(slangWord).append("*");
            }
            //return slang + "*";
            return sb.toString();
        }

        ArrayList<String> abr = abrMap.find(twitWord.trim());

        if (abr != null && !abr.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String abrWord : abr) {
                System.out.println("    Abbr " + abrWord);
                sb.append(abrWord).append("*");
            }
//            possibleVariants.add(abr);
            possibleVariants.add(sb.toString());
        }

        Collection<String> processingVariants = new ArrayList<String>();

        if (aspellVariants != null && aspellVariants.length >= 1)
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

        //add word self
        possibleVariants.add(twitWord);

        Iterator<String> it = possibleVariants.iterator();
        while (it.hasNext()) {
            String s = it.next();
//            System.out.println(s);
            sb1.append(s).append("*");

        }

        String s = sb1.toString();
        System.out.println("        Res " + s);
        return sb1.toString();
    }


    public String callPythonForFiles(String twitsFile, String varsFile, String outFile, String outTwitFile, boolean forTest) throws IOException {
        System.out.println("--------");

        List<String> commands = new ArrayList<String>();
        commands.add("python");
        commands.add(PYTHON_SCRIPT);
        commands.add(twitsFile);
        commands.add(varsFile);
        commands.add(outFile);
        commands.add(outTwitFile);
        commands.add(forTest ? "testMode" : "simpleMode");
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
