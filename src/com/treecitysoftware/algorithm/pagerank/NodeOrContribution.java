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

    public NodeOrContribution()
    {
        node = null;
        contribution = null;
    }

    public NodeOrContribution(PageRankNode p)
    {
        node = p;
        contribution = null;
    }

    public NodeOrContribution(Contribution c)
    {
        node = null;
        contribution = c;
    }

    public boolean isNode()
    {
        return node != null;
    }

    public boolean isContribution()
    {
        return contribution != null;
    }

    public void write(DataOutput out)
    throws IOException
    {
        if (node != null)
        {
            out.writeUTF("p");
            node.write(out);
        }
        else
        {
            out.writeUTF("c");
            contribution.write(out);
        }
    }

    public void readFields(DataInput in)
    throws IOException
    {
        String type = in.readUTF();
        if (type.equals("p"))
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

