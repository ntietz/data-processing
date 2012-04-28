package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;

public class ParallelBFSOutputJob
{
    public static void main(String... args)
    throws IOException
    {
        String inputPath, outputPath;
        int numberOfReducers, sourceNodeID;

        try
        {
            inputPath = args[0];
            outputPath = args[1];
            numberOfReducers = Integer.valueOf(args[2]);
            sourceNodeID = Integer.valueOf(args[3]);

            JobConf conf = getJobConfiguration( inputPath
                                              , outputPath
                                              , numberOfReducers
                                              , sourceNodeID
                                              );

            RunningJob job = JobClient.runJob(conf);
            job.waitForCompletion();
        }
        catch (Exception e) 
        {
            System.out.println("Usage : <...> bfs-output /input/path /output/path <numReducers> <sourceNodeID>");
            System.exit(0);
        }
    }

    public static JobConf getJobConfiguration( String inputPath
                                             , String outputPath
                                             , int numberOfReducers
                                             , int sourceNodeID
                                             )
    {
        JobConf conf = new JobConf(ParallelBFSOutputJob.class);
        conf.setJobName("bfs-output");

        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(BFSNode.class);

        conf.setOutputKeyClass(FromToPair.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(OutputMapper.class);
        conf.setReducerClass(OutputReducer.class);

        conf.setInputFormat(SequenceFileInputFormat.class);
        conf.setOutputFormat(SequenceFileOutputFormat.class);

        conf.setNumReduceTasks(numberOfReducers);

        conf.set("sourceNodeID", Integer.toString(sourceNodeID));

        SequenceFileInputFormat.setInputPaths(conf, new Path(inputPath));
        SequenceFileOutputFormat.setOutputPath(conf, new Path(outputPath));

        return conf;
    }
}
