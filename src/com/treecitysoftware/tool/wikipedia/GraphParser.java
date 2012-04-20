package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.data.*;

import java.io.*;
import java.util.*;

public class GraphParser
{
    public static void main(String... args)
    throws FileNotFoundException // TODO handle this in the arg handling
         , IOException
    {
        // args[0] : page path
        // args[1] : edge path
        // args[2] : output graph path
        // args[3] : output log path
        String inputPagePath = args[0];
        String inputEdgePath = args[1];
        String outputGraphPath = args[2];
        String outputLogPath = args[3];

        BufferedReader pageIn = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(inputPagePath))));
        BufferedReader edgeIn = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(inputEdgePath))));
        PrintStream graphOut = new PrintStream(new FileOutputStream(outputGraphPath));
        PrintStream logOut = new PrintStream(new FileOutputStream(outputLogPath));

        // read in all the pages, store pageTitle -> id in a map
        Map<String, String> pageToIdMap = new HashMap<String, String>();

        String each = pageIn.readLine();
        while (each != null)
        {
            DataLine line = new DataLine(each);

            if (line.containsPageData())
            {
                List<StringPair> pages = line.getPages();

                for (StringPair page : pages)
                {
                    pageToIdMap.put(page.right, page.left);
                    graphOut.println(page.right + "\t" + page.left);
                }
            }
            else
            {
                // output to the log TODO
            }

            each = pageIn.readLine();
        }

        each = edgeIn.readLine();
        while (each != null)
        {
            DataLine line = new DataLine(each);

            if (line.containsEdgeData())
            {
                List<StringPair> edges = line.getEdges();

                for (StringPair edge : edges)
                {
                    // find the target page (right of the pair) id

                    // if the id exists, output the page
                    // otherwise log it!

                    // TODO BAD NICHOLAS BAD !!!!
                    logOut.println(edge.left + "\t" + edge.right);
                    // TODO CHANGE THIS TODO ONLY TEMPORARY
                }
            }
            else
            {
                // output to the log TODO
            }

            each = edgeIn.readLine();
        }

    }

    
}

