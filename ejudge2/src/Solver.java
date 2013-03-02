import java.io.InputStreamReader;
import java.util.*;

class Solver<T extends Vertex>{

    //use reduce
    public boolean solve(OrientedGraph<T> graph, T rootElement, final T searchedElement){
        Reducer<T, Boolean> reducer = new Reducer<T, Boolean>();
/*
        return reducer.reduce(graph.depthFirstIterator(rootElement), new DoActionHandler<Boolean, T>() {
            @Override
            public Boolean doAction(Boolean element0, T element1) {
                if (!element0){
                 element0 = searchedElement.equals(element1);
                }
                return element0;
            }
        }, false);
*/

        return reducer.reduce2(graph.depthFirstIterator(rootElement), new DoActionHandler<Boolean, T>() {
            @Override
            public Boolean doAction(Boolean element0, T element1) {
                if (!element0) {
                    element0 = searchedElement.equals(element1);
                }
                return element0;
            }
        }, false, new CheckActionHandler<Boolean>() {
                    @Override
                    public boolean checkAction(Boolean element) {
                        return element;
                    }
                });
    }

    public static void main(String[] args) {
        //read graph and use solve method
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        OrientedGraph<BasicVertex> graph = new OrientedGraph<BasicVertex>();
        int size = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < size; i ++){
            String line = scanner.nextLine();
            String[] lines = line.split(" ");
            BasicVertex bv1 = new BasicVertex(lines[0]);
            BasicVertex bv2 = new BasicVertex(lines[1]);
            graph.add(bv1, bv2);
            //graph.add(new BasicVertex(lines[0]), new BasicVertex(lines[1]));
        }

        Solver<BasicVertex> solver = new Solver<BasicVertex>();
        while (scanner.hasNext()){
            String[] line = scanner.nextLine().split(" ");
            BasicVertex from = new BasicVertex(line[0]);
            BasicVertex to = new BasicVertex(line[1]);
            if (!graph.contains(from) || !graph.contains(to))
                System.out.println("?");
            else{
                boolean finded = solver.solve(graph, from, to);
                if (finded)
                    System.out.println("+");
                else
                    System.out.println("-");
            }
        }
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
        children = new LinkedList<BasicVertex>();
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

//done
class DepthFirstIterator<T extends Vertex> implements Iterator<T>{

    //private T root;
    private LinkedList<T> list;
    public DepthFirstIterator(T root) {
        //this.root = root;
        list = new LinkedList<T>();
        list.add(root);
    }

    @Override
    public boolean hasNext() {
        return !list.isEmpty();
    }

    @Override
    public T next() {
        if (list.isEmpty())
            throw new NoSuchElementException();
        //T nextElement = list.getFirst();
        T nextElement = list.pop();
        Iterable<T> nextChildren = nextElement.children();
        if (nextChildren != null){
            for (T nextChild : nextChildren) {
                list.add(nextChild);
            }
        }
        return nextElement;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

//done
class OrientedGraph<T extends Vertex> {

    private ArrayList<T> graph;

    OrientedGraph() {
        this.graph = new ArrayList<T>();
    }

    public void add(T beginElement, T endElement){
        T beginElementGraph = beginElement;
        T endElementGraph = endElement;
        if (graph.contains(beginElement))
            beginElementGraph = graph.get(graph.indexOf(beginElement));
        if (graph.contains(endElement))
            endElementGraph = graph.get(graph.indexOf(endElement));

        beginElementGraph.addChild(endElementGraph);

        if (!graph.contains(beginElementGraph)){
            graph.add(beginElementGraph);
        }

        if (!graph.contains(endElementGraph)){
            graph.add(endElementGraph);
        }
    }

    public boolean contains(T element){
        return graph.contains(element);
    }

    public DepthFirstIterator<T> depthFirstIterator(T vertex){
        if (!graph.contains(vertex))
            throw new IllegalArgumentException("No such vertex in graph : " + vertex);
        return new DepthFirstIterator<T>(graph.get(graph.indexOf(vertex)));
    }
}

interface DoActionHandler<V, T>{
    public V doAction(V element0, T element1);
}

class DepthSearchDoActionHandler<V, T> implements DoActionHandler<V, T>{

    private boolean finded;

    DepthSearchDoActionHandler() {
        this.finded = false;
    }

    @Override
    public V doAction(V element0, T element1) {
        if (element0.equals(element1))
            finded = true;
        return element0;
    }
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
            if (!checkActionHandler.checkAction(firstElement)){
                firstElement = doActionHandler.doAction(firstElement, itElement);
            }
            else
                break;
        }
        return firstElement;
    }
}
