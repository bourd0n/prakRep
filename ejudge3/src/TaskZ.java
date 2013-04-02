import java.io.InputStreamReader;
import java.util.*;

public class TaskZ {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        String dict = scanner.nextLine();
        final String word = scanner.nextLine();

        Dictionary dictionary = new Dictionary(dict);

        ArrayList<String> wordsFromDict = dictionary.checkWordWithOccurances2(word);
        if (wordsFromDict == null || wordsFromDict.isEmpty())
            System.out.println("?");
        else {
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

    //todo: change
    public static final int OCCURANCE_LIMIT = 3;

    private int count = 0;

    public Dictionary(String dictString) {
        StringTokenizer st = new StringTokenizer(dictString, " ");
        dictionary = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            dictionary.add(st.nextToken());
        }
    }


    public ArrayList<String> checkWordWithOccurances2(String word) {
        ArrayList<String> res = checkWord2(word, dictionary);

        if (res != null && res.size() <= OCCURANCE_LIMIT)
            return res;
        else
            return null;
    }

    public ArrayList<String> checkWord2(String word, ArrayList<String> dictionary) {
        ArrayList<String> res = new ArrayList<String>();
        String str = word;
//        System.out.println("word: " + word);
        for (String s : dictionary) {
            if (str.contains(s)) {
                ArrayList<String> tmpRes;
                ArrayList<String> d = new ArrayList<String>(dictionary);
                d.remove(s);
                res.add(s);
                count++;
                String strt = str.replaceFirst(s, "");
                if (!strt.isEmpty()) {
                    tmpRes = checkWord2(strt, d);
                    if (tmpRes != null) {
                        res.addAll(tmpRes);
                        //count += tmpRes.size();
                        if (count <= OCCURANCE_LIMIT)
                            return res;
                        else {
//                            return null;
//                           res.remove(res.size() - 1);
                            count -= res.size();
                            res.clear();
//                            count -= res.size();

//                            return null;
                        }
                    } else {
                        res.remove(res.size() - 1);
                        count--;
                    }
                } else {
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

