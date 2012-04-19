package com.treecitysoftware.algorithm.pagerank;

public class PageRankJob
{
    public static void main(String... args)
    {
        if (args.length != 2)
        {
            System.out.println("Error: expected");
            System.out.println("    pagerank inputpath outputbasepath");
            System.exit(1);
        }

        String inputPath = args[0];
        String outputPath = args[1];

    }
}

