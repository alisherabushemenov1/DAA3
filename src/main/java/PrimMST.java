import java.util.*;

public class PrimMST {
    private static final double EPSILON = 1.0E-12;

    private List<Edge> mstEdges;
    private double totalCost;
    private int operationsCount;
    private double executionTimeMs;
    private boolean isConnected;

    public PrimMST(Graph G) {
        long startTime = System.nanoTime();
        this.mstEdges = new ArrayList<>();
        this.totalCost = 0.0;
        this.operationsCount = 0;
        this.isConnected = G.isConnected();

        if (!isConnected || G.V() == 0) {
            long endTime = System.nanoTime();
            this.executionTimeMs = (endTime - startTime) / 1_000_000.0;
            return;
        }

        Map<String, Double> distTo = new HashMap<>();
        Map<String, Edge> edgeTo = new HashMap<>();
        Set<String> marked = new HashSet<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();

        // Initialize distances
        for (int i = 0; i < G.V(); i++) {
            String node = G.getNodeName(i);
            distTo.put(node, Double.POSITIVE_INFINITY);
            operationsCount++; // initialization operation
        }

        // Start from first vertex
        String startNode = G.getNodeName(0);
        distTo.put(startNode, 0.0);
        pq.offer(new NodeDistance(startNode, 0.0));
        operationsCount++; // insert operation

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            String v = current.node;
            operationsCount++; // delete-min operation

            if (marked.contains(v)) continue;
            marked.add(v);

            // Add edge to MST (except for starting vertex)
            if (edgeTo.containsKey(v)) {
                mstEdges.add(edgeTo.get(v));
                totalCost += edgeTo.get(v).weight();
            }

            // Scan adjacent edges
            for (Edge e : G.adj(v)) {
                String w = e.other(v);
                operationsCount++; // edge examination

                if (marked.contains(w)) continue;

                double weight = e.weight();
                operationsCount++; // comparison operation

                if (weight < distTo.get(w)) {
                    distTo.put(w, weight);
                    edgeTo.put(w, e);
                    pq.offer(new NodeDistance(w, weight));
                    operationsCount++; // priority queue update
                }
            }
        }

        long endTime = System.nanoTime();
        this.executionTimeMs = (endTime - startTime) / 1_000_000.0;
    }

    public List<Edge> edges() {
        return new ArrayList<>(mstEdges);
    }

    public double weight() {
        return totalCost;
    }

    public int operationsCount() {
        return operationsCount;
    }

    public double executionTime() {
        return executionTimeMs;
    }

    public boolean isConnected() {
        return isConnected;
    }

    private static class NodeDistance implements Comparable<NodeDistance> {
        String node;
        double distance;

        NodeDistance(String node, double distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    public boolean check(Graph G) {
        // Check total weight
        double total = 0.0;
        for (Edge e : edges()) {
            total += e.weight();
        }
        if (Math.abs(total - weight()) > EPSILON) {
            return false;
        }

        // Check number of edges
        if (isConnected && edges().size() != G.V() - 1) {
            return false;
        }

        // Check acyclic using Union-Find
        UnionFind uf = new UnionFind(G.V());
        for (Edge e : edges()) {
            String v = e.either();
            String w = e.other(v);
            int vIdx = G.getNodeIndex(v);
            int wIdx = G.getNodeIndex(w);

            if (uf.connected(vIdx, wIdx)) {
                return false; // cycle detected
            }
            uf.union(vIdx, wIdx);
        }

        return true;
    }
}
