package com.treecitysoftware.data;

import org.apache.hadoop.io.*;

import java.io.*;
import java.util.*;

public class FromToPair
implements WritableComparable
{
    /**
     * First value in pair
     */
    public int left;

    /**
     * Second value in pair
     */
    public int right;

    /**
     * Creates an empty pair of nodes
     */
    public FromToPair()
    {
        //Undefined and non safe implementation
    }

    /**
     * Creates a pair of node ID's
     * @param a the first item in the list
     * @param b the second item in the list
     */
    public FromToPair(int a, int b)
    {
        left = a;
        right = b;
    }

    /**
     * Serializes the data out to disk
     * @param out The data output stream
     */
    public void write(DataOutput out)
    throws IOException
    {
        out.writeInt(left);
        out.writeInt(right);
    }

    /**
     * Serializes the data in from the disk
     * @param in The input data stream
     */
    public void readFields(DataInput in)
    throws IOException
    {
        left = in.readInt();
        right = in.readInt();
    }

    public int compareTo(Object obj)
    {
        if (obj instanceof FromToPair)
        {
            FromToPair other = (FromToPair) obj;

            if (left != other.left)
            {
                if (left < other.left)
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
            else if (right != other.right)
            {
                if (right < other.right)
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return -1;
        }
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof FromToPair)
        {
            FromToPair other = (FromToPair) obj;

            return ((left == other.left) && (right == other.right));
        }
        else
        {
            return false;
        }
    }
}

