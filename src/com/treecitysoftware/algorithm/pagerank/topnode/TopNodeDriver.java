package com.treecitysoftware.algorithm.pagerank.topnode;

import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;

public class TopNodeDriver
{
    public static void main(String... args)
    throws IOException
    {
        String inputPath = args[0];
        String outputPath = args[1];

        TopNodeDriver driver = new TopNodeDriver();
        driver.run(inputPath, outputPath);
    }

    public void run( String inputPath
                   , String outputPath
                   )
    throws IOException
    {
        JobConf conf = getJobConfiguration(inputPath, outputPath);
        RunningJob job = JobClient.runJob(conf);
        job.waitForCompletion();
    }

    public JobConf getJobConfiguration( String inputPath
                                      , String outputPath
                                      )
    {
        JobConf conf = new JobConf(TopNodeDriver.class);
        conf.setJobName("topnodes");

        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(PageRankNode.class);

        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(DoubleWritable.class);

        conf.setMapperClass(TopNodeMapper.class);
        conf.setReducerClass(TopNodeReducer.class);

        conf.setInputFormat(SequenceFileInputFormat.class);
        conf.setOutputFormat(SequenceFileOutputFormat.class);

        conf.setNumReduceTasks(1);

        SequenceFileInputFormat.setInputPaths(conf, new Path(inputPath));
        SequenceFileOutputFormat.setOutputPath(conf, new Path(outputPath));

        return conf;
    }
}

