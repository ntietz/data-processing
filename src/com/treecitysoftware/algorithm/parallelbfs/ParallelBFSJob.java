package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;

public class ParallelBFSPreprocessJob
{
    public static void main(String... args)
    throws IOException
    {
        try
        {
            
        }
        catch (Exception e) 
        {
            System.out.println("Usage : <...>");
            System.exit(0);
        }

        JobConf conf = getJobConfiguration( inputPath
                                          , outputPath
                                          , numberOfReducers
                                          );
        RunningJob job = JobClient.runJob(conf);
        job.waitForCompletion();
    }

    public static JobConf getJobConfiguration( String inputPath
                                             , String outputPath
                                             , int numberOfReducers
                                             , String targetNodeFile
                                             , int numberOfTargets
                                             , int sourceNodeID
                                             )
    {
        JobConf conf = new JobConf(ParallelBFSPreprocessJob.class);
        conf.setJobName("pre-bfs");

        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(BFSNode.class);

        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(BFSNode.class);

        conf.setMapperClass(ParallelBFSPreprocessMapper.class);
        conf.setReducerClass(ParallelBFSPreprocessReducer.class);

        conf.setInputFormat(SequenceFileInputFormat.class);
        conf.setOutputFormat(SequenceFileOutputFormat.class);

        conf.setNumReduceTasks(numberOfReducers);
        
        conf.set("sourceNodeID", Integer.toString(sourceNodeID));
        conf.set("targetNodeFileName", targetNodeFile);
        conf.set("targetLength", Integer.toString(numberOfTargets));

        SequenceFileInputFormat.setInputPaths(conf, new Path(inputPath));
        SequenceFileOutputFormat.setOutputPath(conf, new Path(outputPath));

        return conf;
    }
}
