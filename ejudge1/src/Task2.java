import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Task2 {

    static Map<String, Filter> filtersMap = new HashMap<String, Filter>(){{
        put("eql", new EqualStringFilter());
        put("upc", new UpcaseFilter());
        put("dwn", new DwnFilter());
        put("dup", new DupFilter());
        put("rev", new RevFilter());
        put("fst", new FstFilter());
        put("dpc", new DpcFilter());
        put("cut", new CutFilter());
    }};



    private static abstract class Filter{
        public abstract String run(String input);
    }

    private static class EqualStringFilter extends Filter{

        @Override
        public String run(String input) {
            return input;
        }
    }

    private static class UpcaseFilter extends Filter{

        @Override
        public String run(String input) {
            return input.toUpperCase();
        }
    }

    private static class DwnFilter extends Filter {
        @Override
        public String run(String input) {
            //return input.replaceAll("\\p{javaUpperCase}", "");
            return input.replaceAll("\\p{Upper}", "");
        }
    }

    private static class DupFilter extends Filter {
        @Override
        public String run(String input) {
            return input + input;
        }
    }


    private static class RevFilter extends Filter {
        @Override
        public String run(String input) {
            return new StringBuffer(input).reverse().toString();
            //if (input.length() <= 1)
            //    return input;
            //return this.run(input.substring(1, input.length())) + input.charAt(0);
        }
    }

    private static class FstFilter extends Filter {
        @Override
        public String run(String input) {
            //if (input.length() < 1)
            //    return input;
            //Character firstChar = input.charAt(0);
            //return firstChar + this.run(input.replaceAll(firstChar.toString(), ""));
            String tmp = input;
            String result = "";
            Character firstChar = null;
            while (tmp.length() >= 1){
                firstChar = tmp.charAt(0);
                result += firstChar;
                tmp = tmp.replaceAll(firstChar.toString(), "");
            }
            return result;
        }
    }

    private static class DpcFilter extends Filter {
        @Override
        public String run(String input) {
            //if (input.length() < 1)
            //    return input;
            //Character firstChar = input.charAt(0);
            //String v = new String(new char[]{firstChar, firstChar});
            //return v + this.run(input.substring(1, input.length()));
            /*String tmp = input;
            String result = "";
            Character firstChar = null;
            while (tmp.length() >= 1){
                firstChar = tmp.charAt(0);
                String v = new String(new char[]{firstChar, firstChar});
                result += v;
                tmp = tmp.substring(1, tmp.length());
            }
            return result;*/
            char[] chars = input.toCharArray();
            char[] result = new char[input.length() * 2];
            int j = 0;
            for (int i=0; i < chars.length; i++){
                result[j] = chars[i];
                result[j+1] = chars[i];
                j += 2;
            }

            return new String(result);
        }
    }

    private static class CutFilter extends Filter {
        @Override
        public String run(String input) {
            if (input.length() <= 10)
                return input;
            else
                return input.substring(0, 10);
        }
    }

    static class FilterSplitter{

        private String filtersLine;
        FilterSplitter(String filtersLine) {
            //if (filtersLine == null || filtersLine.isEmpty())
            //    throw new IllegalArgumentException("Input line shouldn't be empty");
            this.filtersLine = filtersLine;
        }

        public ArrayList<Filter> getFilters(){
            String[] filters = filtersLine.split(" ");
            ArrayList<Filter> result = new ArrayList<Filter>();
            for (String filter: filters){
                if (filtersMap.containsKey(filter))
                    result.add(filtersMap.get(filter));
                else
                if (filter.equals("stop"))
                    return result;
                //else
                //    throw new IllegalArgumentException("Wrong filter: " + filter);
            }

            return result;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        //while (scanner.hasNext()){
        String filtersLine = scanner.nextLine();
        String input = scanner.nextLine();
        //if (input == null)
        //    throw new IllegalArgumentException("Should be 2 lines");
        FilterSplitter filterSplitter = new FilterSplitter(filtersLine);
        ArrayList<Filter> filters = filterSplitter.getFilters();
        String result = input;
        for (Filter filter : filters){
            result = filter.run(result);
        }

        System.out.println(result);
        //}
    }

}
