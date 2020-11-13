package Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

// @SuppressWarnings("all")
public class Main {

    public static void main(String[] args) {

        int nodeNum = 16000;
        String filename = String.format("./%d.graph", nodeNum);

        Graph graph = new Graph(filename, nodeNum);

        findLongestShortestPath(graph);
        // statistic(graph);

    }

    // Show how many edges in node.
    public static void statistic(Graph graph) {

        List<Edge>[] g = graph.getGraph();

        List<Node> count = new ArrayList<Node>();
        for (int i = 0; i < graph.getNodeNum(); i++) {
            count.add(new Node(i, g[i].size()));
        }

        // Collections.sort(count);
        Collections.sort(count, Collections.reverseOrder());

        for (int i = 0; i < 20; i++) {
            System.out.println(count.get(i));
        }

    }

    public static void findLongestShortestPath(Graph graph) {

        int random_pivot = (int) (Math.random() * graph.getNodeNum());
        System.out.printf("첫 랜덤 피벗 : %d\n", random_pivot);

        CheckTable table = new CheckTable(graph.getNodeNum());

        List<Edge> result = new ArrayList<>();

        while (!table.isAllChecked()) {

            Object[] obj = graph.dijkstra(random_pivot);

            List<Integer> internals = Graph.internalNodes(obj[0]);
            List<Integer> externals = Graph.externalNodes(obj[1]);
            Edge longest = Graph.longest(obj[3]);

            result.add(longest);

            // Check inspected Nodes.
            table.check(internals);

            // Choose random node in external graph
            random_pivot = externals.get((int) (Math.random() * externals.size()));

            System.out.printf("검사한 노드 수 : %d\n", table.getCount());
            System.out.printf("남은 노드 수 : %d\n", table.getTableSize() - table.getCount());
            System.out.println();
        }

        System.out.println("모두 조사 완료!");

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

class Node implements Comparable<Node> {
    int src;
    int edgeNum;

    public Node(int src, int edgeNum) {
        this.src = src;
        this.edgeNum = edgeNum;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.edgeNum, other.edgeNum);
    }

    @Override
    public String toString() {
        return String.format("src : %d    num : %d", this.src, this.edgeNum);
    }

}