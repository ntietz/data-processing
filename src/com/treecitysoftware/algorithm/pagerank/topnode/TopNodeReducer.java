package com.treecitysoftware.algorithm.pagerank.topnode;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class TopNodeReducer
extends MapReduceBase
implements Reducer<DoubleWritable, IntWritable, IntWritable, DoubleWritable>
{
    private final int numberToOutput = 100000;
    private static int numberOutputed = 0;

    public void reduce( DoubleWritable key
                      , Iterator<IntWritable> values
                      , OutputCollector<IntWritable, DoubleWritable> output
                      , Reporter reporter
                      )
    throws IOException
    {
        DoubleWritable score = new DoubleWritable(1 - key.get());

        while (numberOutputed < numberToOutput && values.hasNext())
        {
            IntWritable id = values.next();

            output.collect(id, score);

            reporter.incrCounter("NUMBER", "OUTPUT", 1);
            ++numberOutputed;
        }
    }
}

