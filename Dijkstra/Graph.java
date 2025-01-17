package Dijkstra;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

@SuppressWarnings("all")
public class Graph {

    private String filename;
    private int nodeNum;
    private List<Edge>[] graph;
    private PriorityQueue<Edge> heuristic_order;

    // Constructor
    public Graph(String filename, int nodeNum) {
        this.filename = filename;
        this.nodeNum = nodeNum;
        this.graph = readGraph(filename, nodeNum);
    }

    public String getFilename() {
        return filename;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public List<Edge>[] getGraph() {
        return graph;
    }

    public PriorityQueue<Edge> getHeuristic_order() {
        return heuristic_order;
    }

    public List<Edge>[] readGraph(String filename, int nodeNum) {
        Edge[] tmp = new Edge[nodeNum];
        PriorityQueue<Edge> heuristic_order = new PriorityQueue<>();
        List<Edge>[] graph = new ArrayList[nodeNum];

        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<Edge>();
            tmp[i] = new Edge(i, i, 0);
        }

        try {
            File graph_file = new File(filename);
            FileReader filereader = new FileReader(graph_file);
            BufferedReader buffer = new BufferedReader(filereader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(" ");

                int src = Integer.parseInt(data[0]);
                int dst = Integer.parseInt(data[1]);
                int weight = Integer.parseInt(data[2]);

                // System.out.printf("%d - %d : %d\n", src, dst, weight);
                graph[src].add(new Edge(dst, weight));
                tmp[dst].weight += 1;

            }
            buffer.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        // for heuristic order to num3
        for (int i = 0; i < nodeNum; i++) {
            heuristic_order.add(tmp[i]);
        }
        this.heuristic_order = heuristic_order;

        return graph;
    }

    public static PriorityQueue<Edge> directionCount(String filename, int nodeNum) {
        Edge[] graph = new Edge[nodeNum];
        PriorityQueue<Edge> result = new PriorityQueue<>();

        for (int i = 0; i < nodeNum; i++) {
            graph[i] = new Edge(i, i, 0);
        }

        try {
            File graph_file = new File(filename);
            FileReader filereader = new FileReader(graph_file);
            BufferedReader buffer = new BufferedReader(filereader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(" ");

                int src = Integer.parseInt(data[0]);
                int dst = Integer.parseInt(data[1]);
                int weight = Integer.parseInt(data[2]);

                graph[dst].weight += 1;

            }
            buffer.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < nodeNum; i++) {
            result.add(graph[i]);
        }

        return result;
    }

    public Object[] dijkstra(int pivot) {

        PriorityQueue<Edge> pq = new PriorityQueue<>(); // priority queue that contains routes to be inspected.
        boolean[] flag = new boolean[nodeNum]; // array for checking whether this node is inspected.(index == node_id)
        Edge[] routeEdges = new Edge[nodeNum]; // array for recording all route from pivot node.

        // Initialize all routeEdge's weight and destination.
        for (int i = 0; i < routeEdges.length; i++) {
            if (i == pivot) {
                // Initialize start node's weight to 0.
                routeEdges[i] = new Edge(pivot, i, 0);
            } else {
                // Initialize node's weight that is not inspected to MAX_VALUE.
                routeEdges[i] = new Edge(pivot, i, Integer.MAX_VALUE);
            }
            pq.add(routeEdges[i]);
        }

        // loop infinitely, until all node is inspected.
        while (pq.size() != 0) {

            // Inspect node that has smallest weight by priority queue.
            // (Examine gradually spreading from the pivot node)
            Edge route = pq.poll();

            // Check nodes adjacent to the current node(route's destination).
            for (int i = 0; i < graph[route.dst].size(); i++) {
                Edge next = graph[route.dst].get(i);

                // Nodes in the external graph.
                // (After all nodes in the same graph are removed from pq, only nodes with
                // weight integerMax remain.)
                if (routeEdges[route.dst].weight + next.weight < 0) {
                    // In this case, program doesn't inspect nodes.
                    continue;
                }

                // If adjacent node is not inspected, it should be inspected.
                if (!flag[next.dst]) {

                    // If new route's weight is smaller than existing route's weight, update route's
                    // weight
                    // (= Find new route from pivot to destination!)
                    if (routeEdges[next.dst].weight > routeEdges[route.dst].weight + next.weight) {

                        // Reconstruct priorit queue, because weigt is changed.
                        pq.remove(routeEdges[next.dst]);
                        routeEdges[next.dst].weight = routeEdges[route.dst].weight + next.weight;
                        pq.add(routeEdges[next.dst]);
                    }
                }

            }

            // Check that node is inspected.
            flag[route.dst] = true;
        }

        // Find internal, external graph's nodes.
        List<Integer> internalNodes = new ArrayList<>();
        List<Integer> externalNodes = new ArrayList<>();
        Edge longest = new Edge(-1, -1, -1);

        for (int i = 0; i < routeEdges.length; i++) {
            //
            if (routeEdges[i].weight != Integer.MAX_VALUE) {
                internalNodes.add(routeEdges[i].dst);
                if (longest.weight < routeEdges[i].weight) {
                    longest = routeEdges[i];
                }
            } else {
                externalNodes.add(routeEdges[i].dst);
            }
        }

        // Return longest weight in internal graph and external graph's nodes;
        Object[] returnObj = new Object[4];
        returnObj[0] = internalNodes;
        returnObj[1] = externalNodes;
        returnObj[2] = routeEdges;
        returnObj[3] = longest;

        return returnObj;
    }

    public static Edge longest(Object obj) {
        Edge edge = (Edge) obj;
        return edge;
    }

    public static List<Integer> internalNodes(Object obj) {
        List<Integer> internal = (List<Integer>) obj;
        return internal;
    }

    public static List<Integer> externalNodes(Object obj) {
        List<Integer> external = (List<Integer>) obj;
        return external;
    }

    public static void printResult(int pivot, Object obj) {
        // int pivot : start point of Dijkstra
        // Object obj : type is Edge[], finded shortest route array
        // (= return[2] of dijkstra method)
        Edge[] routeEdges = null;
        try {
            routeEdges = (Edge[]) obj;
        } catch (Exception e) {
            System.out.println("Error!(method : printResult)");
            System.out.println("You should check that input type is Edge[].");
            return;
        }
        // Find external graph's nodes.
        List<Integer> externalNodes = new ArrayList<>();
        int longest = 0;

        System.out.println("+++++ Shortest Path +++++");
        for (int i = 0; i < routeEdges.length; i++) {
            if (routeEdges[i].weight != Integer.MAX_VALUE) {
                System.out.printf("From %d To %d : %d\n", pivot, routeEdges[i].dst, routeEdges[i].weight);
                if (longest < routeEdges[i].weight) {
                    longest = routeEdges[i].weight;
                }
            } else {
                externalNodes.add(routeEdges[i].dst);
            }
        }
        System.out.println();
        System.out.printf("From node(%d)\n", pivot);
        System.out.printf("longest shortest path : %d\n", longest);

        System.out.println();
        System.out.println("+++++ External node +++++");
        System.out.println(externalNodes);

    }

}
