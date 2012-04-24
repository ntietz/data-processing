package com.treecitysoftware.tool.datagenerator;

import java.util.*;
import java.io.*;

public class TestDataGenerator {

	public static void main(String... args) {
		// take care of args
		/*
		if(args.length != 3)
		{
			System.out.println("Error: expected     testdatagenerator numberofnodes numberofdanglers ouptputpath");
			System.exit(1);
		}
		*/
		
		int numberOfNodes = Integer.valueOf(args[0]);
		int numberOfDanglers = Integer.valueOf(args[1]);
		int totalPages = numberOfNodes + numberOfDanglers;
		int pagesPerLine = 2;
		int maxNumberOfLinks = 10;
		String outputPath = args[2];
		String outputPages = outputPath + "wikipagedata";
		String outputLinks = outputPath + "wikilinkdata";
		
		try
		{
			FileWriter fstream = new FileWriter(outputPages);
			BufferedWriter out = new BufferedWriter(fstream);
			
			
			int numberOfLines = 0;
			if(((totalPages) % pagesPerLine) == 0)
			{
				numberOfLines = (totalPages)/pagesPerLine;
			}
			else
			{
				numberOfLines = ((totalPages)/pagesPerLine) + 1;
			}
						
			// Build up the pages and write the lines
			List<PageBuilder> allPages = new ArrayList<PageBuilder>();
			for(int index = 0; index <= totalPages; ++index)
			{
				PageBuilder p = new PageBuilder(index);
				allPages.add(p);
				if(((index % pagesPerLine) == 0) && index != 0)
				{
					PageLineBuilder line = new PageLineBuilder(allPages.subList(index-pagesPerLine, index));
					out.write(line.getLine());
					out.write("\n");
				}
				if(index == totalPages)
				{
					PageLineBuilder line = new PageLineBuilder(allPages.subList(index-(index%pagesPerLine), index));
					out.write(line.getLine());
					out.write("\n");
				}
			}			
			out.close();
			fstream = new FileWriter(outputLinks);
			out = new BufferedWriter(fstream);
			
			for(int index = 0; index < numberOfNodes; ++index)
			{
				Random generator = new Random();
				int numberOfLinks = generator.nextInt(maxNumberOfLinks) + 1;
				List<LinkBuilder> links = new ArrayList<LinkBuilder>();
				for(int j = 0; j < numberOfLinks; ++j)
				{
					links.add(new LinkBuilder(allPages.get(generator.nextInt(totalPages)), allPages.get(generator.nextInt(totalPages))));
				}
				LinkLineBuilder line = new LinkLineBuilder(links);
				out.write(line.getLine());
				out.write("\n");
			}
			
			
			out.close();
		}
		catch (Exception e)
		{
			
		}
	}

}
