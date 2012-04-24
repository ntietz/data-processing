package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.common.*;
import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class ParserIdReplaceReducer
extends MapReduceBase
implements Reducer<Text, PageOrEdge, IntWritable, PageOrEdge>
{
    /**
     * This reducer runs right after the mapper and replaces page titles with page ids.
     * @param key       the page title we are collecting
     * @param values    a list of nodes and originating ids
     * @param output    collects the output pairs for the next reducer
     * @param reporter  allows sending counts back to the job driver
     */
    public void reduce( Text key
                      , Iterator<PageOrEdge> values
                      , OutputCollector<IntWritable, PageOrEdge> output
                      , Reporter reporter
                      )
    throws IOException
    {
        String title = key.toString();
        List<Integer> originators = new ArrayList<Integer>();
        WikiPage page = null;

        while (values.hasNext())
        {
            PageOrEdge obj = values.next();

            if (obj.isPage())
            {
                page = obj.page;
                reporter.incrCounter("HANDLED", "PAGE", 1);
            }
            else if (obj.isEdge())
            {
                originators.add(obj.id);
                reporter.incrCounter("HANDLED", "EDGE", 1);
            }
            else
            {
                reporter.incrCounter("NUMBER", "UNHANLDED", 1);
            }

            reporter.incrCounter("NUMBER", "TOTAL", 1);
        }

        if (page != null)
        {
            Integer targetId = page.getId();

            output.collect(new IntWritable(page.getId()), new PageOrEdge(page));
            reporter.incrCounter("NUMBER", "NODES-WRITTEN", 1);

            for (Integer each : originators)
            {
                output.collect(new IntWritable(each), new PageOrEdge(targetId));
                reporter.incrCounter("NUMBER", "EDGES-WRITTEN", 1);
            }
        }
        else
        {
            reporter.incrCounter("NUMBER", "DEAD-LINKS", 1);
        }
        // if node == null, then we know the link is a dead link
    }
}

