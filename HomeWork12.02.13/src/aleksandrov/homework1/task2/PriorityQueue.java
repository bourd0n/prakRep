package aleksandrov.homework1.task2;

import java.util.Arrays;

public class PriorityQueue {

    public final static int DEFAULT_SIZE = 10;
    public final static int DEFAULT_PRIORITY = 1;

    private class QueueElement{
        private Object object;
        private int priority;

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

    public Object getElementWithMaxPriority() {
        if (size == 0)
            return null;
        Object result = elements[0].getObject();
        //modify queue
        elements[0] = elements[size - 1];
        size--;
        int i = 0;
        int j;
        while (i < size / 2) {
            if (2 * i + 1 < size && elements[2 * i + 1].getPriority() < elements[2 * i + 2].getPriority())
                j = 2 * i + 2;
            else
                j = 2 * i + 1;
            if (elements[i].getPriority() < elements[j].getPriority()) {
                QueueElement tempElement = new QueueElement(elements[i].getObject(), elements[i].getPriority());
                elements[i] = elements[j];
                elements[j] = tempElement;
                i = j;
            } else
                i = size;
        }
        return result;
    }

    public int size(){
        return size;
    }

    @Override
    public String toString() {
        return "PriorityQueue{" +
                "elements=" + (elements == null ? null : Arrays.asList(elements)) +
                ", size=" + size +
                '}';
    }
}