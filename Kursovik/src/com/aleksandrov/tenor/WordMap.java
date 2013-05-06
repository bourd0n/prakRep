package com.aleksandrov.tenor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordMap {

    private Map<String, ArrayList<String>> wordMap;

//    private static final String PATH_TO_SLANG = "/home/samsung/programs/prak/prakRep/Kursovik/src/slang.txt";

    public WordMap(String pathToDict, String delimeter) throws FileNotFoundException {
        wordMap = new HashMap<String, ArrayList<String>>();
        init(pathToDict, delimeter);
    }

    private void init(String pathToDict, String delimeter) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(pathToDict));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
                String[] s = line.split(delimeter);
                String key = s[0].toLowerCase().trim();
                final String value = s[1].trim();
                //wordMap.put(s[0].toLowerCase().trim(), s[1].trim());
                if (wordMap.containsKey(key)) {
                    wordMap.get(key).add(value);
                } else {
                    wordMap.put(key, new ArrayList<String>() {
                        {
                            add(value);
                        }
                    });
                }

            }
        }
    }

    public ArrayList<String> find(String key) {
        return wordMap.get(key);
    }
}
