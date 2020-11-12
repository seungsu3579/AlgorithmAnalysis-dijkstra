package Dijkstra;

import java.util.ArrayList;
import java.util.List;

// @SuppressWarnings("all")
public class Main {

    public static void main(String[] args) {

        int nodeNum = 16000;
        int pivot = 10;
        String filename = String.format("./%d.graph", nodeNum);

        Graph g = new Graph(filename, nodeNum);

        // Find longest shortest path from pivot
        Object[] obj = g.dijkstra(pivot);
        int longest = (Integer) obj[0];
        List<Integer> external = (List<Integer>) obj[1];

        System.out.println(longest);
        // System.out.println(external);

    }

    public static void test() {
        int nodeNum = 11;
        List<Edge>[] graph = new ArrayList[nodeNum];
        for (int i = 0; i < nodeNum; i++) {
            graph[i] = new ArrayList<>();
        }
        graph[1].add(new Edge(2, 3));
        graph[1].add(new Edge(5, 4));
        graph[1].add(new Edge(4, 4));
        graph[2].add(new Edge(3, 2));
        graph[3].add(new Edge(4, 1));
        graph[4].add(new Edge(5, 2));
        graph[5].add(new Edge(6, 4));
        graph[4].add(new Edge(7, 6));
        graph[7].add(new Edge(6, 3));
        graph[3].add(new Edge(8, 3));
        graph[6].add(new Edge(8, 2));

        graph[2].add(new Edge(1, 3));
        graph[5].add(new Edge(1, 4));
        graph[4].add(new Edge(1, 4));
        graph[3].add(new Edge(2, 2));
        graph[4].add(new Edge(3, 1));
        graph[5].add(new Edge(4, 2));
        graph[6].add(new Edge(5, 4));
        graph[7].add(new Edge(4, 6));
        graph[6].add(new Edge(7, 3));
        graph[8].add(new Edge(3, 3));
        graph[8].add(new Edge(6, 2));

        graph[9].add(new Edge(10, 3));
        graph[10].add(new Edge(9, 3));
    }

}
