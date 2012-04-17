package com.treecitysoftware.algorithm.pagerank;

import org.apache.hadoop.io.*;

import java.io.*;

public class Contribution
implements Writable
{
    /**
     * the amount of the contribution
     */
    private double amount;

    /**
     * Acceptable default behavior is setting no contribution.
     */
    public Contribution()
    {
        amount = 0.0;
    }

    /**
     * @param a the amount of the contribution
     */
    public Contribution(double a)
    {
        amount = a;
    }

    /**
     * @return  the amount of the contribution
     */
    public double getAmount()
    {
        return amount;
    }

    /**
     * @param a the amount of the contribution
     */
    public void setAmount(double a)
    {
        amount = a;
    }
    
    /**
     * Serializes the object to a DataOutput object.
     * @param out   the object we serialize using
     */
    public void write(DataOutput out)
    throws IOException
    {
        out.writeDouble(amount);
    }
    
    /**
     * Deserializes the object from a DataInput object.
     * @param in    the object we deserialize from
     */
    public void readFields(DataInput in)
    throws IOException
    {
        amount = in.readDouble();
    }
}

