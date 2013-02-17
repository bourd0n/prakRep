package aleksandrov.homework1;

import aleksandrov.homework1.task1.FirstTaskClass;
import aleksandrov.homework1.task2.PriorityQueue;
import aleksandrov.homework1.task3.Sorter;

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
        //
        pq.add(111, 8);
        pq.add(21, 10);
        pq.add(22, 10);
        pq.add(23, 10);
        pq.add(24 , 10);
        pq.add(25, 10);
        System.out.println("size " + pq.size());
        System.out.println(pq);

        Object obj = null;
        while (pq.size() > 0)    {
            obj = pq.getElementWithMaxPriority();
            System.out.print(obj + " ");

        }
        System.out.println();
        Sorter sorter = new Sorter();
        Integer[] arr = new Integer[]{1, 2, 5, 6, -1, -2, 10, 9, 7, 1, 105, 2};
        sorter.sort(arr);
        for (Integer anArr : arr)
            System.out.print(anArr + " ");
        //System.out.println("size " + pq.size());
    }
}
