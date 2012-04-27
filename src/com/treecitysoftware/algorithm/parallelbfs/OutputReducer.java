package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import java.io.*;
import java.util.*;

public class OutputReducer
extends MapReduceBase
implements Reducer<IntWritable, BFSNode, FromToPair, IntWritable>
{
    /**
     * The ID of the source node that we have searched 
     * from
     */
    private int sourceNodeID;

    /**
     * Called when the reducer is created. We store the 
     * node that everything is coming from.
     */
    public void configure(JobConf conf)
    {
        sourceNodeID = Integer.valueOf(conf.get("sourceNodeID"));
    }

    /**
     * Receives (id, node) pairs, which are filtered to be
     * only target nodes that have distances. For each pair we 
     * receive, we emit a ((src, dest), distance) tuple
     */
    public void reduce( IntWritable key
                      , Iterator<BFSNode> value
                      , OutputCollector<FromToPair, IntWritable> output
                      , Reporter reporter
                      )
    throws IOException
    {
        BFSStatus valuePayload = value.next().getValue();
        output.collect( new FromToPair(sourceNodeID, key.get())
                      , new IntWritable(valuePayload.getDistance())
                      );
    }
}
