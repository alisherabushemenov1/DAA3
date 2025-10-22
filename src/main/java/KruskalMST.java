import java.util.*;

public class KruskalMST {
    private static final double EPSILON = 1.0E-12;

    private List<Edge> mstEdges;
    private double totalCost;
    private int operationsCount;
    private double executionTimeMs;
    private boolean isConnected;

    public KruskalMST(Graph G) {
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

        List<Edge> edges = new ArrayList<>();
        for (Edge e : G.edges()) {
            edges.add(e);
            operationsCount++;
        }

        Collections.sort(edges);
        operationsCount += edges.size() * (int)(Math.log(edges.size()) / Math.log(2));

        UnionFind uf = new UnionFind(G.V());

        for (Edge e : edges) {
            String v = e.either();
            String w = e.other(v);
            int vIdx = G.getNodeIndex(v);
            int wIdx = G.getNodeIndex(w);

            operationsCount++;
            operationsCount++;

            if (!uf.connected(vIdx, wIdx)) {
                uf.union(vIdx, wIdx);
                mstEdges.add(e);
                totalCost += e.weight();
                operationsCount++;

                if (mstEdges.size() == G.V() - 1) {
                    break;
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

    public boolean check(Graph G) {
        double total = 0.0;
        for (Edge e : edges()) {
            total += e.weight();
        }
        if (Math.abs(total - weight()) > EPSILON) {
            return false;
        }

        if (isConnected && edges().size() != G.V() - 1) {
            return false;
        }

        UnionFind uf = new UnionFind(G.V());
        for (Edge e : edges()) {
            String v = e.either();
            String w = e.other(v);
            int vIdx = G.getNodeIndex(v);
            int wIdx = G.getNodeIndex(w);

            if (uf.connected(vIdx, wIdx)) {
                return false;
            }
            uf.union(vIdx, wIdx);
        }

        return true;
    }
}
