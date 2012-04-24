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
     * Flag for first run set at true to start, 
     * set at false first time reduce is run
     */
    private boolean first = true;

    /**
     * Name of the file that stores the list of target node IDs
     */
    private String targetNodeFileName;

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
        //First time?
        if(first)
        {
            //Load in all of the target nodes into the tree set
            first = false;

            FileSystem fs = FileSystem.get(new Configuration());
            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                        fs.open(
                                            new Path(
                                                targetNodeFileName
                                ))));

            String line = in.readLine();

            while (line != null)
            {
                targetNodes.add(Integer.valueOf(line));
            }
        }
      
        //Hand it a blank BFSStatus (infinity, no path, not target)
        BFSNode node = value.next();
        node.setValue(new BFSStatus());

        //if target nodes contains the current ID:
        if(targetNodes.contains(key.get()))
        {
            //Set it as a target node
            node.setTarget(true);
        }

        //if the node is the source
        if(key.get() == sourceNodeID)
        {
            //set the path to itself, and set the distance to 0.
            ((BFSStatus)(node.getValue())).setDistance(0);
            ((BFSStatus)(node.getValue())).getPath().add(key.get());
        }

        //Emit the nodeID and Node
        output.collect(key, node);
    }
}
