package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.algorithm.parallelbfs.*;
import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;

import java.io.*;
import java.util.*;

public class BFSChange
implements Writable
{
    /**
     * Holds the page node id that the change came from
     */
    private int from;

    /**
     * Holds the distance to transmit
     */
    private int distance;

    /**
     * Constructs an empty change object
     */
    public BFSChange()
    {
        // No expected behavior
    }

    /**
     * Constructs a BFSChange object
     * @param id ID of the node that sent the change
     * @param d Distance that the node will send
     */
    public BFSChange(int id, int d)
    {
        from = id;
        distance = d;
    }

    /**
     * @return the node id that sent this change
     */
    public int getSendingNode()
    {
        return from;
    }

    /**
     * @param id The ID of the node that is sending this change
     */
    public void setSendingNode(int id)
    {
        from = id;
    }

    /**
     * @return the distance of the change
     */
    public int getDistance()
    {
        return distance;
    }

    /**
     * @param d The distance that the node is sending
     */
    public void setDistance(int d)
    {
        distance = d;
    }
    
    /**
     * Serializes the change to disk
     * @param out DataOutput that serializes it out
     */
    public void write(DataOutput out)
    throws IOException
    {
        out.writeInt(from);
        out.writeInt(distance);
    }
    
    /**
     * Serializes the change from disk
     * @param in DataInput that serializes it in
     */
    public void readFields(DataInput in)
    throws IOException
    {
        from = in.readInt();
        distance = in.readInt();
    }
}
