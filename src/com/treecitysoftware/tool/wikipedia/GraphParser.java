package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.data.*;

import java.util.*;

public class GraphParser
{
    public static void main(String... args)
    {
        // args[0] : page path
        // args[1] : edge path
        // args[2] : output graph path
        // args[3] : output log path

        // open page path
        List<String> lines = null;

        // read in all the pages, store pageTitle -> id in a map
        Map<String, String> pageToIdMap = new HashMap<String, String>();

        for (String each : lines)
        {
            DataLine line = new DataLine(each);

            if (line.containsPageData())
            {
                List<StringPair> pages = line.getPages();

                for (StringPair page : pages)
                {
                    pageToIdMap.put(page.right, page.left);
                }
            }
            else
            {
                // output to the log TODO
            }
        }

        // read in all the edges, output to file as we go: [edge (id -> id)]
        lines = null;

        for (String each : lines)
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
                }
            }
            else
            {
                // output to the log TODO
            }
        }

    }

    
}

