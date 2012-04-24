package com.treecitysoftware.tool.datagenerator;

public class LinkBuilder {
	private String pl_from;
	private String pl_namespace;
	private String pl_title;
	
	private String link;
	
	private LinkBuilder()
	{
		// NOT SAFE
	}
	
	public LinkBuilder(PageBuilder from, PageBuilder to)
	{
		link = "";
		pl_from = from.getId();
		pl_namespace = "0";
		pl_title = to.getTitle();
		
		buildLink();
	}
	
	private void buildLink()
	{
        link = "";
		link += "(";
		link += pl_from;
		link += ",";
		link += pl_namespace;
		link += ",";
		link += "'";
		link += pl_title;
		link += "'";
		link += ")";
	}
	
	public String getLink()
	{
		return link;
	}

}
