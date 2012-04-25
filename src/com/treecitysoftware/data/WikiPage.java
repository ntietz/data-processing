package com.treecitysoftware.data;

import org.apache.hadoop.io.*;

import java.io.*;
import java.util.*;

public class WikiPage
implements Writable
{
    /**
     * id is used within the system to identify nodes in lists of links and for supplementary information.
     */
    private int id;

    /**
     * title is the title of the page
     */
    private String title;

    /**
     * neighbors stores the ids of all the adjacent nodes.
     * Each of these ids should exist as the id field of another node.
     */
    private Set<Integer> neighbors;

    /**
     * There is no safe or desired default behavior; this is only public so Hadoop works correctly.
     */
    public WikiPage()
    {
        // no safe or desired default behavior
    }

    /**
     * @param i id for the new node
     * @param v value of the new node
     */
    public WikiPage(int i, String t)
    {
        id = i;
        title = t;
        neighbors = new TreeSet<Integer>();
    }

    /**
     * @param i id for the new node
     * @param v value of the new node
     * @param n list of neighbor ids of the new node
     */
    public WikiPage(int i, String t, Set<Integer> n)
    {
        id = i;
        title = t;
        neighbors = n;
    }

    /**
     * @return the id of the node
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param v the value to set the node
     */
    public void setValue(String t)
    {
        title = t;
    }

    /**
     * @return  the value of the node
     */
    public String getValue()
    {
        return title;
    }

    /**
     * @return  the neighbors of the node
     */
    public Set<Integer> getNeighbors()
    {
        return neighbors;
    }

    /**
     * @return  the number of neighbors
     */
    public int numberOfNeighbors()
    {
        return neighbors.size();
    }

    /**
     * Adds the id of a neighbor to the list.
     * Does not verify whether the id is already in the list.
     * @param i id of the neighbor to add.
     */
    public void addNeighbor(int i)
    {
        neighbors.add(i);
    }

    /**
     * Removes all instances of the neighbor from the neighbor list.
     * @param i id of the neighbor to remove
     */
    public void removeNeighbor(int i)
    {
        boolean removing = true;
        while (removing)
        {
            removing = neighbors.remove((Integer)id);
        }
    }

    /**
     * Checks whether or not the id is a neighbor of the current node.
     * @param i id of the possible neighbor
     * @return  true if the id is a neighbor, false otherwise
     */
    public boolean connectsTo(int i)
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

    /**
     * Serializes the object to a DataOutput object.
     * @param out   the object we serialize using
     */
    public void write(DataOutput out)
    throws IOException
    {
        out.writeUTF(title);
        out.writeInt(id);
        out.writeInt(neighbors.size());
        for (Integer each : neighbors)
        {
            out.writeInt(each);
        }
    }

    /**
     * Deserializes the object from a DataInput object.
     * @param in    the object we deserialize from
     */
    public void readFields(DataInput in)
    throws IOException
    {
        title = in.readUTF();
        id = in.readInt();
        int size = in.readInt();
        neighbors = new HashSet();
        for (int index = 0; index < size; ++index)
        {
            neighbors.add(in.readInt());
        }
    }

    /**
     * Tests for equality between this and the passed in object.
     * It only checks for equality on the id since the id should be a unique identifier.
     * @param obj   the object we want to check for equality with
     * @return      true if the object is a node with the same id, false otherwise
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof WikiPage)
        {
            WikiPage other = (WikiPage) obj;

            return id == other.id;
        }
        else
        {
            return false;
        }
    }

    /**
     * Compares this object with another object, on the basis of the id.
     * @param obj   the object we want to compare to
     * @return      -1 if the id is earlier, 0 if it is the same, 1 if it is later
     */
    public int compareTo(Object obj)
    {
        if (obj instanceof WikiPage)
        {
            WikiPage other = (WikiPage) obj;

            if (id < other.id)
            {
                return -1;
            }
            else if (id == other.id)
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
        else
        {
            return -1;
        }
    }

    /**
     * @return  the hashcode of the id of the node
     */
    public int hashCode()
    {
        return id;
    }
}

