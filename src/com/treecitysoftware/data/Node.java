package com.treecitysoftware.data;

import org.apache.hadoop.io.*;

import java.io.*;
import java.util.*;

public class Node
implements Writable
{
    /**
     * id is used within the system to identify nodes in lists of links and for supplementary information.
     */
    private String id;

    /**
     * value is a writable type so the payload of the node can be fitted to the problem.
     * In PageRank, we will use value type Double so we can track the PageRank scores.
     * In a normal graph, we could have PageRank be the contents of the node, such as a WikiPage.
     */
    private Writable value;

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
    public Node(String i, Writable v)
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
    public Node(String i, Writable v, List<String> n)
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
    public Writable getValue()
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

    public void write(DataOutput out)
    throws IOException
    {
        value.write(out);
        out.writeUTF(id);
        out.writeInt(neighbors.size());
        for (int index = 0; index < neighbors.size(); ++index)
        {
            out.writeUTF(neighbors.get(index));
        }
    }

    public void readFields(DataInput in)
    throws IOException
    {
        value.readFields(in);
        id = in.readUTF();
        int size = in.readInt();
        neighbors = new ArrayList(size);
        for (int index = 0; index < size; ++index)
        {
            neighbors.add(in.readUTF());
        }
    }

    // TODO add equals

    // TODO add compareTo

    // TODO add hashCode
}

