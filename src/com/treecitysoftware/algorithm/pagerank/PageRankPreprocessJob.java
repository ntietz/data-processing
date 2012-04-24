package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class PageRankPreprocessJob
{

    // TODO FIX THIS TO BE RIGHT TODO TODO TODO TODO 
    public JobConf getJobConfiguration( String inputPath
                                      , String outputPath
                                      , int numberOfReducers
                                      , long numberOfNodes
                                      , double lostWeight
                                      , double dampingFactor
                                      )
    {
        JobConf conf = new JobConf(PageRankDriver.class);
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
        conf.set("dampingFactor", Double.toString(dampingFactor));
        conf.set("adjustmentBonus", Double.toString(lostWeight / numberOfNodes));

        SequenceFileInputFormat.setInputPaths(conf, new Path(inputPath));
        SequenceFileOutputFormat.setOutputPath(conf, new Path(outputPath));

        return conf;
    }
}

