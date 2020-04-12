package lab1;

import java.util.ArrayList;

public class Forest {
    ArrayList<Graph> graphs = new ArrayList<>();

    public void addEdge(Edge edge){
        for (int i = 0; i < graphs.size(); i++) {
            if (graphs.get(i).getPointList().contains(edge.getA()) || graphs.get(i).getPointList().contains(edge.getB())){
                graphs.get(i).addEdge(edge);
                for (int j = i+1; j < graphs.size(); j++) {
                    if (graphs.get(j).getPointList().contains(edge.getA()) || graphs.get(j).getPointList().contains(edge.getB())){
                        combine(graphs.get(i), graphs.get(j));
                        graphs.remove(j);
                        return;
                    }
                }
                return;
            }
        }
        Graph graph = new Graph();
        graph.addEdge(edge);
        graphs.add(graph);
    }

    private void combine(Graph graph, Graph graph1) {
        for (int i = 0; i < graph1.edges.size(); i++) {
            graph.addEdge(graph1.edges.get(i));
        }
    }

    public boolean havePoint(char a){
        for (int i = 0; i < graphs.size(); i++) {
            if (graphs.get(i).havePoint(a)){
                return true;
            }
        }
        return false;
    }

    public Graph toTree(Graph graph) {
        while (graphs.size()>1){
            ArrayList[] pointLists = new ArrayList[graphs.size()];
            for (int i = 0; i < graphs.size(); i++) {
                pointLists[i] = graphs.get(i).getPointList();
            }

            int minWeight = 100;
            Edge minEdge = new Edge();

            for (int i = 0; i < graph.edges.size(); i++) {
                for (int j = 1; j < pointLists.length; j++) {
                    for (int k = 0; k < pointLists[j].size(); k++){
                        for (int l = 0; l < pointLists[0].size(); l++){
                            if (graph.edges.get(i).havePoint((char)pointLists[0].get(l)) &&
                                    graph.edges.get(i).havePoint((char)pointLists[j].get(k))){
                                if (graph.edges.get(i).getWeight() < minWeight){
                                    minEdge = graph.edges.get(i);
                                    minWeight = graph.edges.get(i).getWeight();
                                }
                            }
                        }
                    }
                }
            }

            this.addEdge(minEdge);
        }

        return graphs.get(0);
    }
}
