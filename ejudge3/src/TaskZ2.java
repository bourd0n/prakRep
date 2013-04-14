/*
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskZ {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        String dict = scanner.nextLine();
        final String word = scanner.nextLine();

        Dictionary dictionary = new Dictionary(dict);

//        ArrayList<String> wordsFromDict = dictionary.checkWordWithOccurances(word);
        ArrayList<String> wordsFromDict = dictionary.checkWordWithOccurances2(word);
        if (wordsFromDict == null || wordsFromDict.isEmpty())
            System.out.println("?");
        else {
//            for (String s : wordsFromDict) {
//                System.out.print(s + " ");
//            }
            Collections.sort(wordsFromDict, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    int p1 = word.indexOf(o1);
                    int p2 = word.indexOf(o2);
                    if (p1 == p2)
                        return 0;
                    else if (p1 < p2)
                        return -1;
                    else
                        return 1;
//                    return word.indexOf(o1) > word.indexOf(o2) ? -1 : 1;
                }
            });

            for (String s : wordsFromDict) {
                System.out.print(s + " ");
            }
        }
    }


}

class Dictionary {

    private ArrayList<String> dictionary;

    public static final int OCCURANCE_LIMIT = 3;

    public Dictionary(String dictString) {
        StringTokenizer st = new StringTokenizer(dictString, " ");
//        StringTokenizer st1 = new StringTokenizer(dictString, " ") ;
        dictionary = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            dictionary.add(st.nextToken());
        }
    }

    public ArrayList<String> checkWordWithOccurances(String word) {
        ArrayList<String> res = checkWord(word);

//        if (res != null && res.size() <= OCCURANCE_LIMIT)
        return res;
//        else
//            return null;
    }

    public ArrayList<String> checkWordWithOccurances2(String word) {
        ArrayList<String> res = checkWord2(word, dictionary);

//        if (res != null && res.size() <= OCCURANCE_LIMIT)
        return res;
//        else
//            return null;
    }

    public ArrayList<String> checkWord(String word) {
//        word.split()
        ArrayList<String> res = new ArrayList<String>();
        String str = word;
        for (String s : dictionary) {
            if (str.contains(s)) {
                Pattern pattern = Pattern.compile(s);
                Matcher matcher = pattern.matcher(str);
                int count = 0;
                while (matcher.find()) {
                    count ++;
                }
                if (count > 1) {
                    return null;
                }

                res.add(s);
                str = str.replaceFirst(s, "");
            }
        }

        if (str == null || str.isEmpty())
            return res;
        else
            return null;
    }

    public ArrayList<String> checkWord2(String word, ArrayList<String> dictionary ) {
        ArrayList<String> res = new ArrayList<String>();
        String str = word;
        for (String s : dictionary) {
            if (str.contains(s)) {
                ArrayList<String> tmpRes;
                ArrayList<String> d = new ArrayList<String>(dictionary);
                d.remove(s);
                res.add(s);
                int pos = str.indexOf(s);
                String strt = str.replaceFirst(s, "");
                if (!strt.isEmpty()){
                    tmpRes = checkWord2(strt, d);
                    if (tmpRes != null){
                        res.addAll(tmpRes);
                        return res;
                    }
                    else
                        res.clear();
                }
                else {
                    return res;
                }
            }
        }

        if (str == null || str.isEmpty())
            return res;
        else
            return null;
    }
}

*/
