import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Task2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String type = scanner.nextLine();

        if ("string".equals(type)) {
            Executor<String> task2 = new Executor<String>(new AccessController<String>(), BasicGenerator.create(String.class));
            while (scanner.hasNextLine()) {
                String str = task2.execute(scanner.nextLine());
                if (str != null && !str.isEmpty()) {
                    System.out.println(str);
                }
            }
        } else if ("int".equals(type)) {
            Executor<Integer> task2 = new Executor<Integer>(new AccessController<Integer>(), BasicGenerator.create(Integer.class));
            while (scanner.hasNextLine()) {
                String str = task2.execute(scanner.nextLine());
                if (str != null && !str.isEmpty()) {
                    System.out.println(str);
                }
            }
        } else if ("long".equals(type)) {
            Executor<Long> task2 = new Executor<Long>(new AccessController<Long>(), BasicGenerator.create(Long.class));
            while (scanner.hasNextLine()) {
                String str = task2.execute(scanner.nextLine());
                if (str != null && !str.isEmpty()) {
                    System.out.println(str);
                }
            }
        }

    }
}

class Executor<T>{
    private  AccessController<T> accessController;
    private Generator<T> generator;

    Executor(AccessController<T> accessController, Generator<T> generator) {
        this.accessController = accessController;
        this.generator = generator;
    }

    public String execute(String line) {
        StringTokenizer stringTokenizer = new StringTokenizer(line);
        String action = stringTokenizer.nextToken();
        if ("set".equals(action)){
            String value = stringTokenizer.nextToken();
            Integer pos = new Integer(stringTokenizer.nextToken());
            Integer neg = new Integer(stringTokenizer.nextToken());
            accessController.set(generator.getNeededTypeInstance(value), pos, neg);
        }
        else
        if ("print".equals(action)){
            Integer securityId = new Integer(stringTokenizer.nextToken());
            try {
                return accessController.get(securityId).toString();
            } catch (AccessViolationException e) {
                return "invalid";
            }

        }
        return null;
    }
}

interface Generator<T>{
    public T getNeededTypeInstance(String st);
}

class BasicGenerator<T> implements Generator<T>{
    private Class<T> type;
    BasicGenerator(Class<T> type){
        this.type = type;
    }

    public T getNeededTypeInstance(String st){
        try {
            return type.getConstructor(String.class).newInstance(st);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static <T> Generator<T> create(Class<T> type){
        return new BasicGenerator<T>(type);
    }
}

class AccessController<T>{

    private T accessManageable;
    private int pos;
    private int neg;

    public T get(int securityId) throws AccessViolationException {
        if ((securityId & pos) == pos && (securityId | neg) == neg){
            return accessManageable;
        }
        else
            throw new AccessViolationException();
    }

    public void set(T accessManageable, int pos, int neg){
        this.accessManageable = accessManageable;
        this.pos = pos;
        this.neg = neg;
    }

    public boolean doRightsSatisfy(int securityId){
        return (securityId & pos) == pos && (securityId | neg) == neg;
    }

}

class AccessViolationException extends Exception {

    AccessViolationException(){
        super("Wrong security identificator");
    }
}

