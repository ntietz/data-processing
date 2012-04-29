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

public class PageLinkReducer
extends MapReduceBase
implements Reducer<IntWritable, WikiPage, IntWritable, WikiPage>
{
    private Connection dbConnection;

    private PreparedStatement insertPageStatement = null;
    private PreparedStatement insertLinkStatement = null;

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

            String insertPageString = 
                "insert into page " +
                "values(?, ?)";

            String insertLinkString =
                "insert into links " +
                "values(?, ?)";
            
            dbConnection.setAutoCommit(false);

            insertPageStatement = dbConnection.prepareStatement(insertPageString);
            insertLinkStatement = dbConnection.prepareStatement(insertPageString);
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
                      , Iterator<WikiPage> value
                      , OutputCollector<IntWritable, WikiPage> output
                      , Reporter reporter
                      )
    throws IOException
    {
        //Lets grab that data
        int pageID = key.get();
        WikiPage node = value.next();
        String title = node.getValue();
        Set<Integer> neighbors = node.getNeighbors();

        try
        {
            //Try and add the page table insert to the batch stack
            insertPageStatement.setInt(1, pageID);
            insertPageStatement.setString(2, title);
            insertPageStatement.addBatch();
        }
        catch (SQLException e)
        {
            //The batch insert failed...
            reporter.incrCounter("FAILED INSERT", "PAGES", 1);
        }

        //Try and add the links table inserts to the batch stack
        for (Integer neighborID : neighbors)
        {
            try
            {
                insertLinkStatement.setInt(1, pageID);
                insertLinkStatement.setInt(2, neighborID.intValue());
                insertLinkStatement.addBatch();
            }
            catch (SQLException e)
            {
                //One of the batch inserts failed
                reporter.incrCounter("FAILED INSERT", "LINKS", 1);
            }
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
            insertPageStatement.executeBatch();
            insertLinkStatement.executeBatch();
            
            dbConnection.commit();

            insertPageStatement.close();
            insertLinkStatement.close();
            dbConnection.close();
        }
        catch (SQLException e)
        {
        }
        
    }
}
