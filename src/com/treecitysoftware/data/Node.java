package com.treecitysoftware.data;

public class Node<T>
{
    private String id;
    private T value;
    private AdjacencyList neighbors;

    public Node(String i, T v)
    {
        id = i;
        value = v;
        neighbors = new AdjacencyList();
    }

    public Node(String i, T v, AdjacencyList n)
    {
        id = i;
        value = v;
        neighbors = n;
    }

    public void addNeighbor(String i)
    {
        neighbors.addNeighbor(i);
    }

    public void removeNeighbor(String i)
    {
        neighbors.removeNeighbor(i);
    }

    public boolean connectsTo(String i)
    {
        return neighbors.contains(i);
    }
}

