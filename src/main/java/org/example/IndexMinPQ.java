package org.example;

import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> {
    private final int maxN;
    private int n;
    private final int[] pq;
    private final int[] qp;
    private final Key[] keys;

    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        n = 0;
        keys = (Key[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
        return qp[i] != -1;
    }

    public void insert(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }

    public int delMin() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        int indexOfMin = pq[1];
        exch(1, n--);
        sink(1);

        qp[indexOfMin] = -1;
        keys[indexOfMin] = null;
        pq[n+1] = -1;
        return indexOfMin;
    }

    public void decreaseKey(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
        if (!contains(i)) throw new IllegalArgumentException("index is not in the priority queue");
        if (keys[i].compareTo(key) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given key not strictly less than current key");

        keys[i] = key;
        swim(qp[i]);
    }

    private boolean less(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        while (k > 1 && less(k, k/2)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j + 1, j)) j++;
            if (!less(j, k)) break;
            exch(k, j);
            k = j;
        }
    }
}