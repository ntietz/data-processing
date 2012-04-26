package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.common.*;
import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class PageRankReducer
extends MapReduceBase
implements Reducer<IntWritable, NodeOrContribution, IntWritable, PageRankNode>
{
    /**
     * The number of nodes in the entire graph.
     */
    private long numberOfNodes;

    /**
     * The damping factor helps the PageRank algorithm converge.
     * It is often set between 0.15 and 0.30.
     */
    private double dampingFactor;

    /**
     * The adjustment bonus is the weight lost in the previous round divided by all the nodes.
     * If the weight of the last round was higher than 1.0, adjustmentBonus will be negative.
     */
    private double adjustmentBonus;

    /**
     * Initializes the numberOfNodes and dampingFactor, transmitted from the job driver.
     * This is run before reduce is run.
     * @param conf  allows reading values set by the job driver
     */
    public  void configure(JobConf conf)
    {
        numberOfNodes = Long.valueOf(conf.get("numberOfNodes"));
        dampingFactor = Double.valueOf(conf.get("dampingFactor"));
        adjustmentBonus = Double.valueOf(conf.get("adjustmentBonus"));
    }

    /**
     * Takes in an id and list of values (node or contributions) and outputs a single id-node pair.
     * The input from the mapper should only have one node be received.
     * Exactly one node should always be received.
     * @param key       the id of the node receiving contributions
     * @param values    a list of either Node (once) or Contribution (many times)
     * @param output    collects the output pairs
     * @param reporter  allows sending counts back to the job driver
     */
    public void reduce( IntWritable key
                      , Iterator<NodeOrContribution> values
                      , OutputCollector<IntWritable, PageRankNode> output
                      , Reporter reporter
                      )
    throws IOException
    {
        
        PageRankNode node = new PageRankNode(key.get(), 0.0);
        double score = 0.0;

        while (values.hasNext())
        {
            NodeOrContribution obj = values.next();

            if (obj.isNode())
            {
                node = obj.node;
            }
            else if (obj.isContribution())
            {
                score += obj.contribution.getAmount();
            }
        }

        score += adjustmentBonus;

        score = dampingFactor / numberOfNodes + (1 - dampingFactor) * score;

        // collect the differences in scores for convergence checking
        Double difference = Math.abs(score - node.getValue());
        long scaledChange = (new Double(PageRankConstants.scalingFactor * numberOfNodes * difference)).longValue();
        reporter.incrCounter("WEIGHT", "CHANGED", scaledChange);

        node.setValue(score);

        output.collect(key, node);
    }
}

