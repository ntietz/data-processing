package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class PageRankReducer
extends MapReduceBase
implements Reducer<Text, Writable, Text, Node>
{
    public void reduce( Text key
                      , Iterator<Writable> values
                      , OutputCollector<Text, Node> output
                      , Reporter reporter
                      )
    throws IOException
    {
        // TODO add reducer content
    }
}

