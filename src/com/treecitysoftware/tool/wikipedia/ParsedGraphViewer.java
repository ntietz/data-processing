package com.treecitysoftware.common;

import com.treecitysoftware.data.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;

import java.io.*;

public class ParsedGraphViewer
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
        WikiPage page = new WikiPage();

        while (reader.next(id, page))
        {
            System.out.println(id.get() + ":\t" + page.getValue());
        }
    }
}

