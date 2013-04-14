package com.aleksandrov.tenor;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class TenorFinal {

    private static final String SOURCE_FILE = "/home/samsung/programs/prak/prakRep/Kursovik/src/test";

    //todo: think about punctuation
    public static void main(String[] args) throws IOException {
        Date d1 = new Date();
        System.out.println(d1);
        Scanner scanner = new Scanner(new File(SOURCE_FILE));
        String twit = scanner.nextLine();
        TwitProcessor processor = new TwitProcessor();
        System.out.println(" FINAL Result : " + processor.processTwit(twit));
    }
}
