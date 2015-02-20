/*
out
A ,3
A ,2
B, 2
A
B
m, 3
A ,8
B, 8
 */

public class A{
    static final int PARAMETER = 11;

    public static void main(String[] args){
        A a1 = new A(PARAMETER % 4);
        B b1 = new B(PARAMETER % 3);
        //A a3 = new B(b1); error - no such constructor in B
        A a5 = b1.m(new B());
    }

    public A(){
        System.out.println("A");
    }

    public A(int i){
        System.out.println("A ," + i);
        this.i = i+1;
    }

    public A(B b){
        this(b.i + 2);
        System.out.println("A, B");
    }

    public A m(B b){
        System.out.println("m, " + (this.i + b.i));
        return new B(i+5);
    }

    int i;
}

class B extends A {

    public B(){
        i = 0;
        System.out.println("B");
    }

    public B(int i){
        super(i);
        this.i = i + 2;
        System.out.println("B, " + i);
    }

    final int i;
}
