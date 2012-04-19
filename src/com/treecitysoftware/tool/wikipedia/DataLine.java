package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.data.*;

import java.util.*;

public class DataLine
{
    public static final String pagePrefix = "INSERT INTO `page` VALUES ";
    public static final String edgePrefix = "INSERT INTO `pagelinks` VALUES ";

    private String line;

    private DataLine() { }

    public DataLine(String l)
    {
        line = l;
    }

    public String getLine()
    {
        return line;
    }

    public boolean containsPageData()
    {
        if (line.startsWith(pagePrefix))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean containsEdgeData()
    {
        if (line.startsWith(edgePrefix))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @return  a list of stringpairs where left is the page id, right is the page title
     */
    public List<StringPair> getPages()
    {
        if (!containsPageData())
        {
            return null;
        }

        List<StringPair> pages = new ArrayList<StringPair>();

        String strippedLine = line.substring(pagePrefix.length(), line.length()); // TODO might have to change this index +- 1

        do
        {
            //System.out.println(pages.size() + "\t" + strippedLine.length());

            int firstComma = strippedLine.indexOf(',');
            int secondComma = strippedLine.indexOf(',', firstComma + 1);
            int endingQuote = strippedLine.indexOf('\'', secondComma + 2);
            while (strippedLine.charAt(endingQuote - 1) == '\\')
            {
                endingQuote = strippedLine.indexOf('\'', endingQuote + 1);
            }

            int endIndex = strippedLine.indexOf(')', endingQuote + 1) + 1;

            String thisPage = strippedLine.substring(0, endIndex);
            strippedLine = strippedLine.substring(endIndex+1, strippedLine.length());

            //System.out.println(thisPage);

            String id = thisPage.substring(1, firstComma);
            String namespace = thisPage.substring(firstComma + 1, secondComma);

            if (namespace.equals("0"))
            {
                String title = thisPage.substring(secondComma + 2, endingQuote);

                StringPair page = new StringPair(id, title);
                pages.add(page);
            }

        } while (strippedLine.length() > 1);

        return pages;
    }


    /**
     * @return  a list of stringpairs where left is the originating page id, right is the target page title
     */
    public List<StringPair> getEdges()
    {
        if (!containsEdgeData())
        {
            return null;
        }

        List<StringPair> pages = new ArrayList<StringPair>();

        String strippedLine = line.substring(edgePrefix.length(), line.length()); // TODO might have to change this index +- 1

        do
        {
            //System.out.println(pages.size() + "\t" + strippedLine.length());

            int firstComma = strippedLine.indexOf(',');
            int secondComma = strippedLine.indexOf(',', firstComma + 1);
            int endingQuote = strippedLine.indexOf('\'', secondComma + 2);
            while (strippedLine.charAt(endingQuote - 1) == '\\')
            {
                endingQuote = strippedLine.indexOf('\'', endingQuote + 1);
            }

            int endIndex = endingQuote + 2;

            String thisPage = strippedLine.substring(0, endIndex);
            strippedLine = strippedLine.substring(endIndex+1, strippedLine.length());

            //System.out.println(thisPage);

            String id = thisPage.substring(1, firstComma);
            String namespace = thisPage.substring(firstComma + 1, secondComma);

            if (namespace.equals("0"))
            {
                String title = thisPage.substring(secondComma + 2, endingQuote);

                StringPair page = new StringPair(id, title);
                pages.add(page);
            }

        } while (strippedLine.length() > 1);

        return pages;
    }

}

