package com.treecitysoftware.algorithm.pagerank;

import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;

public class PageRankDriver
{
    /**
     * Runs pagerank until convergence with the given input path and output path.
     * The data at the input path IS preserved.
     * The data ends up at the output path after convergence.
     * @param initialInputPath      the path for the initial graph
     * @param eventualOutputPath    the path for the final output
     */
    public void run(String initialInputPath, String outputBasePath)
    throws IOException
    {
        boolean finished = false;
        int round = 0;

        String inputPath = initialInputPath;
        String outputPath = outputBasePath + "/" + round;

        int numberOfReducers = 1;
        int numberOfNodes = 0;
        double lostWeight = 0.0;
        double dampingFactor = 0.0;

        while (!finished)
        {

            JobConf conf = getJobConfiguration( inputPath
                                              , outputPath
                                              , numberOfReducers
                                              , numberOfNodes
                                              , lostWeight
                                              , dampingFactor
                                              );

            RunningJob job = JobClient.runJob(conf);
            job.waitForCompletion();

            // TODO calculate lost weight, check convergence

            // TODO check for convergence here
            finished = true;
        }

        // TODO since we're done, copy over to the location of eventualOutputPath
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

        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(Writable.class);

        conf.setOutputKeyClass(IntWritable.class);
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

