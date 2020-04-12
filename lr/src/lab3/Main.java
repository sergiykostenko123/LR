package lab3;
//Алгоритм Форда-Фалкерсона

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Graph graph = new Graph(new File("src/lab3/data"));

        System.out.println("Вхідні дані:");
        System.out.println(graph);

        ArrayList<Flow> flows = new ArrayList<>();
        System.out.println(start(graph, flows));
        int totalWeight = 0;
        for (Flow flow : flows){
            totalWeight += flow.weight;
        }
        System.out.println("Величина потоку " + totalWeight);
    }


    private static ArrayList<Flow> start(Graph graph, ArrayList<Flow> flows) {
        char start = graph.getStartPoint(), end = graph.getEndPoint();


        ArrayList<Character> availablePoint = graph.getPointsList();
        char point = start;
        Flow flow = new Flow();
        while (point < end){
            try {
                availablePoint.remove(availablePoint.indexOf(point));
            }catch (ArrayIndexOutOfBoundsException ignored){}
            Edge maxEdge = new Edge('A', 'A', 0);
            for (Edge edge : graph.getAdjacentEdges(point)) {
                if ( edge.getWeight() > maxEdge.getWeight()){
                    for (char p :  availablePoint){
                        if (edge.havePoint(p)){
                            maxEdge = edge;
                        }
                    }
                }
            }
            if (maxEdge.getWeight() == 0){
                if (point == start){
                    return flows;
                }
                char previousPoint = flow.get(flow.size() - 1).getA() == point ?
                        flow.get(flow.size() - 1).getB() : flow.get(flow.size() - 1).getA();
                point = previousPoint;
                flow.removeLast();
            }else {
                flow.add(maxEdge);
                point = point == maxEdge.getA() ? maxEdge.getB() : maxEdge.getA();
            }
        }

        int minWeight = Integer.MAX_VALUE;

        for (Edge edge : flow.edges) {
            if (minWeight > edge.getWeight()) {
                minWeight = edge.getWeight();
            }
        }

        flow.setWeight(minWeight);

        for (Edge edge : flow.edges){
            edge.setWeight(edge.getWeight() - minWeight);
        }

        flows.add(flow);

        return start(graph, flows);
    }
}
