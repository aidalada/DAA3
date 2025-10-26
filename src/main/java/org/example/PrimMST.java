package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class PrimMST {
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;
    private long operationCount;

    /**
     * @param G взвешенный граф
     */
    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<>(G.V());
        operationCount = 0;

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) prim(G, v);
    }

    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);

        while (!pq.isEmpty()) {
            operationCount++;
            int v = pq.delMin();
            scan(G, v);
        }
    }

    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;

        for (Edge e : G.adj(v)) {
            operationCount++;
            int w = e.other(v);

            if (marked[w]) continue;

            if (e.weight() < distTo[w]) {
                operationCount++;
                distTo[w] = e.weight();
                edgeTo[w] = e;

                if (pq.contains(w)) {
                    pq.decreaseKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }


    public Iterable<Edge> edges() {
        Queue<Edge> mst = new LinkedList<>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.add(e);
            }
        }
        return mst;
    }


    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }

    public long operationCount() {
        return operationCount;
    }
}