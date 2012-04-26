package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.common.*;
import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class PageRankMapper
extends MapReduceBase
implements Mapper<IntWritable, PageRankNode, IntWritable, NodeOrContribution>
{
    /**
     * Takes in (id, node) pairs and outputs one (id, node) and many (id, contribution) pairs.
     * The outgoing id in the (id, node) pair is the same as the incoming.
     * The outgoing id in the (id, contribution) pair is for which neighbor the contribution goes to.
     * @param key       the id of the incoming pair
     * @param value     the node of the incoming pair
     * @param output    collects the output pairs for the reducer
     * @param reporter  allows sending counts back to the job driver
     */
    public void map( IntWritable key
                   , PageRankNode value
                   , OutputCollector<IntWritable, NodeOrContribution> output
                   , Reporter reporter
                   )
    throws IOException
    {
        double currentScore = value.getValue();
        double numberOfNeighbors = value.numberOfNeighbors();

        if (numberOfNeighbors > 0)
        {
            Contribution contribution = new Contribution(currentScore / numberOfNeighbors);

            Set<Integer> neighbors = value.getNeighbors();
            for (Integer each : neighbors)
            {
                IntWritable outputKey = new IntWritable(each);
                output.collect(outputKey, new NodeOrContribution(contribution));
                reporter.incrCounter("SENT", "CONTRIBUTION", 1);
            }
        }

        output.collect(key, new NodeOrContribution(value));
    }

}

