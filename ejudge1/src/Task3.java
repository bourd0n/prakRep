import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Task3 {

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

    static Map<String, Command> commandsMap = new HashMap<String, Command>(){{
        put("one", new OneCommand());
        put("spr", new SprCommand());
        put("utl", new UtlCommand());
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
        }
    }

    private static class FstFilter extends Filter {
        @Override
        public String run(String input) {
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

    private static abstract class Command{
        public abstract boolean run();
    }

    private static class OneCommand extends Command {
        @Override
        public boolean run() {
            String command = stack.pop();
            String commandName = command.replaceFirst("_", "");
            if (!stack.empty()){
                if (isFilter(commandName)) {
                    String result = filtersMap.get(commandName).run(stack.pop());
                    if (result != null && !result.isEmpty()){
                        stack.push(result);
                        return true;
                    }
                    else 
                        return false;
                }
                else if (isCommand(commandName)){
                    if (!stack.empty())
                        return commandsMap.get(commandName).run();
                }
            }
            return false;
        }
    }

    private static class SprCommand extends Command {
        @Override
        public boolean run() {
            String firstCommand = stack.pop();
            String secondCommand = stack.pop();
            String firstCommandName = firstCommand.replaceFirst("_", "");
            String secondCommandName = secondCommand.replaceFirst("_", "");
            //boolean tmpFlag = false;
            if (!stack.empty()){
                if (isFilter(secondCommandName)){
                    String tmpResult = filtersMap.get(secondCommandName).run(stack.pop());
                    if (tmpResult != null && !tmpResult.isEmpty()){
                        stack.push(tmpResult);
                    //  tmpFlag = true;
                    }
                    //else
                    //  tmpFlag = false;
                }
                else if (isCommand(secondCommandName)){
                    if (!stack.empty())
                        commandsMap.get(secondCommandName).run();
                }
                
                if (!stack.empty()){
                    if (isFilter(firstCommandName)){
                        String result = filtersMap.get(firstCommandName).run(stack.pop());
                        if (result != null && !result.isEmpty()){
                            stack.push(result);
                            return true;
                        }
                        else
                            return false;
                    }
                    else if (isCommand(firstCommandName)){
                        if (!stack.empty())
                            return commandsMap.get(firstCommandName).run();
                    }
                }                           
            }
            
            return false;
        }
    }

    private static class UtlCommand extends Command {
        @Override
        public boolean run() {
            String command = stack.pop();
            String commandName = command.replaceFirst("_", "");
            String result = null;
            boolean flag = false;
            do {
                if (!stack.empty()){
                    if (isFilter(commandName)){
                        String stackElement = stack.pop();
                        if (stackElement != null && !stackElement.isEmpty())
                            result = filtersMap.get(commandName).run(stackElement);
                        else
                            return false;
                        if (result != null && !result.isEmpty()){
                            stack.push(result);
                            flag = true;
                        }
                        else
                            flag = false;
                    }
                    else if (isCommand(commandName)){
                        flag = commandsMap.get(commandName).run();
                    }
                }
                else
                    return false;
            //} while (result == null || result.isEmpty());
            } while (!flag && !stack.empty());
            return flag;
        }
    }

    static Stack<String> stack = new Stack<String>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        //scanner.useDelimiter("[ \n\r$]");
        while (scanner.hasNext()){
            String input = scanner.next();
            //if (input.length() == 0)
            //  continue;
            //if (stack.empty() && !isFilter(input) && !){
            //    stack.push(input);
            //}
            //else {
                if (isFilter(input)){
                    if (!stack.empty()) {
                        String stackElement = stack.pop();
                        String res = filtersMap.get(input).run(stackElement);
                        if (res != null && !res.isEmpty()){
                            stack.push(res);
                        }
                    }
                }
                else
                if (isCommand(input)){
                    if (!stack.empty())
                        commandsMap.get(input).run();
                }
                else
                    stack.push(input);
            //}
        }
        
        ArrayList<String> out = new ArrayList<String>();
        while (!stack.empty()){
            out.add(stack.pop());
        }
        
        ListIterator<String> iterator = out.listIterator(out.size());
        while (iterator.hasPrevious()){
            System.out.print(iterator.previous() + " ");
        }
    }

    private static boolean isCommand(String input) {
        return commandsMap.containsKey(input);
    }

    private static boolean isFilter(String input) {
        return filtersMap.containsKey(input);
    }
}
