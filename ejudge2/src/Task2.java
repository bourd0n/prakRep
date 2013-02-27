import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Task2<T> {

    private  AccessController<T> accessController;
    //private Type type;
    private Generator<T> generator;
    //public Task2(AccessController<?> accessController, Type type) {
    public Task2(AccessController<T> accessController, Generator<T> generator) {
        this.accessController = accessController;
        this.generator = generator;
    //    this.type = type;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String type = scanner.nextLine();

        //AccessController<? extends T> accessController = null;
        //Task2 task2 = null;
        if ("string".equals(type)) {
            //accessController = new AccessController<String>();
            //Class type1 = Type.STRING.getType();
            //accessController = new AccessController<String>();
            Task2<String> task2 = new Task2<String>(new AccessController<String>(), BasicGenerator.create(String.class));
            while (scanner.hasNextLine()) {
                String str = task2.execute(scanner.nextLine());
                if (str != null && !str.isEmpty()) {
                    System.out.println(str);
                }
            }
        } else if ("int".equals(type)) {
            //accessController = new AccessController<Integer>();
            //task2 = new Task2(accessController, BasicGenerator.create(Integer.class));
            Task2<Integer> task2 = new Task2<Integer>(new AccessController<Integer>(), BasicGenerator.create(Integer.class));
            while (scanner.hasNextLine()) {
                String str = task2.execute(scanner.nextLine());
                if (str != null && !str.isEmpty()) {
                    System.out.println(str);
                }
            }
        } else if ("long".equals(type)) {
            //accessController = new AccessController<Long>();
            //task2 = new Task2(accessController, BasicGenerator.create(Long.class));
            Task2<Long> task2 = new Task2<Long>(new AccessController<Long>(), BasicGenerator.create(Long.class));
            while (scanner.hasNextLine()) {
                String str = task2.execute(scanner.nextLine());
                if (str != null && !str.isEmpty()) {
                    System.out.println(str);
                }
            }
        }

        //Task2 task2 = new Task2(accessController,);
/*        if (task2 != null) {
            while (scanner.hasNextLine()) {
                String str = task2.execute(scanner.nextLine());
                if (str != null && !str.isEmpty()) {
                    System.out.println(str);
                }
            }
        }*/
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


/*enum Type {
    INTEGER {//(true) {
        public Object parse(String string) { return Integer.valueOf(string); }
        public Class getType() {
            return Integer.class;
        }
    },
    LONG{//(false) {
        public Object parse(String string) { return Long.valueOf(string); }

        @Override
        public Class getType() {
            return null;
        }
    },
    STRING{//(false) {
        public Object parse(String string) { return string; }

        @Override
        public Class getType() {
            return null;
        }
    };

    //boolean primitive;
    //Class clazz;
    //Type(String string) { this.primitive = primitive; }

   // public boolean isPrimitive() { return primitive; }
    public abstract Object parse(String string);
    public abstract Class getType();
}*/

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
        super("Wrong security indentificator");
    }
}

