package lab2;

import java.io.*;
import java.util.*;

public class Dijkstra {
    private static DijkstraGraph.DijkstraEdge[] DijkstraGraph;

    public static String start(String START, String END, Graph graph) {
        DijkstraGraph.DijkstraEdge[] DijkstraGraph = new DijkstraGraph.DijkstraEdge[graph.edges.size()*2];
        for (int i = 0; i < graph.edges.size(); i++){
            String a = Character.toString(graph.edges.get(i).getA()), b = Character.toString(graph.edges.get(i).getB());
            DijkstraGraph[i*2] = new DijkstraGraph.DijkstraEdge(a, b, graph.edges.get(i).getWeight());
            DijkstraGraph[i*2+1] = new DijkstraGraph.DijkstraEdge(b, a, graph.edges.get(i).getWeight());
        }
        DijkstraGraph g = new DijkstraGraph(DijkstraGraph);
        g.dijkstra(START);
        return g.printPath(END);
    }
}

class DijkstraGraph {
    private final Map<String, Vertex> DijkstraGraph; // mapping of vertex names to Vertex objects, built from a set of DijkstraEdges

    /** One DijkstraEdge of the DijkstraGraph (only used by DijkstraGraph constructor) */
    public static class DijkstraEdge {
        public final String v1, v2;
        public final int dist;
        public DijkstraEdge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    /** One vertex of the DijkstraGraph, complete with mappings to neighbouring vertices */
    public static class Vertex implements Comparable<Vertex>{
        public final String name;
        public int dist = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbours = new HashMap<>();

        public Vertex(String name)
        {
            this.name = name;
        }

        private String printPath()
        {
            StringBuilder res = new StringBuilder();
            if (this == this.previous)
            {
                res.append(this.name);
            }
            else if (this.previous == null)
            {
                res.append(this.name);
            }
            else
            {
                this.previous.printPath();
                res.append(this.previous.printPath() + " " + this.name + " " + this.dist);
            }
            return res.toString();
        }

        public int compareTo(Vertex other)
        {
            if (dist == other.dist)
                return name.compareTo(other.name);

            return Integer.compare(dist, other.dist);
        }

        @Override public String toString()
        {
            return "(" + name + ", " + dist + ")";
        }
    }

    /** Builds a DijkstraGraph from a set of DijkstraEdges */
    public DijkstraGraph(DijkstraEdge[] DijkstraEdges) {
        DijkstraGraph = new HashMap<>(DijkstraEdges.length);

        //one pass to find all vertices
        for (DijkstraEdge e : DijkstraEdges) {
            if (!DijkstraGraph.containsKey(e.v1)) DijkstraGraph.put(e.v1, new Vertex(e.v1));
            if (!DijkstraGraph.containsKey(e.v2)) DijkstraGraph.put(e.v2, new Vertex(e.v2));
        }

        //another pass to set neighbouring vertices
        for (DijkstraEdge e : DijkstraEdges) {
            DijkstraGraph.get(e.v1).neighbours.put(DijkstraGraph.get(e.v2), e.dist);
            //DijkstraGraph.get(e.v2).neighbours.put(DijkstraGraph.get(e.v1), e.dist); // also do this for an undirected DijkstraGraph
        }
    }

    /** Runs dijkstra using a specified source vertex */
    public void dijkstra(String startName) {
        if (!DijkstraGraph.containsKey(startName)) {
            System.err.printf("DijkstraGraph doesn't contain start vertex \"%s\"\n", startName);
            return;
        }
        final Vertex source = DijkstraGraph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();

        // set-up vertices
        for (Vertex v : DijkstraGraph.values()) {
            v.previous = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }

        dijkstra(q);
    }

    /** Implementation of dijkstra's algorithm using a binary heap. */
    private void dijkstra(final NavigableSet<Vertex> q) {
        Vertex u, v;
        while (!q.isEmpty()) {

            u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
            if (u.dist == Integer.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable

            //look at distances to each neighbour
            for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
                v = a.getKey(); //the neighbour in this iteration

                final int alternateDist = u.dist + a.getValue();
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    q.remove(v);
                    v.dist = alternateDist;
                    v.previous = u;
                    q.add(v);
                }
            }
        }
    }

    /** Prints a path from the source to the specified vertex */
    public String printPath(String endName) {
        if (!DijkstraGraph.containsKey(endName)) {
            System.err.printf("DijkstraGraph doesn't contain end vertex \"%s\"\n", endName);
            return " ";
        }

        return DijkstraGraph.get(endName).printPath();
    }
    /** Prints the path from the source to every vertex (output order is not guaranteed) */
    public void printAllPaths() {
        for (Vertex v : DijkstraGraph.values()) {
            v.printPath();
            System.out.println();
        }
    }
}