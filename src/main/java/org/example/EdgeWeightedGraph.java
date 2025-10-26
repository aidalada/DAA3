package org.example;

import java.util.LinkedList;
import java.util.List;

public class EdgeWeightedGraph {
    private final int V;
    private final List<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        adj = (List<Edge>[]) new List[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedList<>();
        }
    }

    public int V() {
        return V;
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }
}