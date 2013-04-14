package com.aleksandrov.tenor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SlangMap {

    private Map<String, String> slangMap;

    private static final String PATH_TO_SLANG = "/home/samsung/programs/prak/prakRep/Kursovik/src/slang.txt";

    public SlangMap() throws FileNotFoundException {
        slangMap = new HashMap<String, String>();
        init();
    }

    private void init() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(PATH_TO_SLANG));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
                String[] s = line.split(":");
                slangMap.put(s[0].toLowerCase().trim(), s[1].trim());
            }
        }
    }

    public String find(String key) {
        return slangMap.get(key);
    }
}
