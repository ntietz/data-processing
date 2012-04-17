package com.treecitysoftware.data;

import java.util.*;

public class WikiPage
{
    private String id;
    private String pageName;
    private List<String> links; // each string is a page id that this page links to
    private String summary;

    public WikiPage(String i, String p)
    {
        id = i;
        pageName = p;
    }

    public String getId()
    {
        return id;
    }

    public String getPageName()
    {
        return pageName;
    }

    public void setLinks(List<String> l)
    {
        links = l;
    }

    public List<String> getLinks()
    {
        return links;
    }

    public void setSummary(String s)
    {
        summary = s;
    }

    public String getSummary()
    {
        return summary;
    }

}

