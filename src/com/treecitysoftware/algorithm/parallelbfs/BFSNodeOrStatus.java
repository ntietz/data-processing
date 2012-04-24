package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.algorithm.parallelbfs.*;
import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;

import java.io.*;
import java.util.*;

/**
 * BFSNodeOrStatus stores either a BFSNode structure
 * or a BFSStatus structure. This object is emitted
 * from the ParallelBFSMapper and read from the 
 * Reducer in the BFS job. 
 */
public class BFSNodeOrStatus
implements Writable
{
    /**
     * Public accessor to the node part of the object
     */
    public BFSNode node;
    
    /**
     * Public accessor to the status part of the object
     */
    public BFSStatus status;

    /**
     * Constructs an empty BFSNodeOrStatus
     */
    public BFSNodeOrStatus()
    {
        node = null;
        status = null;
    }

    /**
     * Constructs a BFSNodeOrStatus that contains node n
     * @param n The node to store in the wrapper
     */
    public BFSNodeOrStatus(BFSNode n)
    {
        node = n;
        status = null;
    }

    /**
     * Constructs a BFSNodeOrStatus that contains status s
     * @param s The status to store in the wrapper
     */
    public BFSNodeOrStatus(BFSStatus s)
    {
        node = null;
        status = s;
    }

    /**
     * @return true if it contains a node
     */
    public boolean isNode()
    {
        return node != null;
    }

    /**
     * @return true if it contains a status
     */
    public boolean isStatus()
    {
        return status != null;
    }

    /**
     * Serializes the data out to the disk
     * @param out DataOutput object that writes the data
     */
    public void write(DataOutput out)
    throws IOException
    {
        if (node != null)
        {
            out.writeUTF("n");
            node.write(out);
        }
        else
        {
            out.writeUTF("s");
            status.write(out);
        }
    }

    /**
     * Serializes the data in from the disk
     * @param in DataInput object that reads the data
     */
    public void readFields(DataInput in)
    throws IOException
    {
        String type = in.readUTF();
        if (type.equals("n"))
        {
            node = new BFSNode();
            node.readFields(in);
        }
        else
        {
            status = new BFSStatus();
            status.readFields(in);
        }
    }

}

