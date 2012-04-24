package com.treecitysoftware.common;

import com.treecitysoftware.algorithm.pagerank.*;
import com.treecitysoftware.tool.wikipedia.*;
import com.treecitysoftware.tool.datagenerator.*;

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
            System.out.println("");
            System.exit(1);
        }

        int numberOfArguments = args.length - 2;
        String[] arguments = new String[numberOfArguments];
        System.arraycopy(args, 2, arguments, 0, numberOfArguments);

        String command = args[1];

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
    }
}