package lab2;

import java.util.ArrayList;

public class EulerianPath {

    private Graph graph;
    private ArrayList<Character> path = new ArrayList<>();

    EulerianPath(Graph graph){
        if (haveEulerianPath(graph)){
            this.graph = graph;
        }else {
            System.out.println(false);
        }
    }

    private boolean haveEulerianPath(Graph graph) {
        String start = "", end = "";
        for (char point : graph.getPointsList()) {
            int degree = 0;
            for(Edge edge : graph.edges){
                if (edge.havePoint(point)){
                    degree++;
                }
            }
            if (degree % 2 != 0){
                if (start.isEmpty()){
                    start = point + "";
                }else if (end.isEmpty()){
                    end = point +"";
                    String[] path = Dijkstra.start(start, end, graph).split(" ");
                    ArrayList<Edge> pathGraph = new ArrayList();
                    Edge edge = new Edge();
                    edge.setA(path[0].charAt(0));
                    edge.setB(path[1].charAt(0));
                    edge.setWeight(Integer.parseInt(path[2]));
                    pathGraph.add(edge);
                    try {
                        for (int i = 1; i < path.length; i += 2) {
                            edge = new Edge();
                            edge.setA(path[i].charAt(0));
                            edge.setB(path[i + 2].charAt(0));
                            edge.setWeight(Integer.parseInt(path[i + 3]));
                            pathGraph.add(edge);
                        }
                    }catch (ArrayIndexOutOfBoundsException ignored){}
                    graph.addEdges(pathGraph);
                    haveEulerianPath(graph);
                }
            }
        }
        return true;
    }

    public ArrayList<Character> getPath() throws CloneNotSupportedException {
        Graph graph = (Graph) this.graph.clone();
        Graph pathGraph = new Graph();
        char start = graph.getStartPoint(), point = start;
        path.add(start);

        do {
            for (Edge edge : graph.getAdjacentEdges(point)){
                if (!pathGraph.edges.contains(edge)){
                    pathGraph.addEdge(edge);
                    point = edge.getA() == point ? edge.getB() : edge.getA();
                    path.add(point);
                    break;
                }
            }
        }while (point != start);

        if (pathGraph.edges.size() == this.graph.edges.size()){
            return path;
        }

        while (pathGraph.edges.size() != this.graph.edges.size()){
            graph.removeEdges(pathGraph.edges);
            Graph pathGraph1 = new Graph();
            ArrayList<Character> path1 = new ArrayList<>();

            a: for (char p : graph.getPointsList()){
                for (Edge edge : pathGraph.edges){
                    if (edge.havePoint(p)){
                        start = p;
                        point = start;
                        path1.add(start);
                        break a;
                    }
                }
            }

            do {
                for (Edge edge : graph.getAdjacentEdges(point)){
                    if (!pathGraph1.edges.contains(edge)){
                        pathGraph1.addEdge(edge);
                        point = edge.getA() == point ? edge.getB() : edge.getA();
                        path1.add(point);
                        break;
                    }
                }
            }while (point != start);

            pathGraph.addEdges(pathGraph1.edges);
            connectPaths(path, path1);
        }

        return path;
    }

    private void connectPaths(ArrayList<Character> path, ArrayList<Character> path1){
        int i = 0;
        for (; i < path.size(); i++){
            if (path.get(i) == path1.get(0)){
                break;
            }
        }

        for (int j = 1; j < path1.size(); j++){
            path.add(++i, path1.get(j));
        }
    }
}
