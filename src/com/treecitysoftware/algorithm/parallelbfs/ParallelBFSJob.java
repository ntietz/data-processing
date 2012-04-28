package com.treecitysoftware.algorithm.parallelbfs;

import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.*;

public class ParallelBFSJob
{
    public static void main(String... args)
    throws IOException
    {
        String inputPath, outputPath;
        int numberOfReducers, maxDepth, targetGoalNumber;
        try
        {
            inputPath = args[0];
            outputPath = args[1];
            numberOfReducers = Integer.valueOf(args[2]);
            maxDepth = Integer.valueOf(args[3]);
            targetGoalNumber = Integer.valueOf(args[4]);

            ParallelBFSJob driver = new ParallelBFSJob();

            driver.run( inputPath
                      , outputPath
                      , numberOfReducers
                      , maxDepth
                      , targetGoalNumber
                      );
        }
        catch (Exception e) 
        {
            System.out.println("Usage : <...> bfs /input/path /output/path [numReducers] [maxDepth] [goalNumTargets]");
            System.exit(0);
        }
    }

    /**
     * Runs BFS until either the Maximum Depth has occured or until it finds 
     * enough Target Nodes in the BFS.
     * @param initialInputPath The Path to the initial input source
     * @param outputBasePath The Path that will have the multiple job results
     * @param maxDepthOfSearch The maximum depth the search will go
     * @param goalNumberOfTargetsFound The number of Target Nodes we are aiming to find
     */
    public void run( String initialInputPath
                   , String outputBasePath
                   , int numberOfReducers
                   , int maxDepthOfSearch
                   , int goalNumberOfTargetsFound
                   )
    throws IOException
    {
        boolean keepGoing = true;
        int round = 0;
        long targetsFoundSoFar = 0;

        String inputPath = initialInputPath;
        String outputPath = outputBasePath + "/" + round;

        while (keepGoing)
        {
            JobConf conf = getJobConfiguration( inputPath
                                              , outputPath
                                              , numberOfReducers
                                              );

            RunningJob job = JobClient.runJob(conf);
            job.waitForCompletion();

            long targetsFound = job.getCounters().findCounter("TARGETS", "FOUND").getCounter();
            targetsFoundSoFar += targetsFound;

            ++round;
            keepGoing = (round < maxDepthOfSearch) && (targetsFoundSoFar < goalNumberOfTargetsFound);
            inputPath = outputPath;
            outputPath = outputBasePath + "/" + round;
        }
    }

    /**
     * Creates a Configuration object based on the given input
     * @param inputPath The location of the input data
     * @param outputPath The location the result data will be stored
     * @param numberOfReducers The number of reducers to run
     */
    public static JobConf getJobConfiguration( String inputPath
                                             , String outputPath
                                             , int numberOfReducers
                                             )
    {
        JobConf conf = new JobConf(ParallelBFSJob.class);
        conf.setJobName("bfs");

        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(BFSNodeOrChange.class);

        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(BFSNode.class);

        conf.setMapperClass(ParallelBFSMapper.class);
        conf.setReducerClass(ParallelBFSReducer.class);

        conf.setInputFormat(SequenceFileInputFormat.class);
        conf.setOutputFormat(SequenceFileOutputFormat.class);

        conf.setNumReduceTasks(numberOfReducers);

        SequenceFileInputFormat.setInputPaths(conf, new Path(inputPath));
        SequenceFileOutputFormat.setOutputPath(conf, new Path(outputPath));

        return conf;
    }
}
