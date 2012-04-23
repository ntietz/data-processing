package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.common.*;
import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class ParserAdjacencyListReducer
extends MapReduceBase
implements Reducer<IntWritable, PageOrEdge, IntWritable, WikiPage>
{
    /**
     * This reducer runs after the first reducer and creates the nodes with their adjacency lists.
     * @param key       the page title we are collecting
     * @param values    a list of nodes and originating ids
     * @param output    collects the output pairs for the next reducer
     * @param reporter  allows sending counts back to the job driver
     */
    public void reduce( IntWritable key
                      , Iterator<PageOrEdge> values
                      , OutputCollector<IntWritable, WikiPage> output
                      , Reporter reporter
                      )
    throws IOException
    {
        WikiPage page = new WikiPage(key.get(), "");

        while (values.hasNext())
        {
            PageOrEdge obj = values.next();

            if (obj.isEdge())
            {
                page.addNeighbor(obj.id);
            }
            else if (obj.isPage())
            {
                page.setValue(obj.page.getValue());
            }
        }

        output.collect(key, page);
        reporter.incrCounter("WRITTEN", "NODES", 1);
    }
}

