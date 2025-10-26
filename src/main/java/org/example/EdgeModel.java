package org.example;

public class EdgeModel {
    public int v;
    public int w;
    public double weight;

    public EdgeModel() {}

    public Edge toEdge() {
        return new Edge(v, w, weight);
    }
}