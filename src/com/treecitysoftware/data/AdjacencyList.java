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
}

