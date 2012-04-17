package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class PageRankReducer
extends MapReduceBase
implements Reducer<Text, Writable, Text, Node>
{
    private long numberOfNodes;
    private double dampingFactor;

    public  void configure(JobConf conf)
    {
        numberOfNodes = Long.valueOf(conf.get("numberOfNodes"));
        dampingFactor = Double.valueOf(conf.get("dampingFactor"));
    }

    public void reduce( Text key
                      , Iterator<Writable> values
                      , OutputCollector<Text, Node> output
                      , Reporter reporter
                      )
    throws IOException
    {
        Node node = new Node(key.toString(), new DoubleWritable(0.0));
        double score = 0.0;

        while (values.hasNext())
        {
            Writable obj = values.next();

            if (obj instanceof Node)
            {
                node = (Node) obj;
            }
            else if (obj instanceof Contribution)
            {
                Contribution contribution = (Contribution) obj;
                score += contribution.getAmount();
            }
        }

        score = dampingFactor / numberOfNodes + (1 - dampingFactor) * score;

        node.setValue(new DoubleWritable(score));

        output.collect(key, node);
    }
}

