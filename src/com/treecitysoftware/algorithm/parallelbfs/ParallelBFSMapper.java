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
implements Mapper<IntWritable, BFSNode, IntWritable, BFSNodeOrStatus>
{
    private int maxDepth;

    public void configure(JobConf conf)
    {
        maxDepth = Integer.valueOf(conf.get("maxDepth"));
    }

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
                  , OutputCollector<IntWritable, BFSNodeOrStatus> output
                  , Reporter reporter
                  )
    throws IOException
    {
        //Get the distance so far
        BFSStatus nodestatus = value.getValue();
        int distanceSoFar = nodestatus.getDistance();
        
        //If it's not infinity, and the distance isn't too large, continue
        if((distanceSoFar != Integer.MAX_VALUE) && ((distanceSoFar + 1) < maxDepth))
        {
            //Get the path from the current node
            List<Integer> pathToStart = nodestatus.getPath();
        
            // go through the values, emitting the new distances and paths along the way
            for (int id : value.getNeighbors())
            {
                List<Integer> pathToSend = new ArrayList(pathToStart);
                pathToSend.add(id);
                
                BFSStatus statusToSend = new BFSStatus(distanceSoFar + 1, pathToSend);

                output.collect(new IntWritable(id), new BFSNodeOrStatus(statusToSend));
            }

            //Then emit this node
            output.collect(key, new BFSNodeOrStatus(value));
        }
        else
        {
            //Well, either this node is not discovered yet, or we've hit the bounds
            //So, we don't send any statuses to the neighbors, we just emit this node
            output.collect(key, new BFSNodeOrStatus(value));

            if(distanceSoFar != Integer.MAX_VALUE)
            {
                //If we got here and the integer is not infinity, it means it's too deep
                //Increment the graph bound counter

                // TODO : Increment a counter here
            }
        }
    }
}
