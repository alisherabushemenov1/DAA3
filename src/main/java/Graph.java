import java.util.*;

public class Graph {
    private final Map<String, Integer> nodeToIndex;
    private final Map<Integer, String> indexToNode;
    private final int V;
    private int E;
    private final List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public Graph(List<String> nodes) {
        this.V = nodes.size();
        this.E = 0;
        this.nodeToIndex = new HashMap<>();
        this.indexToNode = new HashMap<>();
        this.adj = (List<Edge>[]) new ArrayList[V];

        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
            nodeToIndex.put(nodes.get(i), i);
            indexToNode.put(i, nodes.get(i));
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e) {
        String v = e.either();
        String w = e.other(v);

        if (!nodeToIndex.containsKey(v) || !nodeToIndex.containsKey(w)) {
            throw new IllegalArgumentException("Vertex not in graph");
        }

        int vIndex = nodeToIndex.get(v);
        int wIndex = nodeToIndex.get(w);

        adj[vIndex].add(e);
        adj[wIndex].add(e);
        E++;
    }

    public Iterable<Edge> adj(String v) {
        if (!nodeToIndex.containsKey(v)) {
            throw new IllegalArgumentException("Vertex not in graph");
        }
        return adj[nodeToIndex.get(v)];
    }

    public Iterable<Edge> edges() {
        List<Edge> list = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) {
                if (nodeToIndex.get(e.other(indexToNode.get(v))) > v) {
                    list.add(e);
                }
            }
        }
        return list;
    }

    public String getNodeName(int index) {
        return indexToNode.get(index);
    }

    public int getNodeIndex(String name) {
        return nodeToIndex.get(name);
    }

    public boolean isConnected() {
        if (V == 0) return true;

        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        visited[0] = true;
        int count = 1;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (Edge e : adj[v]) {
                int w = nodeToIndex.get(e.other(indexToNode.get(v)));
                if (!visited[w]) {
                    visited[w] = true;
                    queue.add(w);
                    count++;
                }
            }
        }

        return count == V;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V).append(" vertices, ").append(E).append(" edges\n");
        for (Edge e : edges()) {
            s.append(e).append("\n");
        }
        return s.toString();
    }
}
