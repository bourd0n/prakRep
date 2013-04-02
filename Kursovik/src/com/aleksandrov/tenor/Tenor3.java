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

public class Tenor3 {

    public Collection<String> commonTransform(String word) {
        Collection<String> possibleTransformations = new ArrayList<String>();
        //repeated symbols
        Pattern p = Pattern.compile("(\\w)\\1+");
        Matcher m = p.matcher(word);
        while (m.find()) {
            String res = word.replaceFirst("(\\w)\\1+", m.group(1));
            System.out.println("After delete repeated " + res);
            possibleTransformations.add(res);
            res = word.replaceFirst("(\\w)\\1+", m.group(1));
            possibleTransformations.add(res);
            System.out.println("After delete repeated 2 " + res);
        }

        //replace numbers
        p = Pattern.compile("\\d");
        m = p.matcher(word);

        while (m.find()) {
            String finded = m.group();
            Integer num = new Integer(finded);
            int pos = word.indexOf(finded);
            //todo: case of several same digits
            //todo: 2 - to, 4 - for
            if (!finded.equals(word)) {
                //not single number
                //todo: think about if in end, begin or middle of the word
                //todo: think about change of replaceFirst (repeat number)
                switch (num) {
                    case 0:
                        possibleTransformations.add(word.replaceFirst(finded, "o"));
                        break;
                    case 1:
                        possibleTransformations.add(word.replaceFirst(finded, "one"));
                        possibleTransformations.add(word.replaceFirst(finded, "won"));
                        break;
                    case 2:
                        possibleTransformations.add(word.replaceFirst(finded, "to"));
                        possibleTransformations.add(word.replaceFirst(finded, "too"));
                        possibleTransformations.add(word.replaceFirst(finded, "two"));
                        break;
                    case 3:
                        possibleTransformations.add(word.replaceFirst(finded, "e"));
                        break;
                    case 4:
                        possibleTransformations.add(word.replaceFirst(finded, "for"));
                        possibleTransformations.add(word.replaceFirst(finded, "fore"));
                        possibleTransformations.add(word.replaceFirst(finded, "four"));
                        possibleTransformations.add(word.replaceFirst(finded, "a"));   //?
                        break;
                    case 5:
                        possibleTransformations.add(word.replaceFirst(finded, "s"));
                        break;
                    case 6:
                        possibleTransformations.add(word.replaceFirst(finded, "b"));
                        possibleTransformations.add(word.replaceFirst(finded, "g"));
                        break;
                    case 7:
                        possibleTransformations.add(word.replaceFirst(finded, "t")); //?
                        break;
                    case 8:
                        possibleTransformations.add(word.replaceFirst(finded, "ate"));
                        possibleTransformations.add(word.replaceFirst(finded, "ait"));
                        possibleTransformations.add(word.replaceFirst(finded, "eat"));
                        possibleTransformations.add(word.replaceFirst(finded, "eate"));
                        possibleTransformations.add(word.replaceFirst(finded, "ight"));
                        possibleTransformations.add(word.replaceFirst(finded, "aight"));
                        break;
                    case 9:
                        possibleTransformations.add(word.replaceFirst(finded, "g"));
                        break;
                }
            }
        }


        return possibleTransformations;
    }

    public static final double LEXICAL_SIMILARITY_LIMIT = 0.60;

    //todo: think about punctuation
    public static void main(String[] args) {
        Date d1 = new Date();
        System.out.println(d1);
        String twit = null;
        try {
            Scanner scanner = new Scanner(new File("/home/samsung/programs/prak/prakRep/Kursovik/src/test"));
            twit = scanner.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[] twitWords = twit.split(" ");

        Aspell aspell = null;
        try {
            aspell = new Aspell();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tenor3 tenor = new Tenor3();

        int i = 0;
        Collection<String> posibleVars = new ArrayList<String>();

        //for (String word : twitWords) {
        for (int j = 0; j < twitWords.length; j++) {
            StringBuilder sb = new StringBuilder();
            String[] aspellVariants = aspell.find(twitWords[j]);

            if (aspellVariants.length == 1 && aspellVariants[0].equals(twitWords[j])
                    && !twitWords[j].matches(".*\\d.*")) {
                //ok - not OOV
                //sb.append(twitWords[j] + " ");
                System.out.println("ok");
                posibleVars.add(twitWords[j]);
                i++;
            } else {
                //OOV
                Collection<String> possibleVariants = new ArrayList<String>();
                for (String s : aspellVariants) {
                    System.out.println("1 " + s);
                    possibleVariants.add(s);
                }

                //Common transformations like repeated symbols and numbers
                possibleVariants.addAll(tenor.commonTransform(twitWords[j]));

                if (!twitWords[j].matches(".*\\d.*")) {
                    DoubleMetaphone dm = new DoubleMetaphone();
                    String sourceDM = dm.doubleMetaphone(twitWords[j]);


                    Iterator<String> it = possibleVariants.iterator();

                    while (it.hasNext()) {
                        String s = it.next();
                        System.out.println(s);
//                        if (!dm.isDoubleMetaphoneEqual(s, sourceDM))
                        if (!dm.isDoubleMetaphoneEqual(s, twitWords[j]))
                            it.remove();
                    }

                    System.out.println("-");
                    it = possibleVariants.iterator();

                    while (it.hasNext()) {
                        String s = it.next();
                        System.out.println(s);
                    }

                    RatcliffObershelpMetric rm = RatcliffObershelpMetric.apply();
                    Predef.DummyImplicit di = new Predef.DummyImplicit();

                    it = possibleVariants.iterator();

                    while (it.hasNext()) {
                        String s = it.next();
                        Option<Object> some = rm.compare(twitWords[j], s, di);
                        System.out.println("* " + s + " sim " + some.get());
                        if ((Double) some.get() < LEXICAL_SIMILARITY_LIMIT)
                            it.remove();
                    }

                }
                System.out.println("---");
                StringBuilder sb1 = new StringBuilder();
                Iterator<String> it = possibleVariants.iterator();
                while (it.hasNext()) {
                    String s = it.next();
                    System.out.println(s);
                    sb1.append(s).append(" ");

                }

                posibleVars.add(sb1.toString());
                /*System.out.println("-------");
                System.out.println(twit);
                System.out.println(sb1.toString());
                System.out.println("--------");

                try {
                    String[] strings = new String[possibleVariants.size()];
                    strings = possibleVariants.toArray(strings);
                    ProcessBuilder builder = new ProcessBuilder("python", "/home/samsung/programs/prak/prakRep/Kursovik/src/test.py",
                            twit, sb1.toString(), Integer.toString(i));
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
                    System.out.println("line2 " + line2);
                    twitWords[j] = line2;
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                i++;
            }
        }

//        System.out.println("-------");
//        System.out.println(twit);
//        System.out.println(sb1.toString());
        System.out.println("--------");

        try {
            List<String> commands = new ArrayList<String>();
            commands.add("python");
            commands.add("/home/samsung/programs/prak/prakRep/Kursovik/src/test3.py");
            commands.add(twit);
            commands.addAll(posibleVars);
//            ProcessBuilder builder = new ProcessBuilder("python", "/home/samsung/programs/prak/prakRep/Kursovik/src/test.py",
//                    twit, sb1.toString(), Integer.toString(i));
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
//            System.out.println("line2 " + line2);
//            twitWords[j] = line2;
            Date d = new Date();
            System.out.println("Result");
            System.out.println(d);
            System.out.println("line2 " + line2);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Date d = new Date();
//        System.out.println("!@!@#!@");
//        System.out.println(d);
//        System.out.println(Arrays.toString(twitWords));
    }
}
