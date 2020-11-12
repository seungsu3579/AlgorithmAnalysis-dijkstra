package Dijkstra;

import java.util.ArrayList;
import java.util.List;

// @SuppressWarnings("all")
public class Main {

    public static void main(String[] args) {

        int nodeNum = 16000;
        int pivot = 10;
        String filename = String.format("./%d.graph", nodeNum);

        Graph graph = new Graph(filename, nodeNum);

        // Find longest shortest path from pivot
        Object[] obj = graph.dijkstra(pivot);

        int longest = Graph.longest(obj[0]);
        List<Integer> externals = Graph.externalNodes(obj[1]);

    }

    public static void test() {
        int nodeNum = 11;
        int pivot = 1;
        String filename = "./test.txt";

        Graph g = new Graph(filename, nodeNum);

        // Solve by Dijkstra
        Object[] obj = g.dijkstra(pivot);

        // Print Results
        Graph.printResult(pivot, obj[2]);

    }

}

class FindLS {
    public FindLS() {
    }

    public static void findLongestShortestPath(Graph graph) {

    }
}