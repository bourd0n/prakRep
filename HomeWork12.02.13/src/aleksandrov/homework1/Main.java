package aleksandrov.homework1;

public class Main {
    public static void main(String[] args) {
        FirstTaskClass ftc = new FirstTaskClass();
        //System.out.println(ftc.get());
        ftc.set();
        ftc.set();
        //System.out.println(ftc.get());
        PriorityQueue pq = new PriorityQueue(10);
        pq.add(1, 1);
        pq.add(2, 2);
        pq.add(4, 4);
        pq.add(5, 5);
        pq.add(6, 6);
        pq.add(8, 8);
        pq.add(9, 9);
        pq.add(10, 10);
        pq.add(11, 11);
        pq.add(16, 16);
        System.out.println(pq);
    }
}
