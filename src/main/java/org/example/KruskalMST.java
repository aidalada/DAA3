package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class KruskalMST {
    private Queue<Edge> mst;
    private long operationCount;
    private double weight;

    /**
     * @param G взвешенный граф
     */
    public KruskalMST(EdgeWeightedGraph G) {
        mst = new LinkedList<>();
        operationCount = 0;
        weight = 0.0;

        List<Edge> edges = new ArrayList<>();
        for (int v = 0; v < G.V(); v++) {
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (v < w) {
                    edges.add(e);
                }
            }
        }

        Collections.sort(edges);

        UnionFind uf = new UnionFind(G.V());

        for (Edge e : edges) {
            int v = e.either();
            int w = e.other(v);

            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.add(e);
                weight += e.weight();

                if (mst.size() == G.V() - 1) break; // Оптимизация
            }
            operationCount++;
        }

        operationCount += uf.getOperationCount();
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

    public long operationCount() {
        return operationCount;
    }
}