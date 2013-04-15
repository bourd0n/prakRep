package com.aleksandrov.tenor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordMap {

    private Map<String, String> wordMap;

//    private static final String PATH_TO_SLANG = "/home/samsung/programs/prak/prakRep/Kursovik/src/slang.txt";

    public WordMap(String pathToDict, String delimeter) throws FileNotFoundException {
        wordMap = new HashMap<String, String>();
        init(pathToDict, delimeter);
    }

    private void init(String pathToDict, String delimeter) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(pathToDict));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
                String[] s = line.split(delimeter);
                wordMap.put(s[0].toLowerCase().trim(), s[1].trim());
            }
        }
    }

    //todo: think about returning array/collection
    public String find(String key) {
        return wordMap.get(key);
    }
}
