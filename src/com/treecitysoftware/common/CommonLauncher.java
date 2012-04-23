package com.treecitysoftware.common;

import com.treecitysoftware.algorithm.pagerank.*;
import com.treecitysoftware.tool.wikipedia.*;

import java.io.*;

public class CommonLauncher
{
    public static void main(String... args)
    throws IOException
    {
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
    }
}
