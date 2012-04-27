package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class OutputMapper
extends MapReduceBase
implements Mapper<IntWritable, BFSNode, IntWritable, BFSNode>
{
    /**
     * Takes in (id, node) pairs and only emits the nodes that
     * are target nodes and have a distance. This step is more
     * of like a filter.
     * @param key The Node ID
     * @param Node The Node object
     * @param output An Output Collector that collects (id, node) pairs
     * @param reporter Default reporter object
     */
    public void map(IntWritable key
                  , BFSNode value
                  , OutputCollector<IntWritable, BFSNode> output
                  , Reporter reporter
                  )
    throws IOException
    {
        if(value.isTarget())
        {
            BFSStatus valuePayload = value.getValue();
            if (valuePayload.getDistance() < Integer.MAX_VALUE)
            {
                output.collect(key, value);
            }
        }
    }
}
