package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {

    ArrayList<Edge> edges;

    public Graph() {
        this.edges = new ArrayList<>();
    }

    public Graph(ArrayList edges) {
        this.edges = edges;
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
    }

    public ArrayList getPointList(){
        ArrayList<Character> pointsList = new ArrayList<>();
        for(char c = 'A'; c < 'Z'; c++){

            for(int i=0; i<this.edges.size(); i++){
                if(this.edges.get(i).havePoint(c)){
                    pointsList.add(c);
                    break;
                }
            }
        }
        return pointsList;
    }

    public void addEdge(Edge edge){
        this.edges.add(edge);
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


}
