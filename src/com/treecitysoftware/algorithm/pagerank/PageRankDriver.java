package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class PageRankDriver
{
    public static void main(String... args)
    {

    }

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

        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(Writable.class);

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Node.class);

        conf.setMapperClass(PageRankMapper.class);
        conf.setReducerClass(PageRankReducer.class);

        conf.setInputFormat(SequenceFileInputFormat.class);
        conf.setOutputFormat(SequenceFileOutputFormat.class);

        conf.setNumReduceTasks(numberOfReducers);

        conf.set("numberOfNodes", Double.toString(numberOfNodes));
        conf.set("dampingFactor", Double.toString(dampingFactor));
        conf.set("adjustmentBonus", Double.toString(lostWeight / numberOfNodes));

        SequenceFileInputFormat.setInputPaths(conf, new Path(inputPath));
        SequenceFileOutputFormat.setOutputPath(conf, new Path(outputPath));

        return conf;
    }
}

