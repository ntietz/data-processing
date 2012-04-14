package com.treecitysoftware.data;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class NodeTest
{
    @BeforeClass
    public static void setup()
    {

    }

    @Test
    public void testConstructor()
    {
        String id = "a";
        Integer value = 1;

        Node<Integer> node = new Node<Integer>(id, value);

        assertEquals("Id did not match.", id, node.getId());
        assertEquals("Value did not match.", value, node.getValue());
        assertEquals("Neighbors should be empty.", 0, node.getNeighbors().size());
    }

    @Test
    public void testListConstructor()
    {
        String id = "b";
        Double value = 2.0;
        List<String> neighbors = new ArrayList<String>();
        neighbors.add("aa");
        neighbors.add("ab");
        neighbors.add("ac");

        Node<Double> node = new Node<Double>(id, value, neighbors);

        assertEquals("Id did not match.", id, node.getId());
        assertEquals("Value did not match.", value, node.getValue());
        assertEquals("Neighbors should be empty.", 3, node.getNeighbors().size());
        assertEquals("Neighbors should match.", neighbors, node.getNeighbors());
    }
}

