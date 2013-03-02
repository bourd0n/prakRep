import java.util.Iterator;

class Solver<T extends Vertex>{

    //use reduce
    public boolean solve(OrientedGraph<T> graph, T rootElement, T searchedElement){

        return false;
    }

    public static void main(String[] args) {
        //read graph and use solve method
    }
}


interface Vertex<T>{
    public Iterable<T> children();
}


class DepthFirstIterator<T extends Vertex> implements Iterator<T>{

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }

    @Override
    public void remove() {
    }
}

class OrientedGraph<T extends Vertex> {

    public DepthFirstIterator<T> depthFirstIterator(T vertex){

        return null;
    }
}

interface DoActionHandler<V, T>{
    public V doAction(V element0, T elemet1);
}

interface CheckActionHandler<T>{
    public boolean checkAction(T element);
}

class Reducer<T, V> {

    public V reduce (Iterator<T> iterator, DoActionHandler<V, T> doActionHandler, V element0){
        T itElement = null;
        V firstElement = element0;
        while (iterator.hasNext()){
            itElement = iterator.next();
            firstElement = doActionHandler.doAction(firstElement, itElement);
        }
        return firstElement;
    }

    public V reduce2 (Iterator<T> iterator, DoActionHandler<V, T> doActionHandler, V element0, CheckActionHandler<V> checkActionHandler){
        T itElement = null;
        V firstElement = element0;
        while (iterator.hasNext()){
            itElement = iterator.next();
            if (checkActionHandler.checkAction(firstElement)){
                firstElement = doActionHandler.doAction(firstElement, itElement);
            }
            else
                break;
        }
        return firstElement;
    }
}
