package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;

public class PageRankPreprocessMapper
extends MapReduceBase
implements Mapper<IntWritable, WikiPage, IntWritable, WikiPage>
{
    public void map( IntWritable key
                   , WikiPage value
                   , OutputCollector<IntWritable, WikiPage> output
                   , Reporter reporter
                   )
    throws IOException
    {
        output.collect(key, value);
        reporter.incrCounter("NUMBER", "NODES", 1);
    }

}

