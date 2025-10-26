package org.example;

public class UnionFind {
    private int[] parent;
    private int count;
    private long findCount;
    private long unionCount;

    public UnionFind(int N) {
        count = N;
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }
        findCount = 0;
        unionCount = 0;
    }

    public int find(int p) {
        findCount++;

        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ) return;

        parent[rootP] = rootQ;
        count--;
        unionCount++;
    }

    public int count() {
        return count;
    }

    public long getOperationCount() {
        return findCount + unionCount;
    }
}