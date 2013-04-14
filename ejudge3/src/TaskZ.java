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
            for (String s : wordsFromDict) {
                System.out.print(s + " ");
            }
        }
    }


}

class Dictionary {

    private ArrayList<String> dictionary;

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
        for (String s : dictionary) {
            //if (str.contains(s)) {
            if (str.startsWith(s)) {
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
                            count -= res.size();
                            res.clear();
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

