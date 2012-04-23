package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.common.*;
import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;

public class ParserDriver
{
    public static void main(String... args)
    throws IOException
    {
        String inputPath = args[0];
        String outputPath = args[1];
        int numberOfReducers = Integer.valueOf(args[2]);

        ParserDriver driver = new ParserDriver();
        driver.run(inputPath, outputPath, numberOfReducers);
    }

    public void run(String inputPath, String outputPath, int numberOfReducers)
    throws IOException
    {
        String tmpPath = "/tmp/" + outputPath;

        JobConf conf = getIdReplaceConf(inputPath, tmpPath, numberOfReducers);
        RunningJob job = JobClient.runJob(conf);
        job.waitForCompletion();

        conf = getAdjacencyConf(tmpPath, outputPath, numberOfReducers);
        job = JobClient.runJob(conf);
        job.waitForCompletion();

        // TODO : cleanup (delete tmpPath)
    }

    public JobConf getIdReplaceConf( String inputPath
                                   , String outputPath
                                   , int numberOfReducers
                                   )
    {
        JobConf conf = new JobConf(ParserDriver.class);
        conf.setJobName("wiki-graph-parsing");

        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(PageOrEdge.class);

        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(PageOrEdge.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(SequenceFileOutputFormat.class);

        conf.setMapperClass(ParserMapper.class);
        conf.setReducerClass(ParserIdReplaceReducer.class);

        conf.setNumReduceTasks(numberOfReducers);

        TextInputFormat.setInputPaths(conf, new Path(inputPath));
        SequenceFileOutputFormat.setOutputPath(conf, new Path(outputPath));

        return conf;
    }

    public JobConf getAdjacencyConf( String inputPath
                                   , String outputPath
                                   , int numberOfReducers
                                   )
    {
        JobConf conf = new JobConf(ParserDriver.class);
        conf.setJobName("wiki-graph-parsing");

        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(PageOrEdge.class);

        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(WikiPage.class);

        conf.setInputFormat(SequenceFileInputFormat.class);
        conf.setOutputFormat(SequenceFileOutputFormat.class);

        conf.setMapperClass(WikiPassThroughMapper.class);
        conf.setReducerClass(ParserAdjacencyListReducer.class);

        conf.setNumReduceTasks(numberOfReducers);

        TextInputFormat.setInputPaths(conf, new Path(inputPath));
        SequenceFileOutputFormat.setOutputPath(conf, new Path(outputPath));

        return conf;
    }

}

