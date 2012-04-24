package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class ParallelBFSPreprocessMapper
extends MapReduceBase
implements Mapper<IntWritable, WikiPage, IntWritable, BFSNode>
{
    /**
     * Takes in (id, page) pairs and releases one identical (id, node) 
     * pair for each pair it recieves.
     * @param key The Node ID
     * @param Node The Node object
     * @param output An Output Collector that collects (id, node) pairs
     * @param reporter Default reporter object
     */
    public void map(IntWritable key
                  , WikiPage value
                  , OutputCollector<IntWritable, BFSNode> output
                  , Reporter reporter
                  )
    throws IOException
    {
        BFSNode n = new BFSNode(key.get()
                              , new BFSStatus()
                              , value.getNeighbors());
        output.collect(key, n);
    }
}
