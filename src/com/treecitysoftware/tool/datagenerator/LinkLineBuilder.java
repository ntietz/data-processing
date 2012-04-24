package com.treecitysoftware.tool.datagenerator;

import java.util.*;

public class LinkLineBuilder {
	private String header;
	private String line;
	private List<LinkBuilder> links;
	
	private LinkLineBuilder()
	{
		//NOT SAFE
	}
	
	public LinkLineBuilder(List<LinkBuilder> l)
	{
		links = l;
		header = "INSERT INTO `pagelinks` VALUES ";
		line = header;
		buildLine();
	}
	
	private void buildLine() 
	{
		for(int index = 0; index < links.size() - 1; ++index)
		{
			line += links.get(index).getLink() + ",";
		}
        line += links.get(links.size() - 1).getLink() + ";";
	}
	
	public String getLine()
	{
		return line;
	}

}
