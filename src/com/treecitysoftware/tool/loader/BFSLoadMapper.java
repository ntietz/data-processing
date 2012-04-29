package com.treecitysoftware.tool.loader;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class BFSLoadMapper
extends MapReduceBase
implements Mapper<FromToPair, IntWritable, FromToPair, IntWritable>
{
    /**
     * Takes in (id, node) pairs and emits them right back out
     * @param key The Node ID
     * @param Node The Node object
     * @param output An Output Collector that collects (id, node) pairs
     * @param reporter Default reporter object
     */
    public void map( FromToPair key
                   , IntWritable value
                   , OutputCollector<FromToPair, IntWritable> output
                   , Reporter reporter
                   )
    throws IOException
    {
        output.collect(key, value);
    }
}
