package com.treecitysoftware.data;

import java.util.*;

public class Node<T>
{
    /**
     * id is used within the system to identify nodes in lists of links and for supplementary information.
     */
    private String id;

    /**
     * value is a generic type so the payload of the node can be fitted to the problem.
     * In PageRank, we will use value type Double so we can track the PageRank scores.
     * In a normal graph, we could have PageRank be the contents of the node, such as a WikiPage.
     */
    private T value;

    /**
     * neighbors stores the ids of all the adjacent nodes.
     * Each of these ids should exist as the id field of another node.
     */
    private List<String> neighbors;

    /**
     * There is no safe or desired default behavior, so the default constructor is disabled.
     */
    private Node()
    {
        // no safe or desired default behavior
    }

    /**
     * @param i id for the new node
     * @param v value of the new node
     */
    public Node(String i, T v)
    {
        id = i;
        value = v;
        neighbors = new ArrayList<String>();
    }

    /**
     * @param i id for the new node
     * @param v value of the new node
     * @param n list of neighbor ids of the new node
     */
    public Node(String i, T v, List<String> n)
    {
        id = i;
        value = v;
        neighbors = n;
    }

    /**
     * @return the id of the node
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return the value of the node
     */
    public T getValue()
    {
        return value;
    }

    /**
     * @return the neighbors of the node
     */
    public List<String> getNeighbors()
    {
        return neighbors;
    }

    /**
     * Adds the id of a neighbor to the list.
     * Does not verify whether the id is already in the list.
     * @param i id of the neighbor to add.
     */
    public void addNeighbor(String i)
    {
        neighbors.add(i);
    }

    /**
     * Removes all instances of the neighbor from the neighbor list.
     * @param i id of the neighbor to remove
     */
    public void removeNeighbor(String i)
    {
        boolean removing = true;
        while (removing)
        {
            removing = neighbors.remove(id);
        }
    }

    /**
     * Checks whether or not the id is a neighbor of the current node.
     * @param i id of the possible neighbor
     * @return  true if the id is a neighbor, false otherwise
     */
    public boolean connectsTo(String i)
    {
        return neighbors.contains(i);
    }

    /**
     * Checks whether or not the node is a dangling node.
     * Dangling nodes are those which have no neighbors.
     * @return  true if the node is dangling, false otherwise
     */
    public boolean isDangling()
    {
        if (neighbors.size() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // TODO add equals

    // TODO add compareTo

    // TODO add hashCode
}

