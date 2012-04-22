package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class ParserIdReplaceReducer
extends MapReduceBase
implements Reducer<Text, Writable, IntWritable, Writable>
{
    /**
     * This reducer runs right after the mapper and replaces page titles with page ids.
     * @param key       the page title we are collecting
     * @param values    a list of nodes and originating ids
     * @param output    collects the output pairs for the next reducer
     * @param reporter  allows sending counts back to the job driver
     */
    public void reduce( Text key
                      , Iterator<Writable> values
                      , OutputCollector<IntWritable, Writable> output
                      , Reporter reporter
                      )
    throws IOException
    {
        String title = key.toString();
        List<IntWritable> targets = new ArrayList<IntWritable>();
        Node node = null;

        while (values.hasNext())
        {
            Writable obj = values.next();

            if (obj instanceof IntWritable)
            {
                targets.add((IntWritable)obj);
            }
            else if (obj instanceof Node)
            {
                node = (Node) obj;
            }
        }

        if (node != null)
        {
            IntWritable linkId = new IntWritable(node.getId());

            output.collect(linkId, node);

            for (IntWritable each : targets)
            {
                output.collect(each, linkId);
            }
        }
        // if node == null, then we know the link is a dead link
    }
}

