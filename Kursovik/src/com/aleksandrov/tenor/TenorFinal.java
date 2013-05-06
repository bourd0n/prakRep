package com.aleksandrov.tenor;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TenorFinal {

    private static final String SOURCE_FILE = "/home/samsung/programs/prak/prakRep/Kursovik/src/test";
    private static final String VARS_FILE = "/home/samsung/programs/prak/prakRep/Kursovik/src/vars.txt";
    private static final String OUT_TWIT_FILE = "/home/samsung/programs/prak/prakRep/Kursovik/src/out_tweet.txt";
    private static final String OUT_FILE = "/home/samsung/programs/prak/prakRep/Kursovik/src/out.txt";
    private static final String CORPPUS_FILE = "/home/samsung/programs/prak/prakRep/Kursovik/src/corpus_edit1.tweet";

    private ArrayList<ArrayList<String>> testingTwits = new ArrayList<ArrayList<String>>();

    public static void main(String[] args) throws IOException {
        TenorFinal tf = new TenorFinal();
        if (args.length >= 1 && "test".equals(args[0])) {
            tf.processFileForTest();
            tf.run(args[0]);
        } else
            tf.run(null);
        //System.out.println(args[0]);

        if (args.length >= 1 && "test".equals(args[0])) {
            tf.processResults();
        }
    }

    private void processResults() throws FileNotFoundException {
        int Aoov = 0, Boov = 0, Coov = 0;
        int Anorm = 0, Bnorm = 0, Cnorm = 0;
        Scanner scanner = new Scanner(new File(OUT_FILE));

        Iterator<ArrayList<String>> it = testingTwits.iterator();
        int line = 0;
        while (scanner.hasNextLine()) {
            line++;
            String resTwit = scanner.nextLine();
            line++;
            String types = scanner.nextLine();
            ArrayList<String> twitTest = it.next();
            Iterator<String> wordIt = twitTest.iterator();
            Pattern pattern = Pattern.compile("'.+?'|\".+?\"");
            Matcher matcher = pattern.matcher(resTwit);
            Pattern pattern2 = Pattern.compile("'.+?'");
            Matcher matcher2 = pattern2.matcher(types);
//            String corWord = wordIt.next();

            /*StringTokenizer st = new StringTokenizer(corWord, " \t\n\r");
            st.nextToken();*/
            while (matcher.find() && matcher2.find()) {
                String corWord = "";
                try {
                    corWord = wordIt.next();
                } catch (Exception ex) {
                    System.out.println("NIAL" + twitTest.size());
                    ex.printStackTrace();
                }
                StringTokenizer st = new StringTokenizer(corWord, " \t\n\r");
                String sourceWord = st.nextToken();

                String resWord = matcher.group(0);
                String type = matcher2.group(0);
                resWord = resWord.substring(1, resWord.length() - 1);

                type = type.substring(1, type.length() - 1);//replaceAll("'", "");

                String trueType = st.nextToken();
                String trueWord = "";// st.nextToken();

                while (st.hasMoreTokens()) {
                    trueWord += st.nextToken() + " ";
                }

                //add to A, B, C, D
/*                if ("OOV".equals(trueType)) {
                    assert trueWord != null;
*//*
                    if ("OOV".equals(type) && resWord.trim().equalsIgnoreCase(trueWord.trim())) {
                        A++;
                    } else
                        C++;
*//*

                    if ("OOV".equals(type)) {
                        Aoov++;
                        if (resWord.trim().equalsIgnoreCase(trueWord.trim())) {
                            Anorm++;
                        } else
                            Cnorm++;
                    } else
                        Coov++;
                } else if ("IV".equals(trueType)) {
                    assert trueWord != null;
//                    if ("IV".equals(type) && resWord.trim().equalsIgnoreCase(trueWord.trim())) {
                    if ("IV".equals(type)){
                        Boov++;
                    }
                }*/
                //System.out.println("        RESULT      ");
                //System.out.println();
                if ("OOV".equals(type)) {
                    assert trueWord != null;

                    if ("OOV".equals(trueType)) {
                        Aoov++;
                        if (resWord.trim().equalsIgnoreCase(trueWord.trim())) {
                            Anorm++;
                        } else
                            Cnorm++;
                    } else {
                        System.out.println("Source word " + sourceWord + " ResWord " +resWord + " line " + line);
                        Coov++;
                    }
                } else if ("IV".equals(type) || "NO".equals(type)) {
                    assert trueWord != null;
                    if ("OOV".equals(trueType)) {
                        Boov++;
                    }
                }

            }
        }

        double precision = Aoov / (double) (Aoov + Boov);
        double recall = Aoov / (double) (Aoov + Coov);
        double f1 = 2 * precision * recall / (precision + recall);

        System.out.println("OOV detection");
        System.out.println("Aoov " + Aoov + " Boov " + Boov + " C " + Coov);
        System.out.println("precision " + precision);
        System.out.println("recall " + recall);
        System.out.println("f1 " + f1);

    }

    public void processFileForTest() throws IOException {
        Scanner scanner = new Scanner(new File(CORPPUS_FILE));

        BufferedWriter bf = new BufferedWriter(new FileWriter(new File(SOURCE_FILE)));

        while (scanner.hasNext("\\p{Digit}+")) {
            StringBuilder sb = new StringBuilder();
            ArrayList<String> twitForTest = new ArrayList<String>();
            int wordsCount = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < wordsCount; i++) {
                String line = scanner.nextLine();

                StringTokenizer st = new StringTokenizer(line, " \t\n\r");

                String word = st.nextToken();//scanner.next();
                String type = st.nextToken();//scanner.next();
                String correctWord = "";
                while (st.hasMoreTokens()) {
                    correctWord += st.nextToken() + " ";//scanner.next();
                }
                twitForTest.add(word + " " + type + " " + correctWord);
                sb.append(word).append(" ");
            }

            bf.write(sb.toString());
            bf.newLine();
            testingTwits.add(twitForTest);
        }

        bf.close();

    }


    public void run(String mode) throws IOException {
        Date d1 = new Date();
        System.out.println(d1);
        Scanner scanner = new Scanner(new File(SOURCE_FILE));
        BufferedWriter bf = new BufferedWriter(new FileWriter(new File(VARS_FILE)));
        TwitProcessor processor = new TwitProcessor();
        while (scanner.hasNextLine()) {
            String twit = scanner.nextLine();
            if (!twit.isEmpty()) {
                String t = processor.processTwit(twit);
                bf.write(t);
                bf.newLine();
                System.out.println("FINAL1: " + t);
            }
        }

        bf.close();

        processor.callPythonForFiles(SOURCE_FILE, VARS_FILE, OUT_FILE, OUT_TWIT_FILE, "test".equals(mode));
    }
}
