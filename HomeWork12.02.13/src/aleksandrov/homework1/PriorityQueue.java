package aleksandrov.homework1;

import java.util.Arrays;

public class PriorityQueue {

    private class QueueElement{
        private Object object;
        private int priority;

        private final int DEFAULT_PRIORITY = 1;

        QueueElement(Object object, int priority) {
            this.object = object;
            this.priority = priority;
        }

        QueueElement(Object object){
            this.object = object;
            this.priority = DEFAULT_PRIORITY;
        }

        public Object getObject() {
            return object;
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public String toString() {
            return "QueueElement{" +
                    "object=" + object +
                    ", priority=" + priority +
                    '}';
        }
    }

    private QueueElement[] elements;

    private int size;

    public final static int DEFAULT_SIZE = 10;

    public PriorityQueue() {
        this.elements = new QueueElement[DEFAULT_SIZE];
        this.size = 0;
    }

    public PriorityQueue(int size){
        this.elements = new QueueElement[size];
        this.size = 0;
    }

    public void add(Object object, int priority){
        if (size >= elements.length){
            //copy arrays
            QueueElement[] tempArr = new QueueElement[size * 2];
            //copy
            System.arraycopy(elements, 0, tempArr, 0, elements.length);
            elements = tempArr;

        }

        size ++;
        elements[size-1] = new QueueElement(object, priority);

        int i = size - 1;
        while (i > 0 && (elements[i].getPriority() > elements[i/2].getPriority())){
            QueueElement tempElement = new QueueElement(elements[i/2].getObject(), elements[i/2].getPriority());
            elements[i/2] = elements[i];
            elements[i] = tempElement;
            i = i / 2;
        }
    }

    public Object getMaxElement(){
        Object result =  elements[0].getObject();
        //todo: modify queue


        return result;
    }

    public int size(){
        return 0;
    }

    @Override
    public String toString() {
        return "PriorityQueue{" +
                "elements=" + (elements == null ? null : Arrays.asList(elements)) +
                ", size=" + size +
                '}';
    }
}
