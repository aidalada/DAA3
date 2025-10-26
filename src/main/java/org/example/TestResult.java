package org.example;

import java.util.ArrayList;
import java.util.List;

public class TestResult {
    public String graph_id;
    public int V_initial;
    public int E_initial;

    public List<AlgorithmMetrics> results;

    public TestResult(String id, int V, int E) {
        this.graph_id = id;
        this.V_initial = V;
        this.E_initial = E;
        this.results = new ArrayList<>();
    }

    /**
     * @param name
     * @param weight
     * @param timeNs
     * @param ops
     */
    public void addResult(String name, double weight, long timeNs, long ops) {
        long timeMs = timeNs / 1_000_000;

        int mst_edges = 0;
        if (weight > 0.0) {
            mst_edges = V_initial - 1;
        }

        results.add(new AlgorithmMetrics(name, weight, timeMs, ops, V_initial, mst_edges));
    }
}