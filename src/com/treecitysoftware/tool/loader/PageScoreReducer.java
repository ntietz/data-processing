package com.treecitysoftware.tool.loader;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import java.io.*;
import java.util.*;
import java.sql.*;

public class PageScoreReducer
extends MapReduceBase
implements Reducer<IntWritable, PageRankNode, IntWritable, PageRankNode>
{
    private Connection dbConnection;

    private PreparedStatement insertScoreStatement = null;

    /**
     * Called when the reducer is created. We create a
     * database connection here.
     */
    public void configure(JobConf conf)
    {
        String url = conf.get("connectionString");
        String usr = conf.get("username");
        String pwd = conf.get("password");

        try
        {
            dbConnection = DriverManager.getConnection(url, usr, pwd);

            String insertScoreString = 
                "insert into page_scores " +
                "values(?, ?)";

            dbConnection.setAutoCommit(false);

            insertScoreStatement = dbConnection.prepareStatement(insertScoreString);
        }
        catch (SQLException sqle)
        {
            // We cry? 
            // ALOT
        }
    }

    /**
     * Receives (id, node) pairs, which are then loaded into the database.
     */
    public void reduce( IntWritable key
                      , Iterator<PageRankNode> value
                      , OutputCollector<IntWritable, PageRankNode> output
                      , Reporter reporter
                      )
    throws IOException
    {
        //Lets grab that data
        int pageID = key.get();
        PageRankNode node = value.next();
        double score = node.getValue();

        try
        {
            //Try and add the page table insert to the batch stack
            insertScoreStatement.setInt(1, pageID);
            insertScoreStatement.setDouble(2, score);
            insertScoreStatement.addBatch();
        }
        catch (SQLException e)
        {
            //The batch insert failed...
            reporter.incrCounter("FAILED INSERT", "SCORE", 1);
        }
    }

    /**
     * Called at the end of the object's lifetime. Cleans up the environments.
     */
    public void close()
    {
        //Let's execute those Batch queries
        try
        {
            insertScoreStatement.executeBatch();
            
            dbConnection.commit();

            insertScoreStatement.close();
            dbConnection.close();
        }
        catch (SQLException e)
        {
        }
        
    }
}
