package com.aleksandrov.tenor;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ProcessDataSet {

    private static String SOURCE_CORPUS = "/home/samsung/programs/prak/prakRep/Kursovik/src/corpus_edit1.tweet";
    private static String RES_CORPUS = "/home/samsung/programs/prak/prakRep/Kursovik/src/corpus_edit1_processed.tweet";

    public void processDataSet() throws IOException {

        WordMap slangMap = new WordMap(TwitProcessor.PATH_TO_SLANG, ":");
        WordMap abrMap = new WordMap(TwitProcessor.PATH_TO_ABBREVIATIONS, ",");
        Scanner scanner = new Scanner(new File(SOURCE_CORPUS));

        BufferedWriter bf = new BufferedWriter(new FileWriter(new File(RES_CORPUS)));

        while (scanner.hasNext("\\p{Digit}+")) {
            boolean saveTwit = true;
            StringBuilder sb = new StringBuilder();
            ArrayList<String> twitForTest = new ArrayList<String>();
            int wordsCount = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < wordsCount; i++) {

                String line = scanner.nextLine();

                if (saveTwit) {
                    StringTokenizer st = new StringTokenizer(line, " \t\n\r");

                    String word = st.nextToken();//scanner.next();
                    String type = st.nextToken();//scanner.next();
                    String correctWord = "";
                    while (st.hasMoreTokens()) {
                        correctWord += st.nextToken() + " ";//scanner.next();
                    }
//                    twitForTest.add(word + " " + type + " " + correctWord.trim());

                    //delete all twits with not recognized OOV
                    if (correctWord.trim().equals(word) && "OOV".equals(type)) {
                        ArrayList<String> slang = slangMap.find(word);
                        if (slang != null) {
                            ArrayList<String> abr = abrMap.find(word);
                            if (abr != null && !abr.isEmpty())
                                slang.addAll(abr);
                        }
                        if (slang == null || slang.isEmpty()) {
                            saveTwit = false;
                        } else {
                            if (!slang.isEmpty())
                                correctWord = slang.get(0);
                        }
                    }

                    twitForTest.add(word + " " + type + " " + correctWord.trim());
//                    sb.append(word).append(" ");
                }
            }

            if (saveTwit) {
                bf.write(new Integer(wordsCount).toString());
                bf.newLine();
                for (String s : twitForTest) {
                    bf.write(s);
                    bf.newLine();
                }
            }

//            testingTwits.add(twitForTest);
        }

        bf.close();
    }
}
