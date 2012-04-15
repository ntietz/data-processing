package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;

public class PageRankMapper
extends MapReduceBase
implements Mapper<Text, Node, Text, Writable>
{
    public void map( Text key
                   , Node value
                   , OutputCollector<Text, Writable> output
                   , Reporter reporter
                   )
    throws IOException
    {
        // TODO add mapper content
    }

}

