import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MSTSolver {

    public static void main(String[] args) {
        String inputFile = "input.json";
        String outputFile = "output.json";

        if (args.length >= 1) inputFile = args[0];
        if (args.length >= 2) outputFile = args[1];

        try {
            processGraphs(inputFile, outputFile);
            System.out.println("✅ Processing complete. Results written to " + outputFile);
        } catch (Exception e) {
            System.err.println("⚠️ Error processing files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void processGraphs(String inputFile, String outputFile) throws Exception {
        List<GraphData> graphDataList = parseInputFile(inputFile);
        List<ResultData> results = new ArrayList<>();

        for (GraphData graphData : graphDataList) {
            System.out.println("\nProcessing Graph " + graphData.id + "...");

            Graph graph = buildGraph(graphData);

            if (!graph.isConnected()) {
                System.out.println("⚠️ Warning: Graph " + graphData.id + " is not connected!");
            }

            PrimMST primMST = new PrimMST(graph);
            KruskalMST kruskalMST = new KruskalMST(graph);

            ResultData result = new ResultData();
            result.graphId = graphData.id;
            result.vertices = graph.V();
            result.totalEdges = graph.E();

            result.primEdges = primMST.edges();
            result.primCost = primMST.weight();
            result.primOps = primMST.operationsCount();
            result.primTime = primMST.executionTime();

            result.kruskalEdges = kruskalMST.edges();
            result.kruskalCost = kruskalMST.weight();
            result.kruskalOps = kruskalMST.operationsCount();
            result.kruskalTime = kruskalMST.executionTime();

            results.add(result);

            System.out.println("Prim’s MST cost: " + primMST.weight());
            System.out.println("Kruskal’s MST cost: " + kruskalMST.weight());
            System.out.println("Costs match: " + (Math.abs(primMST.weight() - kruskalMST.weight()) < 0.01));
        }

        writeOutput(outputFile, results);
    }

    private static Graph buildGraph(GraphData graphData) {
        Graph graph = new Graph(graphData.nodes);
        for (EdgeData edgeData : graphData.edges) {
            graph.addEdge(new Edge(edgeData.from, edgeData.to, edgeData.weight));
        }
        return graph;
    }

    private static List<GraphData> parseInputFile(String filename) throws IOException {
        InputStream inputStream = MSTSolver.class.getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) throw new FileNotFoundException("❌ File " + filename + " not found in resources.");
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) content.append(line.trim());
        }
        return parseGraphs(content.toString());
    }

    private static List<GraphData> parseGraphs(String json) {
        List<GraphData> graphs = new ArrayList<>();
        int graphsStart = json.indexOf("\"graphs\"");
        if (graphsStart == -1) return graphs;

        int arrayStart = json.indexOf("[", graphsStart);
        int arrayEnd = findMatchingBracket(json, arrayStart, '[', ']');
        String graphsContent = json.substring(arrayStart + 1, arrayEnd);

        int pos = 0;
        while (pos < graphsContent.length()) {
            int objStart = graphsContent.indexOf("{", pos);
            if (objStart == -1) break;
            int objEnd = findMatchingBracket(graphsContent, objStart, '{', '}');
            String graphObj = graphsContent.substring(objStart, objEnd + 1);
            GraphData graph = parseGraph(graphObj);
            if (graph != null) graphs.add(graph);
            pos = objEnd + 1;
        }
        return graphs;
    }

    private static GraphData parseGraph(String graphJson) {
        GraphData graph = new GraphData();
        graph.id = extractInt(graphJson, "\"id\"");
        int nodesStart = graphJson.indexOf("\"nodes\"");
        if (nodesStart != -1) {
            int arrayStart = graphJson.indexOf("[", nodesStart);
            int arrayEnd = findMatchingBracket(graphJson, arrayStart, '[', ']');
            String[] nodeTokens = graphJson.substring(arrayStart + 1, arrayEnd).split(",");
            for (String token : nodeTokens) {
                String node = token.trim().replaceAll("\"", "");
                if (!node.isEmpty()) graph.nodes.add(node);
            }
        }
        int edgesStart = graphJson.indexOf("\"edges\"");
        if (edgesStart != -1) {
            int arrayStart = graphJson.indexOf("[", edgesStart);
            int arrayEnd = findMatchingBracket(graphJson, arrayStart, '[', ']');
            String edgesArray = graphJson.substring(arrayStart + 1, arrayEnd);
            int pos = 0;
            while (pos < edgesArray.length()) {
                int objStart = edgesArray.indexOf("{", pos);
                if (objStart == -1) break;
                int objEnd = findMatchingBracket(edgesArray, objStart, '{', '}');
                String edgeObj = edgesArray.substring(objStart, objEnd + 1);
                EdgeData edge = new EdgeData();
                edge.from = extractString(edgeObj, "\"from\"");
                edge.to = extractString(edgeObj, "\"to\"");
                edge.weight = extractDouble(edgeObj, "\"weight\"");
                graph.edges.add(edge);
                pos = objEnd + 1;
            }
        }
        return graph;
    }

    private static int findMatchingBracket(String str, int start, char open, char close) {
        int count = 1;
        for (int i = start + 1; i < str.length(); i++) {
            if (str.charAt(i) == open) count++;
            if (str.charAt(i) == close) count--;
            if (count == 0) return i;
        }
        return str.length() - 1;
    }

    private static int extractInt(String json, String key) {
        int keyPos = json.indexOf(key);
        if (keyPos == -1) return 0;
        int colonPos = json.indexOf(":", keyPos);
        int commaPos = json.indexOf(",", colonPos);
        if (commaPos == -1) commaPos = json.indexOf("}", colonPos);
        return Integer.parseInt(json.substring(colonPos + 1, commaPos).trim());
    }

    private static double extractDouble(String json, String key) {
        int keyPos = json.indexOf(key);
        if (keyPos == -1) return 0.0;
        int colonPos = json.indexOf(":", keyPos);
        int commaPos = json.indexOf(",", colonPos);
        if (commaPos == -1) commaPos = json.indexOf("}", colonPos);
        return Double.parseDouble(json.substring(colonPos + 1, commaPos).trim());
    }

    private static String extractString(String json, String key) {
        int keyPos = json.indexOf(key);
        if (keyPos == -1) return "";
        int colonPos = json.indexOf(":", keyPos);
        int quoteStart = json.indexOf("\"", colonPos);
        int quoteEnd = json.indexOf("\"", quoteStart + 1);
        return json.substring(quoteStart + 1, quoteEnd);
    }

    private static void writeOutput(String filename, List<ResultData> results) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("{\n  \"results\": [\n");
            for (int i = 0; i < results.size(); i++) {
                ResultData result = results.get(i);
                writer.write("    {\n");
                writer.write("      \"graph_id\": " + result.graphId + ",\n");
                writer.write("      \"input_stats\": {\n");
                writer.write("        \"vertices\": " + result.vertices + ",\n");
                writer.write("        \"edges\": " + result.totalEdges + "\n");
                writer.write("      },\n");

                writer.write("      \"prim\": {\n");
                writeAlgorithmResult(writer, result.primEdges, result.primCost, result.primOps, result.primTime);
                writer.write("      },\n");

                writer.write("      \"kruskal\": {\n");
                writeAlgorithmResult(writer, result.kruskalEdges, result.kruskalCost, result.kruskalOps, result.kruskalTime);
                writer.write("      }\n");

                writer.write("    }");
                if (i < results.size() - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("  ]\n}");
        }
    }

    private static void writeAlgorithmResult(BufferedWriter writer, List<Edge> edges,
                                             double cost, int ops, double time) throws IOException {
        writer.write("        \"mst_edges\": [\n");
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            writer.write("          {\"from\": \"" + e.either() + "\", ");
            writer.write("\"to\": \"" + e.other(e.either()) + "\", ");
            writer.write("\"weight\": " + e.weight() + "}");
            if (i < edges.size() - 1) writer.write(",");
            writer.write("\n");
        }
        writer.write("        ],\n");
        writer.write("        \"total_cost\": " + Math.round(cost * 100.0) / 100.0 + ",\n");
        writer.write("        \"operations_count\": " + ops + ",\n");
        writer.write("        \"execution_time_ms\": " + Math.round(time * 100.0) / 100.0 + "\n");
    }

    static class GraphData {
        int id;
        List<String> nodes = new ArrayList<>();
        List<EdgeData> edges = new ArrayList<>();
    }

    static class EdgeData {
        String from;
        String to;
        double weight;
    }

    static class ResultData {
        int graphId;
        int vertices;
        int totalEdges;
        List<Edge> primEdges;
        double primCost;
        int primOps;
        double primTime;
        List<Edge> kruskalEdges;
        double kruskalCost;
        int kruskalOps;
        double kruskalTime;
    }
}
