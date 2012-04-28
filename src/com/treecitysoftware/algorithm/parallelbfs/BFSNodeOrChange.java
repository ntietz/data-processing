package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.algorithm.parallelbfs.*;
import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;

import java.io.*;
import java.util.*;

/**
 * BFSNodeOrChange stores either a BFSNode structure
 * or a BFSChange structure. This object is emitted
 * from the ParallelBFSMapper and read from the 
 * Reducer in the BFS job. 
 */
public class BFSNodeOrChange
implements Writable
{
    /**
     * Public accessor to the node part of the object
     */
    public BFSNode node;
    
    /**
     * Public accessor to the change part of the object
     */
    public BFSChange change;

    /**
     * Keeps track if the object contains a node or not
     */
    boolean isNode = false;

    /**
     * Constructs an empty BFSNodeOrChange
     */
    public BFSNodeOrChange()
    {
        node = null;
        change = null;
    }

    /**
     * Constructs a BFSNodeOrChange that contains node n
     * @param n The node to store in the wrapper
     */
    public BFSNodeOrChange(BFSNode n)
    {
        node = n;
        change = null;
        isNode = true;
    }

    /**
     * Constructs a BFSNodeOrChange that contains change c
     * @param c The change to store in the wrapper
     */
    public BFSNodeOrChange(BFSChange c)
    {
        node = null;
        change = c;
        isNode = false;
    }

    /**
     * @return true if it contains a node
     */
    public boolean isNode()
    {
        return isNode;
    }

    /**
     * @return true if it contains a change
     */
    public boolean isChange()
    {
        return !isNode;
    }

    /**
     * Serializes the data out to the disk
     * @param out DataOutput object that writes the data
     */
    public void write(DataOutput out)
    throws IOException
    {
        out.writeBoolean(isNode);
        if (isNode)
        {
            node.write(out);
        }
        else
        {
            change.write(out);
        }
    }

    /**
     * Serializes the data in from the disk
     * @param in DataInput object that reads the data
     */
    public void readFields(DataInput in)
    throws IOException
    {
        isNode = in.readBoolean();
        if (isNode)
        {
            node = new BFSNode();
            node.readFields(in);
        }
        else
        {
            change = new BFSChange();
            change.readFields(in);
        }
    }

}
