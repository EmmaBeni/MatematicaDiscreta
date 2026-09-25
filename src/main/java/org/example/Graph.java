package org.example;

import java.util.LinkedList;

public interface Graph<T> {
    public void addVertex(T v);
    public void addEdge(T v, T w);
    public void deleteEdge(T v, T w);
    public void deleteVertex(T v);
    public boolean existsEdge(T v, T w);
    public LinkedList<T> getListAdy(T v);
}