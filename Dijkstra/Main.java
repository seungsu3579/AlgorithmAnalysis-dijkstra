package Dijkstra;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

@SuppressWarnings("all")
public class Main {

    public static void main(String[] args) {

        int nodeNum = 1000000;
        String filename = String.format("./%d.graph", nodeNum);

        Graph graph = new Graph(filename, nodeNum);

        heuristic_findLongestShortestPath(graph);
        // findLongestShortestPath(graph);

    }

    public static void heuristic_findLongestShortestPath(Graph graph) {

        // 16000 19s
        // 32000 2m 37s

        PriorityQueue<Edge> result = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Edge> search_order = graph.getHeuristic_order();

        CheckTable table = new CheckTable(graph.getNodeNum());

        while (!table.isAllChecked()) {

            Edge edge = search_order.poll();

            if (!table.show(edge.dst)) {
                int pivot = edge.dst;
                Object[] obj = graph.dijkstra(pivot);

                List<Integer> internals = Graph.internalNodes(obj[0]);
                Edge longest = Graph.longest(obj[3]);

                result.add(longest);
                Edge e = result.peek();

                // Check inspected Nodes.
                table.check(internals);

                int d = (int) ((table.getCount() * 100 / table.getTableSize()));

                System.out.printf(" Progress : %7d/%7d ( %3d )  | ", table.getCount(), table.getTableSize(), d);
                System.out.printf("Longest Shortest Path : %20s \r", e);

            }

        }

        System.out.printf("\n\n");

        System.out.println("+++++ longest shortest path rank +++++");

        for (int i = 0; i < 20; i++) {
            Edge edge = result.poll();
            System.out.printf(" %-2d. %s\n", i + 1, edge);
        }

        System.out.println("\nComplete!\n");

    }

    public static void findLongestShortestPath(Graph graph) {

        int pivot = (int) (Math.random() * graph.getNodeNum());

        CheckTable table = new CheckTable(graph.getNodeNum());

        PriorityQueue<Edge> result = new PriorityQueue<>(Comparator.reverseOrder());

        System.out.println("\n+++++ calculating +++++");
        while (!table.isAllChecked()) {

            Object[] obj = graph.dijkstra(pivot);

            List<Integer> internals = Graph.internalNodes(obj[0]);
            Edge longest = Graph.longest(obj[3]);

            result.add(longest);
            Edge e = result.peek();

            // Check inspected Nodes.
            table.check(internals);

            // Choose node in external graph
            pivot = table.getUnchecked();

            int d = (int) ((table.getCount() * 100 / table.getTableSize()));

            System.out.printf(" Progress : %7d/%7d ( %3d )  | ", table.getCount(), table.getTableSize(), d);
            System.out.printf("Longest Shortest Path : %20s \r", e);
        }

        System.out.printf("\n\n");

        System.out.println("+++++ longest shortest path rank +++++");

        for (int i = 0; i < 20; i++) {
            Edge edge = result.poll();
            System.out.printf(" %-2d. %s\n", i + 1, edge);
        }

        System.out.println("\nComplete!\n");

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
