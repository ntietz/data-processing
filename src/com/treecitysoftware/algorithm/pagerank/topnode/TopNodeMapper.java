package com.treecitysoftware.algorithm.pagerank.topnode;

public class TopNodeMapper
extends MapReduceBase
implements Mapper<IntWritable, PageRankNode, DoubleWritable, IntWritable>
{
    public void map( IntWritable key
                   , PageRankNode value
                   , OutputCollector<DoubleWritable, IntWritable> output
                   , Reporter reporter
                   )
    throws IOException
    {
        double score = 1 - value.getValue();
        output.collect(new DoubleWritable(score), key);
    }
}

