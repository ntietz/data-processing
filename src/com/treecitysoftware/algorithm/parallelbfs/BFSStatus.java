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
     * The shortest path from the starting node to this current node
     */
    private List<Integer> path;

    /**
     * Default Constructor returns a BFSStatus with an empty path and 
     * distance at logical infinity and not a target
     */
    public BFSStatus()
    {
        distance = Integer.MAX_VALUE;
        path = new ArrayList<Integer>();
    }

    /**
     * @param startingDistance The distance from the starting node
     * @param startingPath The path from the source node to the current node
     */
    public BFSStatus(int startingDistance, List<Integer> startingPath)
    {
        distance = startingDistance;
        path = startingPath;
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
     * Returns the path from the starting node to the current node
     * @return The path from the starting node to the current node
     */
    public List<Integer> getPath()
    {
        return path;
    }

    /**
     * Sets the path from the starting node to the current node
     * @param newPath The path from the starting node to the current node
     */
    public void setPath(List<Integer> newPath)
    {
        path = newPath;
    }

    /**
     * Deserializes the object out to the disk
     * @param out DataOutput object used to serialize the data to disk
     */
    public void write(DataOutput out)
    throws IOException
    {
        out.writeInt(distance);
        out.writeInt(path.size());
        for( Integer each : path )
        {
            out.writeInt(each.intValue());
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
        int pathSize = in.readInt();
        for ( int idx = 0; idx < pathSize; idx++ )
        {
            path.add(new Integer(in.readInt()));
        }
    }
}
