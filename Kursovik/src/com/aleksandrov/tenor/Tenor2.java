package com.aleksandrov.tenor;

import com.rockymadden.stringmetric.similarity.RatcliffObershelpMetric;
import org.apache.commons.codec.language.DoubleMetaphone;
import pt.tumba.spell.Aspell;
import scala.Option;
import scala.Predef;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tenor2 {

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
            System.out.println("After delete repeated 2" + res);
        }

        //replace numbers
        p = Pattern.compile("\\d");
        m = p.matcher(word);

        while (m.find()) {
            String finded = m.group(1);
            Integer num = new Integer(finded);
            int pos = word.indexOf(finded);
            //todo: case of several same digits
            //todo: 2 - to, 4 - for
            if (!finded.equals(word)) {
                //not single number
                //todo: think about if in end, begin or midle of the word
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

    //todo: think about punctuation
    public static void main(String[] args) {
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

        Tenor2 tenor = new Tenor2();

        for (String word : twitWords) {
            StringBuilder sb = new StringBuilder();
            String[] aspellVariants = aspell.find(word);

            if (aspellVariants.length == 1 && aspellVariants[0].equals(word)) {
                //ok - not OOV
                sb.append(word + " ");
            } else {
                //OOV
                Collection<String> possibleVariants = new ArrayList<String>();
                for (String s : aspellVariants) {
                    possibleVariants.add(s);
                }

                //Common transformations like repeated symbols and numbers
                possibleVariants.addAll(tenor.commonTransform(word));

                DoubleMetaphone dm = new DoubleMetaphone();
                String sourceDM = dm.doubleMetaphone(word);

                Iterator<String> it = possibleVariants.iterator();

                while (it.hasNext()) {
                    if (!dm.isDoubleMetaphoneEqual(it.next(), sourceDM))
                        it.remove();
                }

                RatcliffObershelpMetric rm = RatcliffObershelpMetric.apply();

                //System.out.println(rm.compare("Pennsylvania".toCharArray(), "Pencilvaneya".toCharArray()));
                //System.out.println(rm.compare("Pencilvaneya", "Pennsylvania", new Predef.DummyImplicit()));
                Option<Object> some = (Option<Object>) rm.compare("Pencilvaneya", "Pennsylvania", new Predef.DummyImplicit());
                System.out.println(some.get());
                    for (String st : possibleVariants) {
                    System.out.println(st + " DoubleMetaphone " + dm.doubleMetaphone(st));
                }
            }
        }

    }
}
