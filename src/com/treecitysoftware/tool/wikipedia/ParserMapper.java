package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class ParserMapper
extends MapReduceBase
implements Mapper<LongWritable, Text, Text, Writable>
{
    /**
     * Takes in a line of text and parse it into nodes and edges.
     * If the line contains node data, outgoing is an (title, node) pair.
     * If the line contains edge data, the outgoing is an (title, id) pair.
     * In the case of edge data, the first title is the title of the target page.
     * @param key       an identifier for the line
     * @param value     the line of to-be-parsed text
     * @param output    collects the output pairs for the reducers
     * @param reporter  allows sending counts back to the job driver
     */
    public void map( LongWritable key
                   , Text line
                   , OutputCollector<Text, Writable> output
                   , Reporter reporter
                   )
    throws IOException
    {
        DataLine parsedLine = new DataLine(line.toString());

        if (parsedLine.containsPageData())
        {
            List<StringPair> pages = parsedLine.getPages();

            for (StringPair each : pages)
            {
                int id = Integer.valueOf(each.left);
                String title = each.right;

                Text payload = new Text(title);
                Node node = new Node(id, payload);

                output.collect(new Text(title), node);
            }
        }
        else if (parsedLine.containsEdgeData())
        {
            List<StringPair> edges = parsedLine.getEdges();

            for (StringPair each : edges)
            {
                int id = Integer.valueOf(each.left);
                String title = each.right;

                // emit: (targetTitle, originator id)
                output.collect(new Text(title), new IntWritable(id));
            }
        }
    }
}

