import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class MSTTest {

    @Test
    public void testSmallGraph_SameCost() {
        // Graph with 5 vertices
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        Graph graph = new Graph(nodes);

        graph.addEdge(new Edge("A", "B", 4));
        graph.addEdge(new Edge("A", "C", 3));
        graph.addEdge(new Edge("B", "C", 2));
        graph.addEdge(new Edge("B", "D", 5));
        graph.addEdge(new Edge("C", "D", 7));
        graph.addEdge(new Edge("C", "E", 8));
        graph.addEdge(new Edge("D", "E", 6));

        PrimMST primMST = new PrimMST(graph);
        KruskalMST kruskalMST = new KruskalMST(graph);

        assertEquals(primMST.weight(), kruskalMST.weight(), 0.01,
                "Prim and Kruskal should produce same total cost");
        System.out.println("✓ Test passed: Same cost for both algorithms");
    }

    @Test
    public void testCorrectNumberOfEdges() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        Graph graph = new Graph(nodes);

        graph.addEdge(new Edge("A", "B", 1));
        graph.addEdge(new Edge("A", "C", 4));
        graph.addEdge(new Edge("B", "C", 2));
        graph.addEdge(new Edge("C", "D", 3));
        graph.addEdge(new Edge("B", "D", 5));

        PrimMST primMST = new PrimMST(graph);
        KruskalMST kruskalMST = new KruskalMST(graph);

        int expectedEdges = graph.V() - 1;
        assertEquals(expectedEdges, primMST.edges().size(),
                "Prim's MST should have V-1 edges");
        assertEquals(expectedEdges, kruskalMST.edges().size(),
                "Kruskal's MST should have V-1 edges");
        System.out.println("✓ Test passed: Correct number of edges (V-1)");
    }

    @Test
    public void testMSTIsAcyclic() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        Graph graph = new Graph(nodes);

        graph.addEdge(new Edge("A", "B", 4));
        graph.addEdge(new Edge("A", "C", 3));
        graph.addEdge(new Edge("B", "C", 2));
        graph.addEdge(new Edge("B", "D", 5));
        graph.addEdge(new Edge("C", "D", 7));
        graph.addEdge(new Edge("C", "E", 8));
        graph.addEdge(new Edge("D", "E", 6));

        PrimMST primMST = new PrimMST(graph);
        KruskalMST kruskalMST = new KruskalMST(graph);

        assertTrue(isAcyclic(graph, primMST.edges()),
                "Prim's MST should be acyclic");
        assertTrue(isAcyclic(graph, kruskalMST.edges()),
                "Kruskal's MST should be acyclic");
        System.out.println("✓ Test passed: MST is acyclic");
    }

    @Test
    public void testMSTConnectsAllVertices() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        Graph graph = new Graph(nodes);

        graph.addEdge(new Edge("A", "B", 1));
        graph.addEdge(new Edge("B", "C", 2));
        graph.addEdge(new Edge("C", "D", 3));
        graph.addEdge(new Edge("A", "D", 10));

        PrimMST primMST = new PrimMST(graph);
        KruskalMST kruskalMST = new KruskalMST(graph);

        assertTrue(connectsAllVertices(graph, primMST.edges()),
                "Prim's MST should connect all vertices");
        assertTrue(connectsAllVertices(graph, kruskalMST.edges()),
                "Kruskal's MST should connect all vertices");
        System.out.println("✓ Test passed: MST connects all vertices");
    }

    @Test
    public void testDisconnectedGraph() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        Graph graph = new Graph(nodes);

        // Only connect A-B and C-D, leaving two components
        graph.addEdge(new Edge("A", "B", 1));
        graph.addEdge(new Edge("C", "D", 2));

        assertFalse(graph.isConnected(), "Graph should be disconnected");

        PrimMST primMST = new PrimMST(graph);
        KruskalMST kruskalMST = new KruskalMST(graph);

        assertFalse(primMST.isConnected(),
                "Prim should detect disconnected graph");
        assertFalse(kruskalMST.isConnected(),
                "Kruskal should detect disconnected graph");
        System.out.println("✓ Test passed: Disconnected graph handled correctly");
    }

    @Test
    public void testExecutionTimeIsNonNegative() {
        List<String> nodes = Arrays.asList("A", "B", "C");
        Graph graph = new Graph(nodes);

        graph.addEdge(new Edge("A", "B", 1));
        graph.addEdge(new Edge("B", "C", 2));

        PrimMST primMST = new PrimMST(graph);
        KruskalMST kruskalMST = new KruskalMST(graph);

        assertTrue(primMST.executionTime() >= 0,
                "Prim execution time should be non-negative");
        assertTrue(kruskalMST.executionTime() >= 0,
                "Kruskal execution time should be non-negative");
        System.out.println("✓ Test passed: Execution time is non-negative");
    }

    @Test
    public void testOperationCountIsNonNegative() {
        List<String> nodes = Arrays.asList("A", "B", "C");
        Graph graph = new Graph(nodes);

        graph.addEdge(new Edge("A", "B", 1));
        graph.addEdge(new Edge("B", "C", 2));

        PrimMST primMST = new PrimMST(graph);
        KruskalMST kruskalMST = new KruskalMST(graph);

        assertTrue(primMST.operationsCount() > 0,
                "Prim operation count should be positive");
        assertTrue(kruskalMST.operationsCount() > 0,
                "Kruskal operation count should be positive");
        System.out.println("✓ Test passed: Operation count is positive");
    }

    @Test
    public void testResultsAreReproducible() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        Graph graph1 = new Graph(nodes);
        Graph graph2 = new Graph(nodes);

        graph1.addEdge(new Edge("A", "B", 1));
        graph1.addEdge(new Edge("B", "C", 2));
        graph1.addEdge(new Edge("C", "D", 3));

        graph2.addEdge(new Edge("A", "B", 1));
        graph2.addEdge(new Edge("B", "C", 2));
        graph2.addEdge(new Edge("C", "D", 3));

        PrimMST prim1 = new PrimMST(graph1);
        PrimMST prim2 = new PrimMST(graph2);

        KruskalMST kruskal1 = new KruskalMST(graph1);
        KruskalMST kruskal2 = new KruskalMST(graph2);

        assertEquals(prim1.weight(), prim2.weight(), 0.01,
                "Prim should produce same cost for same input");
        assertEquals(kruskal1.weight(), kruskal2.weight(), 0.01,
                "Kruskal should produce same cost for same input");
        System.out.println("✓ Test passed: Results are reproducible");
    }

    @Test
    public void testMediumGraph() {
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            nodes.add("V" + i);
        }
        Graph graph = new Graph(nodes);

        // Create a connected graph with 15 edges
        graph.addEdge(new Edge("V0", "V1", 2));
        graph.addEdge(new Edge("V0", "V2", 3));
        graph.addEdge(new Edge("V1", "V2", 1));
        graph.addEdge(new Edge("V1", "V3", 4));
        graph.addEdge(new Edge("V2", "V4", 5));
        graph.addEdge(new Edge("V3", "V5", 2));
        graph.addEdge(new Edge("V4", "V5", 3));
        graph.addEdge(new Edge("V4", "V6", 6));
        graph.addEdge(new Edge("V5", "V7", 4));
        graph.addEdge(new Edge("V6", "V7", 2));
        graph.addEdge(new Edge("V6", "V8", 7));
        graph.addEdge(new Edge("V7", "V9", 3));
        graph.addEdge(new Edge("V8", "V9", 5));
        graph.addEdge(new Edge("V3", "V4", 8));
        graph.addEdge(new Edge("V2", "V3", 6));

        PrimMST primMST = new PrimMST(graph);
        KruskalMST kruskalMST = new KruskalMST(graph);

        assertEquals(primMST.weight(), kruskalMST.weight(), 0.01);
        assertEquals(9, primMST.edges().size());
        assertEquals(9, kruskalMST.edges().size());
        System.out.println("✓ Test passed: Medium graph (10 vertices)");
    }

    // Helper methods
    private boolean isAcyclic(Graph graph, List<Edge> edges) {
        UnionFind uf = new UnionFind(graph.V());
        for (Edge e : edges) {
            String v = e.either();
            String w = e.other(v);
            int vIdx = graph.getNodeIndex(v);
            int wIdx = graph.getNodeIndex(w);

            if (uf.connected(vIdx, wIdx)) {
                return false; // Cycle detected
            }
            uf.union(vIdx, wIdx);
        }
        return true;
    }

    private boolean connectsAllVertices(Graph graph, List<Edge> edges) {
        if (edges.size() != graph.V() - 1) {
            return false;
        }

        UnionFind uf = new UnionFind(graph.V());
        for (Edge e : edges) {
            String v = e.either();
            String w = e.other(v);
            int vIdx = graph.getNodeIndex(v);
            int wIdx = graph.getNodeIndex(w);
            uf.union(vIdx, wIdx);
        }

        return uf.count() == 1;
    }

    public static void main(String[] args) {
        MSTTest test = new MSTTest();

        System.out.println("Running MST Tests...\n");

        try {
            test.testSmallGraph_SameCost();
            test.testCorrectNumberOfEdges();
            test.testMSTIsAcyclic();
            test.testMSTConnectsAllVertices();
            test.testDisconnectedGraph();
            test.testExecutionTimeIsNonNegative();
            test.testOperationCountIsNonNegative();
            test.testResultsAreReproducible();
            test.testMediumGraph();

            System.out.println("\n✅ All tests passed!");
        } catch (AssertionError e) {
            System.out.println("\n❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
