package com.treecitysoftware.common;

import com.treecitysoftware.algorithm.pagerank.*;
import com.treecitysoftware.algorithm.pagerank.topnode.*;
import com.treecitysoftware.tool.datagenerator.*;
import com.treecitysoftware.tool.wikipedia.*;

import java.io.*;

public class CommonLauncher
{
    public static void main(String... args)
    throws IOException
    {
        if (args.length < 1)
        {
            System.out.println("Choices:");
            System.out.println("    parse");
            System.out.println("    view-parsed-graph");
            System.out.println("    make-test-data");
            System.out.println("    pagerank");
            System.out.println("    pre-pagerank");
            System.out.println("    topnodes");
            System.out.println("");
            System.exit(1);
        }

        int numberOfArguments = args.length - 1;
        String[] arguments = new String[numberOfArguments];
        System.arraycopy(args, 1, arguments, 0, numberOfArguments);

        String command = args[0];

        System.out.println(command);

        if (command.equals("parse"))
        {
            ParserDriver.main(arguments);
        }
        else if (command.equals("view-parsed-graph"))
        {
            System.out.println("viewing");
            ParsedGraphViewer.main(arguments);
        }
        else if (command.equals("make-test-data"))
        {
            System.out.println("generating");
            TestDataGenerator.main(arguments);
        }
        else if (command.equals("pagerank"))
        {
            System.out.println("page ranking");
            PageRankDriver.main(arguments);
        }
        else if (command.equals("pre-pagerank"))
        {
            System.out.println("pagerank preprocessing");
            PageRankPreprocessJob.main(arguments);
        }
        else if (command.equals("topnodes"))
        {
            System.out.println("top nodes");
            TopNodeDriver.main(arguments);
        }
    }
}
