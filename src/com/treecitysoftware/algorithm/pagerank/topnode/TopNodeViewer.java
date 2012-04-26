package com.treecitysoftware.common;

import com.treecitysoftware.data.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;

import java.io.*;

public class TopNodeViewer
{
    public static void main(String... args)
    throws IOException
    {
        String inputPath = args[0];

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        SequenceFile.Reader reader =
            new SequenceFile.Reader( fs
                                   , new Path(inputPath)
                                   , conf
                                   );

        IntWritable id = new IntWritable();
        DoubleWritable score = new DoubleWritable();

        while (reader.next(id, score))
        {
            String outputLine = id.get() + ":\t" + score.get();
            System.out.println(outputLine);
        }
    }
}

