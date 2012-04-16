package com.treecitysoftware.algorithm.pagerank;

public class Contribution
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
}

