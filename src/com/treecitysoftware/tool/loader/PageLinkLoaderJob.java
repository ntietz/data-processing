package com.treecitysoftware.tool.loader;

import com.treecitysoftware.data.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.*;

import java.io.*;
import java.util.*;

public class PageLinkLoaderJob
{
    public static void main(String... args)
    throws IOException
    {
        String inputPath, configPath;
        int numberOfReducers;

        Scanner configScanner = null;
        
        try
        {
            //Assign args
            inputPath = args[0];
            configPath = args[1];
            numberOfReducers = Integer.valueOf(args[2]);

            //Read configuration stuff
            File configFile = new File(configPath);
            configScanner = new Scanner(configFile);

            //Make strings
            String username = configScanner.nextLine();
            String password = configScanner.nextLine();
            String connectionString = configScanner.nextLine();

            //create the JobConf
            JobConf conf = getJobConfiguration( inputPath
                                              , configPath
                                              , numberOfReducers
                                              , username
                                              , password
                                              , connectionString
                                              );

            //run job
            RunningJob job = JobClient.runJob(conf);
            job.waitForCompletion();
        }
        catch (IOException ioe)
        {
            System.out.println("Cannot open configuration file");
        }
        catch (Exception e) 
        {
            System.out.println("Usage : <...> load-page-link /input/path /config/path <numReducers>");
        }
        finally
        {
            if (configScanner != null)
            {
                configScanner.close();
            }
            System.exit(0);
        }
    }

    public static JobConf getJobConfiguration( String inputPath
                                             , String configPath
                                             , int numberOfReducers
                                             , String username
                                             , String password
                                             , String connectionString
                                             )
    {
        JobConf conf = new JobConf(PageLinkLoaderJob.class);
        conf.setJobName("load-page-link");

        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(WikiPage.class);

        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(WikiPage.class);

        conf.setMapperClass(PageLinkMapper.class);
        conf.setReducerClass(PageLinkReducer.class);

        conf.setInputFormat(SequenceFileInputFormat.class);
        conf.setOutputFormat(NullOutputFormat.class);

        conf.setNumReduceTasks(numberOfReducers);

        conf.set("username", username);
        conf.set("password", password);
        conf.set("connectionString", connectionString);

        SequenceFileInputFormat.setInputPaths(conf, new Path(inputPath));

        return conf;
    }
}
