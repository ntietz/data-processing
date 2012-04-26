package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.algorithm.pagerank.*;
import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;

import java.io.*;
import java.util.*;

public class NodeOrContribution
implements Writable
{
    public PageRankNode node;
    public Contribution contribution;
    boolean isNode = false;

    public NodeOrContribution()
    {
        node = null;
        contribution = null;
    }

    public NodeOrContribution(PageRankNode p)
    {
        node = p;
        contribution = null;
        isNode = true;
    }

    public NodeOrContribution(Contribution c)
    {
        node = null;
        contribution = c;
        isNode = false;
    }

    public boolean isNode()
    {
        return isNode;
    }

    public boolean isContribution()
    {
        return !isNode;
    }

    public void write(DataOutput out)
    throws IOException
    {
        out.writeBoolean(isNode);
        if (node != null)
        {
            node.write(out);
        }
        else
        {
            contribution.write(out);
        }
    }

    public void readFields(DataInput in)
    throws IOException
    {
        isNode = in.readBoolean();
        if (isNode)
        {
            node = new PageRankNode();
            node.readFields(in);
        }
        else
        {
            contribution = new Contribution();
            contribution.readFields(in);
        }
    }

}

