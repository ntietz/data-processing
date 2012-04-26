package com.treecitysoftware.common;

import com.treecitysoftware.data.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;

import java.io.*;

public class PageRankGraphViewer
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
        PageRankNode node = new PageRankNode();
        int displayed = 0;

        while (displayed < numberToDisplay && reader.next(id, node))
        {
            String outputLine = id.get() + ":\t" + node.getValue();
            System.out.println(outputLine);
            ++displayed;
        }
    }
}

