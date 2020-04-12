package lab2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        Graph graph = new Graph(new File("src/lab2/data"));
        System.out.println("АЛГОРИТМ РІШЕННЯ ЗАДАЧІ ЛИСТОНОШІ".toLowerCase());
        System.out.println("Вхідний граф:");
        System.out.println(graph);
        EulerianPath path = new EulerianPath(graph);

        System.out.println("Резкльтат:");
        System.out.println(path.getPath());
    }
}
