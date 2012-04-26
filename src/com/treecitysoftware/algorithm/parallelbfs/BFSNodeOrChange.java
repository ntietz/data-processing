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
    }

    /**
     * Constructs a BFSNodeOrChange that contains change c
     * @param c The change to store in the wrapper
     */
    public BFSNodeOrChange(BFSChange c)
    {
        node = null;
        change = c;
    }

    /**
     * @return true if it contains a node
     */
    public boolean isNode()
    {
        return node != null;
    }

    /**
     * @return true if it contains a change
     */
    public boolean isChange()
    {
        return change != null;
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
            out.writeUTF("c");
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
        String type = in.readUTF();
        if (type.equals("n"))
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
