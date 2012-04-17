package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

public class PageRankMapper
extends MapReduceBase
implements Mapper<Text, Node, Text, Writable>
{
    public void map( Text key
                   , Node value
                   , OutputCollector<Text, Writable> output
                   , Reporter reporter
                   )
    throws IOException
    {
        double currentScore = ((DoubleWritable)value.getValue()).get();
        double numberOfNeighbors = value.numberOfNeighbors();

        if (numberOfNeighbors > 0)
        {
            Contribution contribution = new Contribution(currentScore / numberOfNeighbors);

            List<String> neighbors = value.getNeighbors();
            for (String each : neighbors)
            {
                Text outputKey = new Text(each);
                output.collect(outputKey, (Writable)contribution);
            }
        }

        output.collect(key, (Writable)value);
    }

}

