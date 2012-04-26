package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;

import java.io.*;
import java.util.*;

/**
 * Mapper of the 'BFS' job in the "ParallelBFS" Driver
 * Emits distances and paths to each node's neighbor
 */
public class ParallelBFSMapper
extends MapReduceBase
implements Mapper<IntWritable, BFSNode, IntWritable, BFSNodeOrChange>
{
    /**
     * Takes in (id, node) pairs and propogates their distance values
     * to each of of their neighbors.
     * Finally, they emit themselves.
     * @param key The Node ID
     * @param Node The Node object
     * @param output An Output Collector that collects (id, node) pairs
     * @param reporter Default reporter object
     */
    public void map(IntWritable key
                  , BFSNode value
                  , OutputCollector<IntWritable, BFSNodeOrChange> output
                  , Reporter reporter
                  )
    throws IOException
    {
        BFSStatus currentPayload = value.getValue();
        
        if (currentPayload.needsExpansion())
        {
            int dist = currentPayload.getDistance();

            for (Integer eachID : currentPayload.getExpansionSet())
            {
                output.collect( new IntWritable(eachID.intValue())
                              , new BFSNodeOrChange(new BFSChange( key.get(), (dist + 1)))
                              );
            }

            currentPayload.setExpansion(false);

            Set<Integer> s = currentPayload.getExpansionSet();
            s.clear();
            currentPayload.setExpansionSet(s);

            value.setValue(currentPayload);
        }
        
        output.collect(key, new BFSNodeOrChange(value));
    }
}
