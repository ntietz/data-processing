package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class ParallelBFSPreprocessMapper
extends MapReduceBase
implements Mapper<IntWritable, Node, IntWritable, Node>
{
    /**
     * Takes in (id, node) pairs and outputs one (id, node) pair
     * with an initialized payload
     */
    public void map(IntWritable key
                  , Node value
                  , OutputCollector<IntWritable, Node> output
                  , Reporter reporter
                  )
    throws IOException
    {
        
    }
}
