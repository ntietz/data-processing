package com.treecitysoftware.data;

public class LineBuilder {
	
	private String header;
	private String line;
	private PageBuilder pages[];
	
	LineBuilder()
	{
		//disabled: not safe		
	}
	
	LineBuilder(int numberOfPages)
	{
		header = "INSERT INTO `page` VALUES ";
		pages = new PageBuilder[numberOfPages];
		line = "";
		line += header;
		buildLine();
	}
	
	private void buildLine()
	{
		for (int i = 0; i < pages.length; ++i)
		{
			pages[i] = new PageBuilder(i);
			line += pages[i].getPage();
			if(i < pages.length - 1)
				line += ",";
		}
		line += ";";
	}
	
	public String getLine()
	{
		return line;
	}
	
	public PageBuilder getPage(int index)
	{
		return pages[index];
	}
	
}
