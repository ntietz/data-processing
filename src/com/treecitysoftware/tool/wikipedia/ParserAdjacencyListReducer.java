package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class ParserAdjacencyListReducer
extends MapReduceBase
implements Reducer<IntWritable, Writable, IntWritable, Node>
{
    /**
     * This reducer runs after the first reducer and creates the nodes with their adjacency lists.
     * @param key       the page title we are collecting
     * @param values    a list of nodes and originating ids
     * @param output    collects the output pairs for the next reducer
     * @param reporter  allows sending counts back to the job driver
     */
    public void reduce( IntWritable key
                      , Iterator<Writable> values
                      , OutputCollector<IntWritable, Node> output
                      , Reporter reporter
                      )
    throws IOException
    {
        Node node = new Node(key.get(), new Text(""));

        while (values.hasNext())
        {
            Writable obj = values.next();

            if (obj instanceof IntWritable)
            {
                IntWritable id = (IntWritable) obj;
                node.addNeighbor(id.get());
            }
            else if (obj instanceof Node)
            {
                Node original = (Node) obj;
                node.setValue(original.getValue());
            }
        }

        output.collect(key, node);
    }
}

