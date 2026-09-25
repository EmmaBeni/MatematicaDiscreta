package org.example;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GraphStandard<T> implements Graph<T> {

    private final Map<T, LinkedList<T>> adjacencyList;
    private final boolean isDirected;

    /**
     * Constructor por defecto: crea un grafo no dirigido.
     */
    public GraphStandard() {
        this(false);
    }

    /**
     * Constructor que permite indicar si el grafo es dirigido o no.
     *
     * @param isDirected true si es dirigido (dígrafo), false si es no dirigido.
     */
    public GraphStandard(boolean isDirected) {
        this.isDirected = isDirected;
        this.adjacencyList = new LinkedHashMap<>();
    }

    @Override
    public void addVertex(T v) {
        if (v != null) {
            adjacencyList.putIfAbsent(v, new LinkedList<>());
        }
    }

    @Override
    public void addEdge(T v, T w) {
        if (v == null || w == null) {
            return;
        }

        // Si los vértices no existen en el grafo, se agregan automáticamente
        addVertex(v);
        addVertex(w);

        // Agregamos la arista de v hacia w si no existe ya
        LinkedList<T> vList = adjacencyList.get(v);
        if (!vList.contains(w)) {
            vList.add(w);
        }

        // Si es no dirigido y no es un lazo (auto-bucle), agregamos la arista inversa de w hacia v
        if (!isDirected && !Objects.equals(v, w)) {
            LinkedList<T> wList = adjacencyList.get(w);
            if (!wList.contains(v)) {
                wList.add(v);
            }
        }
    }

    @Override
    public void deleteEdge(T v, T w) {
        if (v == null || w == null) {
            return;
        }

        if (adjacencyList.containsKey(v)) {
            adjacencyList.get(v).removeIf(node -> Objects.equals(node, w));
        }

        if (!isDirected && adjacencyList.containsKey(w)) {
            adjacencyList.get(w).removeIf(node -> Objects.equals(node, v));
        }
    }

    @Override
    public void deleteVertex(T v) {
        if (v == null || !adjacencyList.containsKey(v)) {
            return;
        }

        // Eliminar el vértice y su lista de adyacencia
        adjacencyList.remove(v);

        // Eliminar cualquier arista entrante/conectada a v en el resto de los vértices
        for (LinkedList<T> neighbors : adjacencyList.values()) {
            neighbors.removeIf(node -> Objects.equals(node, v));
        }
    }

    @Override
    public boolean existsEdge(T v, T w) {
        if (v == null || w == null || !adjacencyList.containsKey(v)) {
            return false;
        }
        return adjacencyList.get(v).contains(w);
    }

    @Override
    public LinkedList<T> getListAdy(T v) {
        if (v == null || !adjacencyList.containsKey(v)) {
            return null;
        }
        return adjacencyList.get(v);
    }

    public boolean isDirected() {
        return isDirected;
    }

    public Set<T> getVertices() {
        return adjacencyList.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<T, LinkedList<T>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
