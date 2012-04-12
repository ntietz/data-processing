package com.treecitysoftware.data;

import java.util.*;

public class AdjacencyList
{
    private List<String> neighbors;

    public AdjacencyList()
    {
        neighbors = new ArrayList<String>();
    }

    public AdjacencyList(List<String> n)
    {
        neighbors = n;
    }

    public void addNeighbor(String id)
    {
        neighbors.add(id);
    }

    public void removeNeighbor(String id)
    {
        neighbors.remove(id); // TODO should we remove all here?
    }

    public boolean contains(String id)
    {
        return neighbors.contains(id);
    }
}

