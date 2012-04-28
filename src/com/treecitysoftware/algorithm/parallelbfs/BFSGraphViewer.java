package com.treecitysoftware.common;

import com.treecitysoftware.data.*;
import com.treecitysoftware.algorithm.parallelbfs.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;

import java.io.*;

public class BFSGraphViewer
{
    public static void main(String... args)
    throws IOException
    {
        String inputPath = args[0];
        int numberToDisplay = Integer.valueOf(args[1]);

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        SequenceFile.Reader reader = 
            new SequenceFile.Reader( fs
                                   , new Path(inputPath)
                                   , conf
                                   );

        IntWritable id = new IntWritable();
        BFSNode node = new BFSNode();
        int displayed = 0;

        while (displayed < numberToDisplay && reader.next(id, node))
        {
            BFSStatus nodePayload = node.getValue();
            String outputLine = id.get() + ":\t" + nodePayload.getDistance();
            System.out.println(outputLine);
            ++displayed;
        }
    }
}
