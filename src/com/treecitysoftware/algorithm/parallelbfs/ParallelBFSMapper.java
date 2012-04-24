package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class ParallelBFSMapper
extends MapReduceBase
implements Mapper<IntWritable, BFSNode, IntWritable, BFSNode>
{
    /**
     * Takes in (id, node) pairs and propogates their distance values
     * to each of of their neighbors.
     * Finally, they emit themselves.
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
        //Get the distance so far
        int distanceSoFar = ((BFSStatus) value.getValue()).getDistance();
        
        //If it's infinity, then we have not discovered our position yet
        if(distanceSoFar == Integer.MAX_VALUE)
        {
            // TODO : After getting compilable code
        }
    }
}
