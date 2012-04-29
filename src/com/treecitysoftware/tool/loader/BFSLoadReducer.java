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

public class BFSLoadReducer
extends MapReduceBase
implements Reducer<FromToPair, IntWritable, FromToPair, IntWritable>
{
    private Connection dbConnection;

    private PreparedStatement insertBFSStatement = null;

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

            String insertBFSString = 
                "insert into bfs " +
                "values(?, ?, ?)";

            dbConnection.setAutoCommit(false);

            insertBFSStatement = dbConnection.prepareStatement(insertBFSString);
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
    public void reduce( FromToPair key
                      , Iterator<IntWritable> value
                      , OutputCollector<FromToPair, IntWritable> output
                      , Reporter reporter
                      )
    throws IOException
    {
        //Lets grab that data
        int srcNode = key.left;
        int desNode = key.right;
        int distance = value.next().get();

        try
        {
            //Try and add the page table insert to the batch stack
            insertBFSStatement.setInt(1, srcNode);
            insertBFSStatement.setInt(2, desNode);
            insertBFSStatement.setInt(3, distance);
            insertBFSStatement.addBatch();
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
            insertBFSStatement.executeBatch();
            
            dbConnection.commit();

            insertBFSStatement.close();
            dbConnection.close();
        }
        catch (SQLException e)
        {
        }
        
    }
}
