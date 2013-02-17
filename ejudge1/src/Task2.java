import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Task2 {

    static Map<String, Filter> filtersMap = new HashMap<String, Filter>(){{
        put("eql", new EqualStringFilter());
        put("upc", new UpcaseStringFilter());
    }};



    static abstract class Filter{
        public abstract String run(String input);
    }

    static class EqualStringFilter extends Filter{

        @Override
        public String run(String input) {
            return input;
        }
    }

    static class UpcaseStringFilter extends Filter{

        @Override
        public String run(String input) {
            return input.toUpperCase();
        }
    }

/*    enum Filters{
        EQL("eql", new eqlFilter()),
        UPC("upc"),
        DWN("dwn"),
        DUP("dup"),
        REV("rev"),
        FST("fst"),
        DPC("dpc"),
        CUT("cut");
        private final String cmd;
        private final Filter filter;

        private Filters(final String cmd, final Filter filter) {
            this.cmd = cmd;
            this.filter = filter;
        }

        public String getCmd(){
            return cmd;
        }

        public Filter getFilter(){
            return filter;
        }

        public static Filters findFilter (String s){
         if (this.)
        }
    }*/

    static class FilterSplitter{

        private String filtersLine;
        FilterSplitter(String filtersLine) {
            if (filtersLine == null || filtersLine.isEmpty())
                throw new IllegalArgumentException("Input line should be empty");
            this.filtersLine = filtersLine;
        }

        public ArrayList<Filter> getFilters(){
            String[] filters = filtersLine.split(" ");
            ArrayList<Filter> result = new ArrayList<Filter>();
            for (String filter: filters){
                //Filters filters = Filters.valueOf()
                if (filtersMap.containsKey(filter))
                    result.add(filtersMap.get(filter));
                else
                if (filter.equals("stop"))
                    return result;
                else
                    throw new IllegalArgumentException("Wrong filter: " + filter);
            }

            return result;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String filtersLine = scanner.nextLine();
        String input = scanner.nextLine();
        if (input == null)
            throw new IllegalArgumentException("Should be 2 lines");
        FilterSplitter filterSplitter = new FilterSplitter(filtersLine);
        ArrayList<Filter> filters = filterSplitter.getFilters();
        String result = input;
        for (Filter filter : filters){
            result = filter.run(result);
        }

        System.out.println(result);
    }
}
