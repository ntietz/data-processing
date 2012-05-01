package com.treecitysoftware.tool.hackyloader;

import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.*;

import java.io.*;
import java.util.*;

public class BFSLoaderJob
{
    public static void main(String... args)
    throws IOException
    {
        String inputPath = args[0];
        String outputPath = args[1];
        int numberOfReducers = Integer.valueOf(args[2]);

        JobConf conf = getJobConfiguration( inputPath
                                          , outputPath
                                          , numberOfReducers
                                          );

        //run job
        RunningJob job = JobClient.runJob(conf);
        job.waitForCompletion();
    }

    public static JobConf getJobConfiguration( String inputPath
                                             , String outputPath
                                             , int numberOfReducers
                                             )
    {
        JobConf conf = new JobConf(BFSLoaderJob.class);
        conf.setJobName("load-bfs");

        conf.setMapOutputKeyClass(FromToPair.class);
        conf.setMapOutputValueClass(IntWritable.class);

        conf.setOutputKeyClass(FromToPair.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(BFSLoadMapper.class);
        conf.setReducerClass(BFSLoadReducer.class);

        conf.setInputFormat(SequenceFileInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        conf.setNumReduceTasks(numberOfReducers);

        SequenceFileInputFormat.setInputPaths(conf, new Path(inputPath));
        TextOutputFormat.setOutputPath(conf, new Path(outputPath));

        return conf;
    }
}
