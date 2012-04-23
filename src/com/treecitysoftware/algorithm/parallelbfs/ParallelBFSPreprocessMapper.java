package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class ParallelBFSPreprocessMapper
extends MapReduceBase
implements Mapper<IntWritable, Node, IntWritable, Node>
{
    /**
     * Takes in (id, node) pairs and releases one identical (id, node) pair
     * for each pair it recieves.
     * @param key The Node ID
     * @param Node The Node object
     * @param output An Output Collector that collects (id, node) pairs
     * @param reporter Default reporter object
     */
    public void map(IntWritable key
                  , Node value
                  , OutputCollector<IntWritable, Node> output
                  , Reporter reporter
                  )
    throws IOException
    {
        output.collect(key, value);
    }
}
