package com.treecitysoftware.data;

public class LinkBuilder {
	private String pl_from;
	private String pl_namespace;
	private String pl_title;
	
	private String link;
	
	LinkBuilder()
	{
		// NOT SAFE
	}
	
	LinkBuilder(PageBuilder from, PageBuilder to)
	{
		link = new String("");
		pl_from = new String(from.getId());
		pl_namespace = "0";
		pl_title = new String(to.getTitle());
		
		buildLink();
	}
	
	private void buildLink()
	{
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
