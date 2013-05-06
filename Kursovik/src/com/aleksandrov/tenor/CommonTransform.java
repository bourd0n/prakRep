package com.aleksandrov.tenor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonTransform {

    public Collection<String> getCommonTransformations(String word) {
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

        //if not number itself
        if (!word.matches("\\d+")) {
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
        }

        return possibleTransformations;
    }
}
