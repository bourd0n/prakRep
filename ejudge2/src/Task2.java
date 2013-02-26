import java.io.InputStreamReader;
import java.util.Scanner;

public class Task2 {

    private  AccessController<?> accessController;

    public Task2(AccessController<?> accessController) {
        this.accessController = accessController;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String type = scanner.nextLine();

        AccessController<?> accessController = null;

        if ("string".equals(type)){
            accessController = new AccessController<String>();
        }
        else if ("int".equals(type)){
            accessController = new AccessController<Integer>();
        }
        else if ("long".equals(type)){
            accessController = new AccessController<Long>();
        }

        Task2 task2 = new Task2(accessController);

        while (scanner.hasNextLine()){
            String str = task2.execute(scanner.nextLine());
            if (str != null && !str.isEmpty()){
                System.out.println(str);
            }
        }
    }

    public String execute(String line) {
        String[] stringsArr = line.split(" ");
        if ("set".equals(stringsArr[0])){

        }
        else if ("print".equals(stringsArr[0])){

        }

        return null;
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

