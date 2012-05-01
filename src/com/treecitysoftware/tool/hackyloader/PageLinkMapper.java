package com.treecitysoftware.tool.hackyloader;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class PageLinkMapper
extends MapReduceBase
implements Mapper<IntWritable, WikiPage, IntWritable, WikiPage>
{
    private static IntWritable one = new IntWritable(1);
    /**
     * Takes in (id, node) pairs and emits them right back out
     * @param key The Node ID
     * @param Node The Node object
     * @param output An Output Collector that collects (id, node) pairs
     * @param reporter Default reporter object
     */
    public void map(IntWritable key
                  , WikiPage value
                  , OutputCollector<IntWritable, WikiPage> output
                  , Reporter reporter
                  )
    throws IOException
    {
        output.collect(one, value);
    }
}
