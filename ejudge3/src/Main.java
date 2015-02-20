import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String s = "bankban";
        Pattern p = Pattern.compile("[\'bank\', \'ban\']");
        s = s.replaceFirst(p.pattern(), "ad");
        System.out.println(s);

    }
}
