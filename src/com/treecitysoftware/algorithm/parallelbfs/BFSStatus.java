package com.treecitysoftware.algorithm.parallelbfs;

import org.apache.hadoop.io.*;

import java.io.*;
import java.lang.*;
import java.util.*;

public class BFSStatus
implements Writable
{
    /**
     * Distance from the current Node to the Source Node
     */
    private int distance;

    /**
     * Set true if the node needs to expand
     */
    private boolean toExpand;

    /**
     * Contains list of node ID's to send distance to
     */
    private Set<Integer> whoTo;

    /**
     * Default Constructor returns a BFSStatus with distance at 
     * logical infinity, not to expand, and an empty Set of integers
     */
    public BFSStatus()
    {
        distance = Integer.MAX_VALUE;
        toExpand = false;
        whoTo = new TreeSet<Integer>();
    }

    /**
     * @param startingDistance The distance from the starting node
     * @param expanding Whether or not the node should expand
     * @param nodesToSendData The set of neighbors that we have not heard from
     */
    public BFSStatus(int startingDistance, boolean expanding, Set<Integer> nodesToSendData)
    {
        distance = startingDistance;
        toExpand = expanding;
        whoTo = nodesToSendData;
    }

    /**
     * Returns the distance from the node to the starting node
     * @return distance from starting node
     */
    public int getDistance()
    {
        return distance;
    }

    /**
     * Sets the distance from the curernt node to the starting node
     * @param newDistance The new distance
     */
    public void setDistance(int newDistance)
    {
        distance = newDistance;
    }


    /**
     * @return whether or not the node needs expansion
     */
    public boolean needsExpansion()
    {
        return toExpand;
    }

    /**
     * @param needToExpand true if we want to make it expand
     */
    public void setExpansion(boolean needToExpand)
    {
        toExpand = needToExpand;
    }

    /**
     * @return The set of nodes we need to send data to if the
     * page needs to expand.
     */
    public Set<Integer> getExpansionSet()
    {
        return whoTo;
    }

    /**
     * @param nodesToSendTo The nodes we want to send data to when
     * we expand.
     */
    public void setExpansionSet(Set<Integer> nodesToSendTo)
    {
        whoTo = nodesToSendTo;
    }

    /**
     * Deserializes the object out to the disk
     * @param out DataOutput object used to serialize the data to disk
     */
    public void write(DataOutput out)
    throws IOException
    {
        out.writeInt(distance);
        out.writeBoolean(toExpand);
        if (toExpand)
        {
            int cardinality = whoTo.size();
            out.writeInt(cardinality);
            for (Integer eachID : whoTo)
            {
                out.writeInt(eachID.intValue());
            }
        }
    }

    /**
     * Reads the data in serially from the disk
     * @param in DataInput object used to serialize from
     */
    public void readFields(DataInput in)
    throws IOException
    {
        distance = in.readInt();
        toExpand = in.readBoolean();
        if (toExpand)
        {
            int cardinality = in.readInt();
            for (int idx = 0; idx < cardinality; idx++)
            {
                whoTo.add(new Integer(in.readInt()));
            }
        }
    }
}
