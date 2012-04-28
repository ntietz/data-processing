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
implements Reducer<IntWritable, BFSNodeOrChange, IntWritable, BFSNode>
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
                     , Iterator<BFSNodeOrChange> values
                     , OutputCollector<IntWritable, BFSNode> output
                     , Reporter reporter
                     )
    throws IOException
    {
        //Handles for the Node, the smallest Distance received,
        // and the set of nodes we've received from.
        BFSNode keyNode = null;
        int smallestDistance = Integer.MAX_VALUE;
        Set<Integer> receivedFrom = new TreeSet<Integer>();

        //Reclaim the information received
        while (values.hasNext())
        {
            BFSNodeOrChange incoming = values.next();
            
            if (incoming.isNode())
            {
                keyNode = incoming.node;
            }
            else
            {
                if(incoming.change.getDistance() < smallestDistance)
                {
                    smallestDistance = incoming.change.getDistance();
                    receivedFrom.add(incoming.change.getSendingNode());
                }
            }
        }

        if (keyNode != null && keyNode.getValue().getDistance() != Integer.MAX_VALUE)
        {
            reporter.incrCounter("NODES", "REACHED", 1);
        }

        //If d is infinity, we didn't get anything
        if (smallestDistance == Integer.MAX_VALUE)
        {
            output.collect(key, keyNode);
            return;
        }
        else
        {
            BFSStatus keyNodePayload = keyNode.getValue();

            //If our node already has a value other than infinity
            if (keyNodePayload.getDistance() != Integer.MAX_VALUE)
            {
                //We already have the shortest path
                output.collect(key, keyNode);
                return;
            }
            else
            {
                //set distance to source
                keyNodePayload.setDistance(smallestDistance);

                reporter.incrCounter("NODES", "FOUND", 1);

                //Well we found a target, lets make note of it
                if (keyNode.isTarget())
                {
                    reporter.incrCounter("TARGETS", "FOUND",1);
                }

                //Notify only those who haven't called us
                Set<Integer> nodesToNotify = new TreeSet<Integer>(keyNode.getNeighbors());
                nodesToNotify.removeAll(receivedFrom);

                if (nodesToNotify.size() == 0)
                {
                    //No one to notify that hasn't JUST sent us info
                    keyNodePayload.setExpansion(false);
                }
                else
                {
                    //There are nodes to notify, so we should expand
                    keyNodePayload.setExpansion(true);
                }

                //After all of this we set the expansion set
                //whether it's got nodes or not
                keyNodePayload.setExpansionSet(nodesToNotify);

                //attach the new status to the key node
                keyNode.setValue(keyNodePayload);

                //Emit
                output.collect(key, keyNode);
            }
        }
    }
}
