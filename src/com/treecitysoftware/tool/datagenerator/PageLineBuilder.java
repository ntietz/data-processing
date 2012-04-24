package com.treecitysoftware.tool.datagenerator;

import java.util.*;

public class PageLineBuilder {
	
	private String header;
	private String line;
	private List<PageBuilder> allPages;
	
	private PageLineBuilder()
	{
		//disabled: not safe		
	}
	
	public PageLineBuilder(int numberOfPages)
	{
		/*
		header = "INSERT INTO `page` VALUES ";
		pages = new PageBuilder[numberOfPages];
		line = "";
		line += header;
		buildLine();
		*/
	}
	
	PageLineBuilder(List<PageBuilder> p)
	{
		allPages = p;
		header = "INSERT INTO `page` VALUES ";
		line = header;
		buildLine();
	}
	
	private void buildLine()
	{
		for (int i = 0; i < allPages.size(); ++i)
		{
			line += allPages.get(i).getPage();
			if(i < allPages.size() - 1)
				line += ",";
		}
		line += ";";
	}
	
	public String getLine()
	{
		return line;
	}
	
}
