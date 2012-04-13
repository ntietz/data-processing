package com.treecitysoftware.data;

import java.util.*;

public class Node<T>
{
    private String id;
    private T value;
    private List<String> neighbors;

    public Node(String i, T v)
    {
        id = i;
        value = v;
        neighbors = new ArrayList<String>();
    }

    public Node(String i, T v, List<String> n)
    {
        id = i;
        value = v;
        neighbors = n;
    }

    public String getId()
    {
        return id;
    }

    public T getValue()
    {
        return value;
    }

    public List<String> getNeighbors()
    {
        return neighbors;
    }

    public void addNeighbor(String i)
    {
        neighbors.add(i);
    }

    public void removeNeighbor(String i)
    {
        boolean removing = true;
        while (removing)
        {
            removing = neighbors.remove(id);
        }
    }

    public boolean connectsTo(String i)
    {
        return neighbors.contains(i);
    }
}

