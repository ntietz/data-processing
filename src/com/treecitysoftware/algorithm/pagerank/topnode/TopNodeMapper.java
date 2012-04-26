package com.treecitysoftware.algorithm.pagerank.topnode;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class TopNodeMapper
extends MapReduceBase
implements Mapper<IntWritable, PageRankNode, DoubleWritable, IntWritable>
{
    public void map( IntWritable key
                   , PageRankNode value
                   , OutputCollector<DoubleWritable, IntWritable> output
                   , Reporter reporter
                   )
    throws IOException
    {
        double score = 1 - value.getValue();
        output.collect(new DoubleWritable(score), key);
    }
}

