package com.treecitysoftware.data;

import java.util.*;

public class LinkLineBuilder {
	private String header;
	private String line;
	private List<LinkBuilder> links;
	
	LinkLineBuilder()
	{
		//NOT SAFE
	}
	
	LinkLineBuilder(List<LinkBuilder> l)
	{
		links = l;
		header = new String("INSERT INTO `pagelinks` VALUES ");
		line = new String(header);
		buildLine();
	}
	
	private void buildLine() 
	{
		for(int i = 0; i < links.size(); ++i)
		{
			line += links.get(i).getLink();
			if(i < links.size() - 1)
				line += ",";
		}
		line += ";";
	}
	
	public String getLine()
	{
		return line;
	}

}
