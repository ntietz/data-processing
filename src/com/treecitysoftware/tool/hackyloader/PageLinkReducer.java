package com.treecitysoftware.tool.hackyloader;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import java.io.*;
import java.util.*;

public class PageLinkReducer
extends MapReduceBase
implements Reducer<IntWritable, WikiPage, Text, Text>
{
    private static final Text blank = new Text("");

    private static final String pageLineStart = "insert into `pages` (page_id, title) values ";
    private static final String edgeLineStart = "insert into `links` (from_id, to_id) values ";

    private static String pageLine = pageLineStart;
    private static int pagesInLine = 0;

    private static String edgeLine = edgeLineStart;
    private static int edgesInLine = 0;

    /**
     * Receives (id, node) pairs, which are then loaded into the database.
     */
    public void reduce( IntWritable key
                      , Iterator<WikiPage> values
                      , OutputCollector<Text, Text> output
                      , Reporter reporter
                      )
    throws IOException
    {
        while (values.hasNext())
        {
            WikiPage node = values.next();
            int pageId = node.getId();
            String title = node.getValue();
            Set<Integer> neighbors = node.getNeighbors();

            pageLine = pageLine + "(" + pageId + ", '" + title + "'),";
            ++pagesInLine;

            reporter.incrCounter("ADDED", "PAGE", 1);
            
            for (Integer neighborId : neighbors)
            {
                edgeLine = edgeLine + "(" + pageId + ", " + neighborId.intValue() + "),";
                ++edgesInLine;

                reporter.incrCounter("ADDED", "EDGE", 1);
            }

            if (pagesInLine > 100)
            {
                pageLine = pageLine.substring(0, pageLine.length()-1) + ";";
                output.collect(new Text(pageLine), blank);
                pageLine = pageLineStart;
                pagesInLine = 0;

                reporter.setStatus("processed a line of pages");

                reporter.incrCounter("ADDED", "PAGELINE", 1);
            }

            if (edgesInLine > 100)
            {
                edgeLine = edgeLine.substring(0, edgeLine.length()-1) + ";";
                output.collect(new Text(edgeLine), blank);
                edgeLine = edgeLineStart;
                edgesInLine = 0;

                reporter.setStatus("processed a line of edges");

                reporter.incrCounter("ADDED", "EDGELINE", 1);
            }
        }

        if (pagesInLine > 0)
        {
            pageLine = pageLine.substring(0, pageLine.length()-1) + ";";
            output.collect(new Text(pageLine), blank);
        }

        if (edgesInLine > 0)
        {
            edgeLine = edgeLine.substring(0, edgeLine.length()-1) + ";";
            output.collect(new Text(edgeLine), blank);
        }
    }
}
