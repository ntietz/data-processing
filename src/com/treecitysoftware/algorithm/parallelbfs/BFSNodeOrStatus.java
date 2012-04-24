package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.algorithm.parallelbfs.*;
import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;

import java.io.*;
import java.util.*;

public class BFSNodeOrStatus
implements Writable
{
    public BFSNode node;
    public BFSStatus status;

    public BFSNodeOrStatus()
    {
        node = null;
        status = null;
    }

    public BFSNodeOrStatus(BFSNode n)
    {
        node = n;
        status = null;
    }

    public BFSNodeOrStatus(BFSStatus s)
    {
        node = null;
        status = s;
    }

    public boolean isNode()
    {
        return node != null;
    }

    public boolean isStatus()
    {
        return status != null;
    }

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

