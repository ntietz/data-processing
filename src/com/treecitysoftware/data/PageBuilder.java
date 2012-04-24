package com.treecitysoftware.data;

public class PageBuilder {
	private int 	page_id;
	private int 	page_namespace;
	private String 	page_title;
	private String 	page_restrictions;
	private int 	page_counter;
	private int 	page_is_redirect;
	private int 	page_is_new;
	private int 	page_random;
	private String 	page_touched;
	private int 	page_latest;
	private int 	page_len;
	
	private String page;
	
	PageBuilder()
	{
		//There is no safe default
	}
	
	PageBuilder(int id)
	{
		page = new String("");
		page_id = id;
		page_namespace = 0;
		page_title = new String(new RandomWikiTitle().getTitle());
		page_restrictions = new String("");
		page_counter = 0;
		page_is_redirect = 0;
		page_is_new = 0;
		page_random = 0;
		page_touched = new String("684643138");
		page_latest = 0;
		page_len = 0;
		
		buildPage();
	}
	
	private void buildPage()
	{
		page += "(";
		page += Integer.toString(page_id);
		page += ",";
		page += Integer.toString(page_namespace);
		page += ",";
		page += "'";
		page += page_title;
		page += "'";
		page += ",";
		page += "'";
		page += page_restrictions;
		page += "'";
		page += ",";
		page += Integer.toString(page_counter);
		page += ",";
		page += Integer.toString(page_is_redirect);
		page += ",";
		page += Integer.toString(page_is_new);
		page += ",";
		page += Integer.toString(page_random);
		page += ",";
		page += "'";
		page += page_touched;
		page += "'";
		page += ",";
		page += Integer.toString(page_latest);
		page += ",";
		page += Integer.toString(page_len);
		page += ")";
	}
	
	public String getPage()
	{
		return page;
	}
	
	public String getId() 
	{
		return Integer.toString(page_id);
	}
	
	public String getTitle()
	{
		return page_title;
	}
	
}
