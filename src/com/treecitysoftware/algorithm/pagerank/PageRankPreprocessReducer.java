package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class PageRankPreprocessReducer
extends MapReduceBase
implements Reducer<IntWritable, WikiPage, IntWritable, PageRankNode>
{
    private long numberOfNodes;

    public void configure(JobConf conf)
    {
        numberOfNodes = Long.valueOf(conf.get("numberOfNodes"));
    }

    public void reduce( IntWritable key
                      , Iterator<WikiPage> values
                      , OutputCollector<IntWritable, PageRankNode> output
                      , Reporter reporter
                      )
    throws IOException
    {
        double defaultScore = 1.0 / numberOfNodes;
        while (values.hasNext())
        {
            WikiPage page = values.next();

            PageRankNode node = new PageRankNode( page.getId()
                                                , defaultScore
                                                , page.getNeighbors()
                                                );

            output.collect(key, node);
        }
    }
}

