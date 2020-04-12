package lab3;

import java.util.ArrayList;

public class Flow {

    ArrayList<Edge> edges = new ArrayList<>();

    int weight;

    public Edge get(int index){
        return edges.get(index);
    }

    public int size(){
        return edges.size();
    }

    public void add(Edge edge){
        edges.add(edge);
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Flow{" +
                "edges=" + edges +
                ", weight=" + weight +
                "}\n";
    }

    public void removeLast() {
        edges.remove(edges.size() - 1);
    }
}
