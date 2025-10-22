public class Edge implements Comparable<Edge> {
    private final String v;
    private final String w;
    private final double weight;

    public Edge(String v, String w, double weight) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("Vertex names cannot be null");
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        }
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public String either() {
        return v;
    }

    public String other(String vertex) {
        if (vertex.equals(v)) return w;
        else if (vertex.equals(w)) return v;
        else throw new IllegalArgumentException("Invalid vertex");
    }

    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    @Override
    public String toString() {
        return String.format("%s-%s %.2f", v, w, weight);
    }
}
