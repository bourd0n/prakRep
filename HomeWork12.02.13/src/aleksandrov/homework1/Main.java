package aleksandrov.homework1;

import aleksandrov.homework1.task1.FirstTaskClass;
import aleksandrov.homework1.task2.PriorityQueue;

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
        pq.add(17, 17);
        pq.add(18, 18);
        pq.add(19, 19);
        System.out.println("size " + pq.size());
        System.out.println(pq);

        Object obj = null;
        do {
            obj = pq.getElementWithMaxPriority();
            System.out.println(obj);

        } while (obj != null);
        System.out.println("size " + pq.size());
    }
}
