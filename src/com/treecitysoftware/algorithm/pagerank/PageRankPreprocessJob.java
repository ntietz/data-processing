package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;

public class PageRankPreprocessJob
{
    public static void main(String... args)
    throws IOException
    {
        String inputPath = args[0];
        String outputPath = args[1];
        int numberOfReducers = Integer.valueOf(args[2]);
        long numberOfNodes = Long.valueOf(args[3]);

        JobConf conf = getJobConfiguration(inputPath, outputPath, numberOfReducers, numberOfNodes);
        RunningJob job = JobClient.runJob(conf);
        job.waitForCompletion();
    }

    public static JobConf getJobConfiguration( String inputPath
                                      , String outputPath
                                      , int numberOfReducers
                                      , long numberOfNodes
                                      )
    {
        JobConf conf = new JobConf(PageRankPreprocessJob.class);
        conf.setJobName("pagerank");

        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(WikiPage.class);

        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(PageRankNode.class);

        conf.setMapperClass(PageRankPreprocessMapper.class);
        conf.setReducerClass(PageRankPreprocessReducer.class);

        conf.setInputFormat(SequenceFileInputFormat.class);
        conf.setOutputFormat(SequenceFileOutputFormat.class);

        conf.setNumReduceTasks(numberOfReducers);

        conf.set("numberOfNodes", Long.toString(numberOfNodes));

        SequenceFileInputFormat.setInputPaths(conf, new Path(inputPath));
        SequenceFileOutputFormat.setOutputPath(conf, new Path(outputPath));

        return conf;
    }
}

