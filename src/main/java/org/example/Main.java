package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static EdgeWeightedGraph createGraphFromModel(InputGraphModel model) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(model.V);
        for (EdgeModel edge : model.edges) {
            G.addEdge(new Edge(edge.v, edge.w, edge.weight));
        }
        return G;
    }

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<TestResult> allResults = new ArrayList<>();

        try (java.io.InputStream is = Main.class.getClassLoader().getResourceAsStream("asign3.json");
             java.io.Reader reader = new java.io.InputStreamReader(is))
        {

            if (is == null) {
                System.err.println("Error: Resource 'asign3.json' Didn't find in classpath. Are you sure it's in src/main/resources/?");
                return;
            }

            Type listType = new TypeToken<ArrayList<InputGraphModel>>(){}.getType();
            List<InputGraphModel> inputGraphs = gson.fromJson(reader, listType);

            for (InputGraphModel input : inputGraphs) {
                System.out.printf("\n--- Testing Graph: %s (V=%d, E=%d) ---\n",
                        input.graph_id, input.V, input.edges.size());

                EdgeWeightedGraph G = createGraphFromModel(input);
                TestResult result = new TestResult(input.graph_id, G.V(), input.edges.size());

                long startPrim = System.nanoTime();
                PrimMST primMST = new PrimMST(G);
                long endPrim = System.nanoTime();

                long primOps = primMST.operationCount(); // Сбор метрик
                result.addResult("Prim's Algorithm", primMST.weight(), (endPrim - startPrim), primOps);
                System.out.printf("Prim's: Cost=%.5f, Time=%.3f ms, Ops=%d\n",
                        primMST.weight(), (endPrim - startPrim) / 1_000_000.0, primOps);

                long startKruskal = System.nanoTime();
                KruskalMST kruskalMST = new KruskalMST(G);
                long endKruskal = System.nanoTime();

                long kruskalOps = kruskalMST.operationCount(); // Сбор метрик
                result.addResult("Kruskal's Algorithm", kruskalMST.weight(), (endKruskal - startKruskal), kruskalOps);
                System.out.printf("Kruskal's: Cost=%.5f, Time=%.3f ms, Ops=%d\n",
                        kruskalMST.weight(), (endKruskal - startKruskal) / 1_000_000.0, kruskalOps);

                if (Math.abs(primMST.weight() - kruskalMST.weight()) > 1e-9) {
                    System.err.println("Cost of MST doesn't match!");
                }

                allResults.add(result);
            }

            try (FileWriter writer = new FileWriter("output.json")) {
                gson.toJson(allResults, writer);
                System.out.println("\nResults are succesfully writed in output.json");
            }

        } catch (Exception e) {
            System.err.println("File r/w error:");
            e.printStackTrace();
        }
    }
}