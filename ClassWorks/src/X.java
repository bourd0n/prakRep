/*
    out:
    18
    -1, 10
    10, -1
    -1, 10
 */
public class X {

    Integer i = 0;
    X t = null;

    private static Integer parameter = 18;

    public X(Integer k){
        i = k + (parameter % 3);
    }

    public X(X other){
        t = other;
    }

    public void m(X y){
        if ((i + parameter) % 4 > 2){
            i++;
            y = new X(y);
        } else {
            i--;
            t = y;
            y = null;
        }
    }

    public void print(){
        if (t == null){
            System.out.println(i);
        }
        else {
            System.out.print(t.i);
            System.out.print(", ");
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        X x1 = new X(10);
        X x2 = new X(x1);
        X x3 = x1;
        x3.m(x2);
        x1.m(x2.t);
        x2.m(x1);
        System.out.println(parameter);
        x1.print();
        x2.print();
        x3.print();
    }
}
