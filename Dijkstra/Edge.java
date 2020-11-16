package Dijkstra;

class Edge implements Comparable<Edge> {
    int src;
    int dst;
    int weight;

    public Edge(int src, int dst, int weight) {
        this.src = src;
        this.dst = dst;
        this.weight = weight;
    }

    public Edge(int dst, int weight) {
        this.dst = dst;
        this.weight = weight;
    }

    public String toString() {
        return String.format("%d -> %d : %d", src, dst, weight);
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

}