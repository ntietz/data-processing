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
     * Default Constructor returns a BFSStatus with an empty path and 
     * distance at logical infinity and not a target
     */
    public BFSStatus()
    {
        distance = Integer.MAX_VALUE;
    }

    /**
     * @param startingDistance The distance from the starting node
     */
    public BFSStatus(int startingDistance)
    {
        distance = startingDistance;
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
     * Deserializes the object out to the disk
     * @param out DataOutput object used to serialize the data to disk
     */
    public void write(DataOutput out)
    throws IOException
    {
        out.writeInt(distance);
    }

    /**
     * Reads the data in serially from the disk
     * @param in DataInput object used to serialize from
     */
    public void readFields(DataInput in)
    throws IOException
    {
        distance = in.readInt();
    }
}
