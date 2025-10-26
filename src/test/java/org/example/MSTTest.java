package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MSTTest {

    private EdgeWeightedGraph createTestGraph() {
        EdgeWeightedGraph G = new EdgeWeightedGraph(4);
        G.addEdge(new Edge(0, 1, 0.5));
        G.addEdge(new Edge(0, 2, 0.1));
        G.addEdge(new Edge(1, 2, 0.3));
        G.addEdge(new Edge(2, 3, 0.2));
        G.addEdge(new Edge(1, 3, 0.7));
        return G;
    }

    private int countEdges(Iterable<Edge> edges) {
        int count = 0;
        for (Edge e : edges) {
            count++;
        }
        return count;
    }

    @Test
    void testTotalCostIdentityAndCorrectness() {
        EdgeWeightedGraph G = createTestGraph();
        PrimMST prim = new PrimMST(G);
        KruskalMST kruskal = new KruskalMST(G);

        double expectedCost = 0.6;

        assertEquals(expectedCost, prim.weight(), 1e-9,
                "Prim's MST cost is incorrect.");
        assertEquals(prim.weight(), kruskal.weight(), 1e-9,
                "MST costs must match between Prim and Kruskal.");
    }

    @Test
    void testEdgeCount() {
        EdgeWeightedGraph G = createTestGraph();
        PrimMST prim = new PrimMST(G);
        KruskalMST kruskal = new KruskalMST(G);

        int expectedEdges = G.V() - 1;

        assertEquals(expectedEdges, countEdges(prim.edges()),
                "Prim's MST must have V-1 edges.");
        assertEquals(expectedEdges, countEdges(kruskal.edges()),
                "Kruskal's MST must have V-1 edges.");
    }

    @Test
    void testDisconnectedGraphHandling() {
        EdgeWeightedGraph G = new EdgeWeightedGraph(4);
        G.addEdge(new Edge(0, 1, 0.5));
        G.addEdge(new Edge(2, 3, 0.2));

        PrimMST prim = new PrimMST(G);
        KruskalMST kruskal = new KruskalMST(G);

        int expectedEdges = 2;

        assertEquals(expectedEdges, countEdges(prim.edges()),
                "Prim's must find correct number of edges in spanning forest.");
        assertEquals(expectedEdges, countEdges(kruskal.edges()),
                "Kruskal's must find correct number of edges in spanning forest.");

        assertEquals(0.7, prim.weight(), 1e-9, "Cost of spanning forest is incorrect.");
    }
}