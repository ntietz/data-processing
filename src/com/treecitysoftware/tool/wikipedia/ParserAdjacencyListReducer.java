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
        boolean changed = false;

        while (values.hasNext())
        {
            PageOrEdge obj = values.next();

            if (obj.isEdge())
            {
                page.addNeighbor(obj.id);
                reporter.incrCounter("ADDED", "NEIGHBOR", 1);
            }
            else if (obj.isPage())
            {
                page.setValue(obj.page.getValue());
                reporter.incrCounter("ADDED", "PAGE", 1);
                changed = true;
            }
        }

        if (changed)
        {
            output.collect(key, page);
            reporter.incrCounter("PAGES", "WRITTEN", 1);
        }
        else
        {
            reporter.incrCounter("PAGES", "NULL", 1);
        }
    }
}

