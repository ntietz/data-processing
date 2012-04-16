package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

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
        Contribution contribution = new Contribution();
        // TODO set contribution

        List<String> neighbors = value.getNeighbors();
        for (String each : neighbors);
        {
            
        }

        output.collect(key, (Writable)value);
    }

}

