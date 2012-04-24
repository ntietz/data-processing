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
     * TODO THESE PARAMETERS ARE NOT UPDATED
     */
    public void run( String initialInputPath
                   , String outputBasePath
                   , int numberOfReducers
                   , int numberOfNodes
                   , double dampingFactor
                   )
    throws IOException
    {
        boolean keepGoing = true;
        int round = 0;

        String inputPath = initialInputPath;
        String outputPath = outputBasePath + "/" + round;

        double lostWeight = 0.0;
        int maxRounds = 20;
        double threshold = 0.05;

        while (keepGoing)
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

            // TODO check for convergence here
            long scaledChange = job.getCounters().findCounter("WEIGHT", "CHANGED").getCounter();
            double change = ((double)scaledChange) / PageRankConstants.scalingFactor;

            ++round;
            keepGoing = (round < maxRounds) && change > threshold;
            inputPath = outputPath;
            outputPath = outputBasePath + "/" + round;

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
        conf.setMapOutputValueClass(NodeOrContribution.class);

        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(PageRankNode.class);

        conf.setMapperClass(PageRankMapper.class);
        conf.setReducerClass(PageRankReducer.class);

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

