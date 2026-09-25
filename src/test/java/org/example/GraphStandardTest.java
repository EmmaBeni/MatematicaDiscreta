package org.example;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

public class GraphStandardTest {

    @Test
    void testUndirectedGraphOperations() {
        GraphStandard<String> graph = new GraphStandard<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertTrue(graph.existsEdge("A", "B"));
        assertTrue(graph.existsEdge("B", "A"));

        LinkedList<String> adyA = graph.getListAdy("A");
        assertNotNull(adyA);
        assertTrue(adyA.contains("B"));

        LinkedList<String> adyB = graph.getListAdy("B");
        assertNotNull(adyB);
        assertTrue(adyB.contains("A"));

        // Delete edge
        graph.deleteEdge("A", "B");
        assertFalse(graph.existsEdge("A", "B"));
        assertFalse(graph.existsEdge("B", "A"));

        // Delete vertex
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.deleteVertex("B");

        assertNull(graph.getListAdy("B"));
        assertFalse(graph.existsEdge("A", "B"));
        assertFalse(graph.getListAdy("A").contains("B"));
        assertFalse(graph.getListAdy("C").contains("B"));
    }

    @Test
    void testDirectedGraphOperations() {
        GraphStandard<Integer> digraph = new GraphStandard<>(true);

        digraph.addEdge(1, 2);
        assertTrue(digraph.existsEdge(1, 2));
        assertFalse(digraph.existsEdge(2, 1));

        digraph.deleteEdge(1, 2);
        assertFalse(digraph.existsEdge(1, 2));
    }
}
