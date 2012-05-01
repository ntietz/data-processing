package com.treecitysoftware.tool.hackyloader;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import java.io.*;
import java.util.*;

public class BFSLoadReducer
extends MapReduceBase
implements Reducer<FromToPair, IntWritable, Text, Text>
{
    private static final Text blank = new Text("");

    /**
     * Receives (id, node) pairs, which are then loaded into the database.
     */
    public void reduce( FromToPair key
                      , Iterator<IntWritable> value
                      , OutputCollector<Text, Text> output
                      , Reporter reporter
                      )
    throws IOException
    {
        int srcNode = key.left;
        int desNode = key.right;
        int distance = value.next().get();

        String insertString = "insert into `bfs` values("
            + srcNode + ", "
            + desNode + ", "
            + distance + ");";
        
        output.collect(new Text(insertString), blank);
    }
}
