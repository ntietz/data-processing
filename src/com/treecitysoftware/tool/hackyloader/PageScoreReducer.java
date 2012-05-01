package com.treecitysoftware.tool.hackyloader;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import java.io.*;
import java.util.*;

public class PageScoreReducer
extends MapReduceBase
implements Reducer<IntWritable, PageRankNode, Text, Text>
{
    private static final Text blank = new Text("");

    /**
     * Receives (id, node) pairs, which are then loaded into the database.
     */
    public void reduce( IntWritable key
                      , Iterator<PageRankNode> value
                      , OutputCollector<Text, Text> output
                      , Reporter reporter
                      )
    throws IOException
    {
        //Lets grab that data
        int pageId = key.get();
        PageRankNode node = value.next();
        double score = node.getValue();

        String insertLine = "insert into `page_scores` values("
            + pageId + ", " + score + ");";

        output.collect(new Text(insertLine), blank);
    }
}
