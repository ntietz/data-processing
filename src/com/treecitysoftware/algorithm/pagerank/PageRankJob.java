package com.treecitysoftware.algorithm.pagerank;

public class PageRankJob
{
    public static void main(String... args)
    {
        // TODO fix the input paths
        if (args.length < 4)
        {
            System.out.println("Error: expected");
            System.out.println("    pagerank inputpath outputbasepath numberofreducers numberofnodes [dampingfactor=0.2]");
            System.exit(1);
        }

        String inputPath = args[0];
        String outputPath = args[1];
        int numberOfReducers = Integer.valueOf(args[2]);
        int numberOfNodes = Integer.valueOf(args[3]);
        double dampingFactor = 0.2;
        if (args.length >= 5)
        {
            Double.valueOf(args[4]);
        }

        PageRankDriver driver = new PageRankDriver();
        driver.run( inputPath
                  , outputPath
                  , numberOfReducers
                  , numberOfNodes
                  , dampingFactor
                  );

    }
}

