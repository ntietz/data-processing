package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class PageRankMapper
extends MapReduceBase
implements Mapper<Text, Node, Text, Writable>
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
    public void map( Text key
                   , Node value
                   , OutputCollector<Text, Writable> output
                   , Reporter reporter
                   )
    throws IOException
    {
        double currentScore = ((DoubleWritable)value.getValue()).get();
        double numberOfNeighbors = value.numberOfNeighbors();

        if (numberOfNeighbors > 0)
        {
            Contribution contribution = new Contribution(currentScore / numberOfNeighbors);

            List<String> neighbors = value.getNeighbors();
            for (String each : neighbors)
            {
                Text outputKey = new Text(each);
                output.collect(outputKey, (Writable)contribution);
            }
        }

        output.collect(key, (Writable)value);
    }

}

