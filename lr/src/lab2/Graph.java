package lab2;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph implements Comparable<Graph>{

    public ArrayList<Edge> edges;
    ArrayList<Character> pointsList;

    public Graph() {
        this.edges = new ArrayList<>();
    }

    public Graph(ArrayList edges) {
        this.edges = edges;
        updatePointList();
    }

    public Graph(File file) throws IOException {
        ArrayList<Edge> edges = new ArrayList<>();
        FileReader fr= new FileReader(file);
        Scanner scan = new Scanner(fr);

        while (scan.hasNext()){
            Edge edge = new Edge();
            edge.setA(scan.next().charAt(0));
            edge.setB(scan.next().charAt(0));
            edge.setWeight(Integer.parseInt(scan.next()));

            edges.add(edge);
        }
        fr.close();

        this.edges = edges;
        updatePointList();
    }

    private void updatePointList(){
        this.pointsList = new ArrayList<>();
        for(char c = 'A'; c < 'Z'; c++){
            for(int i=0; i<this.edges.size(); i++){
                if(this.edges.get(i).havePoint(c)){
                    this.pointsList.add(c);
                    break;
                }
            }
        }
    }

    public void addEdge(Edge edge){
        this.edges.add(edge);
        updatePointList();
    }

    public void addEdges(ArrayList<Edge> edges){
        for (Edge edge : edges){
            this.edges.add(edge);
        }
        updatePointList();
    }

    public void removeEdges(ArrayList<Edge> edges){
        for (Edge edge : edges){
            this.edges.remove(edge);
        }
        updatePointList();
    }

    public boolean havePoint(char a){
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).havePoint(a)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "edges=" + edges.toString() +
                '}';
    }

    public int getWeight() {
        int weight = 0;

        for (Edge edge : edges) {
            weight += edge.getWeight();
        }

        return weight;
    }

    public ArrayList<Character> getPointsList() {
        return (ArrayList<Character>) pointsList.clone();
    }

    public char getStartPoint() {
        for (char point  = 'A'; point < 'Z'; point++){
            if (pointsList.contains(point)){
                return point;
            }
        }
        return ' ';
    }

    public char getEndPoint(){
        char c = 'A';
        a: for(; c < 'Z'; c++){
            for(int i=0; i<this.edges.size(); i++){
                if(this.edges.get(i).havePoint(c)){
                    continue a;
                }
            }
            break;
        }
        return --c;
    }

    ArrayList<Character> getAdjacentPoints(char a){
        ArrayList<Character> adjacentPoints =  new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.havePoint(a)) {
                adjacentPoints.add(edge.getA() == a ? edge.getB() : edge.getA());
            }
        }

        return adjacentPoints;
    }

    public ArrayList<Edge> getAdjacentEdges(char a){
        ArrayList<Edge> adjacentEdges =  new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.havePoint(a)) {
                adjacentEdges.add(edge);
            }
        }

        return adjacentEdges;
    }

    @Override
    public int compareTo(Graph o) {
        if (this.getWeight() == o.getWeight()) {
            return 0;
        } else if (this.getWeight() < o.getWeight()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ArrayList<rgr.Edge> edges1 = (ArrayList<rgr.Edge>) edges.clone();
        return new Graph(edges1);
    }
}
