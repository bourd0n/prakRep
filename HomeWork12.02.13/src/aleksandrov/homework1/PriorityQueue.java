package aleksandrov.homework1;

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
    }

    private QueueElement[] elements;

    private int size;

    public final static int DEFAULT_SIZE = 10;

    public PriorityQueue() {
        this.elements = new QueueElement[DEFAULT_SIZE];
        this.size = DEFAULT_SIZE;
    }

    public PriorityQueue(int size){
        this.elements = new QueueElement[size];
        this.size = size;
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
        elements[size] = new QueueElement(object, priority);

        int i = size;
        while (i > 1 && (elements[i].getPriority() > elements[i/2].getPriority())){
            QueueElement tempElement = new QueueElement(elements[i/2].getObject(), elements[i/2].getPriority());
            elements[i/2] = elements[i];
            elements[i] = tempElement;
            i = i / 2;
        }
    }

    public Object[] getObjectsWithHightestPriority(){
        return null;
    }

    public int size(){
        return 0;
    }

}
