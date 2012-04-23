package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.common.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;

public class WikiPassThroughMapper
extends MapReduceBase
implements Mapper<IntWritable, PageOrEdge, IntWritable, PageOrEdge>
{
    public void map( IntWritable key
                   , PageOrEdge value
                   , OutputCollector<IntWritable, PageOrEdge> output
                   , Reporter reporter
                   )
    throws IOException
    {
        output.collect(key, value);
    }
}

