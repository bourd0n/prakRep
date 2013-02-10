package aleksandrov.homework1;

public class PriorityQueue {

    private class QueueElement{
        private Object object;
        private int priority;

        private final int DEFAULT_PRIORITY = 0;

        QueueElement(Object object, int priority) {
            this.object = object;
            this.priority = priority;
        }

        QueueElement(Object object){
            this.object = object;
            this.priority = DEFAULT_PRIORITY;
        }


    }
    private QueueElement[] elements;

    private int size;

    public PriorityQueue() {
        this.elements = new QueueElement[0];
        this.size = 0;
    }

    public PriorityQueue(int size){
        this.elements = new QueueElement[size];
        this.size = 0;
    }

    public void add(Object object, int priority){
        if (size >= elements.length){

        }
        else{
            elements.
        }

    }

    public Object[] getObjectsWithHightestPriority(){
        return null;
    }

    public int size(){
        return 0;
    }

}
