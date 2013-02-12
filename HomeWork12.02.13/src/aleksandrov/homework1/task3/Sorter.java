package aleksandrov.homework1.task3;

import aleksandrov.homework1.task2.PriorityQueue;

public class Sorter {

    public void sort(Integer[] data){
        PriorityQueue priorityQueue = new PriorityQueue(data.length);
        for (Integer aData : data) {
            priorityQueue.add(aData, aData);
        }

        int i = 0;
        while (priorityQueue.size() > 0){
            data[i++] = (Integer) priorityQueue.getElementWithMaxPriority();
        }

    }
}
