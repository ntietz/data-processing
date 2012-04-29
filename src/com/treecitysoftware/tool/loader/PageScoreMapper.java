package com.treecitysoftware.tool.loader;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class PageScoreMapper
extends MapReduceBase
implements Mapper<IntWritable, PageRankNode, IntWritable, PageRankNode>
{
    /**
     * Takes in (id, node) pairs and emits them right back out
     * @param key The Node ID
     * @param Node The Node object
     * @param output An Output Collector that collects (id, node) pairs
     * @param reporter Default reporter object
     */
    public void map(IntWritable key
                  , PageRankNode value
                  , OutputCollector<IntWritable, PageRankNode> output
                  , Reporter reporter
                  )
    throws IOException
    {
        output.collect(key, value);
    }
}
