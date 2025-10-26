package org.example;

public class AlgorithmMetrics {
    public String algorithm_name;

    public double mst_weight;
    public long execution_time_ms;
    public long operation_count;

    public int v_count;
    public int e_count_mst;

    public AlgorithmMetrics(String name, double weight, long timeMs, long ops, int V, int E_mst) {
        this.algorithm_name = name;
        this.mst_weight = weight;
        this.execution_time_ms = timeMs;
        this.operation_count = ops;
        this.v_count = V;
        this.e_count_mst = E_mst;
    }
}