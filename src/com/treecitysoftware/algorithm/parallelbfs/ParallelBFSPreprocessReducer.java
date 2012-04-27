package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import java.io.*;
import java.util.*;

public class ParallelBFSPreprocessReducer
extends MapReduceBase
implements Reducer<IntWritable, BFSNode, IntWritable, BFSNode>
{
    /**
     * The ID of the sourceNode that we are currently
     * searching from.
     */
    private int sourceNodeID;

    /**
     * Name of the file that stores the list of target node IDs
     */
    private String targetNodeFileName;

    /**
     * Number of nodes to take from the top of Target's 
     * sequence file
     */
    private int targetLength;

    /**
     * Set that is created to hold the nodes in the target file
     */
    private Set<Integer> targetNodes;

    /**
     * Runs before any reducing begins; Sets the file name and 
     * the sourceNodeID's
     * @param conf The JobConf object that is created by the driver
     */
    public void configure(JobConf conf)
    {
        sourceNodeID = Integer.valueOf(conf.get("sourceNodeID"));
        targetNodeFileName = conf.get("targetNodeFileName");
        targetNodes = new TreeSet<Integer>();
        targetLength = Integer.valueOf(conf.get("targetLength"));

        Configuration c = new Configuration();
        FileSystem fs = FileSystem.get(c);
        
        SequenceFile.Reader reader = 
            new SequenceFile.Reader( fs
                                   , new Path(targetNodeFileName)
                                   , c
                                   );

        IntWritable id = new IntWritable();
        WikiPage page = new WikiPage();

        int index = 0;
        while ((reader.next(id, page)) && (index < targetLength))
        {
            //Construct the set of //Construct the set of target id's
            targetNodes.add(new Integer(id.get()));
            ++index;
        }
    }

    /**
     * Recieves (id, node) pairs from the mappers and for each pair
     * it assigns a payload of type BFSStatus.
     * If a node is from the target list, it marks it as so.
     * If the node is the source, it gets the first distance assigned to it.
     * @param key The node's id
     * @param value The collection of Node objects that correspond to the ID
     * @param output An OutputCollector object to recieve the initialized nodes
     * @param reporter The default reporting object
     */
    public void reduce(IntWritable key
                     , Iterator<BFSNode> value
                     , OutputCollector<IntWritable, BFSNode> output
                     , Reporter reporter
                     )
    throws IOException
    {

        // Get the node associated with the ID
        BFSNode node = value.next();

        //if target node set contains the current ID:
        if(targetNodes.contains(key.get()))
        {
            //Set it as a target node
            node.setTarget(true);
        }

        //if the node ID is the source ID
        if(key.get() == sourceNodeID)
        {
            BFSStatus sourceStatus = new BFSStatus(0, true, node.getNeighbors());
            node.setValue(sourceStatus);
        }

        //Emit the nodeID and Node
        output.collect(key, node);
    }
}
