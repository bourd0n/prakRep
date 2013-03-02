import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

//todo: implement
class Solver<T extends Vertex>{

    //use reduce
    public boolean solve(OrientedGraph<T> graph, T rootElement, T searchedElement){

        return false;
    }

    public static void main(String[] args) {
        //read graph and use solve method
    }
}


interface Vertex<T extends Vertex>{

    public void addChild(T child);

    public Iterable<T> children();
}


class BasicVertex implements Vertex<BasicVertex>{

    private String name;

    private LinkedList<BasicVertex> children;

    BasicVertex(String name){
        this.name = name;
    }

    public void addChild(BasicVertex basicVertex){
        if (children.contains(basicVertex))
            throw new IllegalArgumentException("Vertex :" + name + " always contains child with name " + basicVertex.name);
        children.add(basicVertex);
    }

    @Override
    public Iterable<BasicVertex> children() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicVertex that = (BasicVertex) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

}

//todo: implement
class DepthFirstIterator<T extends Vertex> implements Iterator<T>{

    private T root;
    public DepthFirstIterator(T root) {
        this.root = root;
    }

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

//todo: implement
class OrientedGraph<T extends Vertex> {

    private ArrayList<T> graph;

    public void add(T beginElement, T endElement){
        beginElement.addChild(endElement);
        if (!graph.contains(beginElement)){
            graph.add(beginElement);
        }

        if (!graph.contains(endElement)){
            graph.add(endElement);
        }
    }

    public DepthFirstIterator<T> depthFirstIterator(T vertex){
        if (!graph.contains(vertex))
            throw new IllegalArgumentException("No such vertex in graph : " + vertex);
        return new DepthFirstIterator<T>(graph.get(graph.indexOf(vertex)));
    }
}

interface DoActionHandler<V, T>{
    public V doAction(V element0, T elemet1);
}

interface CheckActionHandler<T>{
    public boolean checkAction(T element);
}

//done
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
