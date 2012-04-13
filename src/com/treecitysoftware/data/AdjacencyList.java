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
        neighbors = new ArrayList<String>(n);
    }

    public void addNeighbor(String id)
    {
        if (!contains(id))
        {
            neighbors.add(id);
        }
    }

    public void removeNeighbor(String id)
    {
        boolean removing = true;
        while (removing)
        {
            removing = neighbors.remove(id);
        }
    }

    public boolean contains(String id)
    {
        return neighbors.contains(id);
    }
}

