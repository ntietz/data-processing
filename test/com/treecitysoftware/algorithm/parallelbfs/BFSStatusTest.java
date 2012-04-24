package com.treecitysoftware.algorithm.parallelbfs;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class BFSStatusTest
{
    @BeforeClass
    public static void setup()
    {

    }

    @Test
    public void testDefaultConstructor()
    {
        BFSStatus bfss = new BFSStatus();

        int d = 0;
        List<Integer> p = new ArrayList<Integer>();

        assertEquals("Distance does not match.", d, bfss.getDistance());
        assertEquals("Paths do not match.", p, bfss.getPath());
    }

    @Test
    public void testSpecialConstructor()
    {
        int d = 20;
        List<Integer> p = new ArrayList<Integer>(4);
        p.add(new Integer(1));
        p.add(new Integer(2));
        p.add(new Integer(3));
        p.add(new Integer(4));

        BFSStatus bfss = new BFSStatus(d,p);

        assertEquals("Distance does not match.", d, bfss.getDistance());
        assertEquals("Paths do not match.", p, bfss.getPath());
    }

    @Test
    public void testSetters()
    {
        BFSStatus bfss = new BFSStatus();

        int d = 12;
        List<Integer> p = new ArrayList<Integer>();
        p.add(new Integer(1));
        p.add(new Integer(2));

        bfss.setDistance(d);
        bfss.setPath(p);

        assertEquals("Distance does not match.", d, bfss.getDistance());
        assertEquals("Paths do not match.", p, bfss.getPath());
    }

    @Test
    public void testPathReferencing()
    {
        BFSStatus bfss = new BFSStatus();

        List<Integer> p = new ArrayList<Integer>();
        p.add(new Integer(1));
        p.add(new Integer(5));

        (bfss.getPath()).add(new Integer(1));
        (bfss.getPath()).add(new Integer(5));

        assertEquals("Did not match paths together.", p, bfss.getPath());
    }
}
