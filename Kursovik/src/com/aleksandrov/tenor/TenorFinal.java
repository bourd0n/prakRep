package com.aleksandrov.tenor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class TenorFinal {

    private static final String SOURCE_FILE = "/home/samsung/programs/prak/prakRep/Kursovik/src/test";
    private static final String VARS_FILE = "/home/samsung/programs/prak/prakRep/Kursovik/src/vars.txt";
    private static final String OUT_FILE = "/home/samsung/programs/prak/prakRep/Kursovik/src/out.txt";

    //todo: think about punctuation
    public static void main(String[] args) throws IOException {
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

        processor.callPythonForFiles(SOURCE_FILE, VARS_FILE, OUT_FILE);
    }
}
