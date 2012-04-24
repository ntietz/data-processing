package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import java.io.*;
import java.util.*;

/**
 * Reducer used in the BFS step of the BFS job.
 * Returns and Id and Node with shortest paths found back to src
 */
public class ParallelBFSReducer
extends MapReduceBase
implements Reducer<IntWritable, BFSNodeOrStatus, IntWritable, BFSNode>
{
    /**
     * Receives either (id,node) or (id, status) pairs from the mappers and 
     * picks the smallest status number, and re-emits the node with updated status
     * @param key The node's id
     * @param value The collection of Node objects that correspond to the ID
     * @param output An OutputCollector object to recieve the initialized nodes
     * @param reporter The default reporting object
     */
    public void reduce(IntWritable key
                     , Iterator<BFSNodeOrStatus> values
                     , OutputCollector<IntWritable, BFSNode> output
                     , Reporter reporter
                     )
    throws IOException
    {
        int shortestDistance = Integer.MAX_VALUE;
        List<Integer> pathToSource = null;
        BFSNode resultNode = null;

        while(values.hasNext())
        {
            BFSNodeOrStatus val = values.next();

            if(val.isNode())
            {
                resultNode = val.node;
            }

            if(val.isStatus())
            {
                BFSStatus stat = val.status;
                int thisDistance = stat.getDistance();
                if(thisDistance < shortestDistance)
                {
                    shortestDistance = thisDistance;
                    pathToSource = stat.getPath();
                }
            }
        }

        //Construct the output
        BFSStatus statusToSend = new BFSStatus(shortestDistance, pathToSource);
        resultNode.setValue(statusToSend);
        output.collect(key, resultNode);
    }
}
