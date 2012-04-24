package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.data.*;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class DataLineTest
{
    @BeforeClass
    public static void setup()
    {

    }

    @Test
    public void testEdgeParsing()
    {
        String line = "INSERT INTO `pagelinks` VALUES (0,0,'Housing'),(3,10,'Pending_deletion'),(10,0,'Computer_accessibility'),(10,4,'Subpages');";
        DataLine dataLine = new DataLine(line);

        assertTrue("Should contain edge data", dataLine.containsEdgeData());

        List<StringPair> pairs = dataLine.getEdges();

        assertEquals("Should be right length", 2, pairs.size());

        StringPair first = pairs.get(0);
        StringPair second = pairs.get(1);

        assertEquals("Values should match.", "0", first.left);
        assertEquals("Values should match.", "Housing", first.right);
        assertEquals("Values should match.", "10", second.left);
        assertEquals("Values should match.", "Computer_accessibility", second.right);

    }

    @Test
    public void testPageParsing()
    {
        String line = "INSERT INTO `page` VALUES (10,0,'AccessibleComputing','',0,1,0,0.33167112649574,'20120322044848',381202555,57),(12,1,'Anarchism','',5252,0,0,0.786172332974311,'20120402132302',485053242,144382),(13,0,'AfghanistanHistory','',5,1,0,0.0621502865684687,'20120402231231',74466652,57),(14,0,'AfghanistanGeography','',0,1,0, 0.952234464653055,'20120401025458',407008307,59);";
        DataLine dataLine = new DataLine(line);

        assertTrue("Should contain page data", dataLine.containsPageData());
 
        List<StringPair> pairs = dataLine.getPages();

        assertEquals("Should be right length", 3, pairs.size());

        StringPair first = pairs.get(0);
        StringPair second = pairs.get(1);
        StringPair third = pairs.get(2);

        assertEquals("Values should match.", "10", first.left);
        assertEquals("Values should match.", "AccessibleComputing", first.right);
        assertEquals("Values should match.", "13", second.left);
        assertEquals("Values should match.", "AfghanistanHistory", second.right);
        assertEquals("Values should match.", "14", third.left);
        assertEquals("Values should match.", "AfghanistanGeography", third.right);

    }
}

