package aleksandrov.homework1.task2;

public class PriorityQueue {

    private final static int DEFAULT_SIZE = 10;
    private final static int DEFAULT_PRIORITY = 0;

    private class QueueElement{
        private Object object;
        private int priority;
        private int source_position;

        QueueElement(Object object, int priority, int source_position) {
            this.object = object;
            this.priority = priority;
            this.source_position = source_position;
        }

        QueueElement(Object object){
            this.object = object;
            this.priority = DEFAULT_PRIORITY;
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
        elements[size-1] = new QueueElement(object, priority, size - 1);

        int i = size - 1;
        while (i > 0 && (elements[i].priority > elements[i/2].priority)){
            QueueElement tempElement = new QueueElement(elements[i/2].object, elements[i/2].priority, elements[i/2].source_position);
            elements[i/2] = elements[i];
            elements[i] = tempElement;
            i = i / 2;
        }
    }

    public Object getElementWithMaxPriority() {
        if (size == 0)
            return null;
        Object result = elements[0].object;
        //modify queue
        elements[0] = elements[size - 1];
        elements[size - 1] = null;
        size--;

        int i = 0;
        int j;
        while (i < size / 2) {
            if (2 * i + 1 < size - 1){
                if (elements[2 * i + 1].priority > elements[2 * i + 2].priority)
                    j = 2 * i + 1;
                else if (elements[2 * i + 1].priority < elements[2 * i + 2].priority)
                    j = 2 * i + 2;
                else {
                    if (elements[2*i + 1].source_position > elements[2*i + 2].source_position)
                        j = 2 * i + 2;
                    else
                        j = 2 * i + 1;
                }
            }
            else
                j = 2 * i + 1;
            if ((elements[i].priority < elements[j].priority)
                    || (elements[i].priority == elements[j].priority && elements[i].source_position > elements[j].source_position)) {
                QueueElement tempElement = new QueueElement(elements[i].object, elements[i].priority, elements[i].source_position);
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
}
