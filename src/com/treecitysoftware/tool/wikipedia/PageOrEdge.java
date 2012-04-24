package com.treecitysoftware.tool.wikipedia;

import com.treecitysoftware.data.*;

import org.apache.hadoop.io.*;

import java.io.*;
import java.util.*;

public class PageOrEdge
implements Writable
{
    public WikiPage page;
    public Integer id;
    boolean isPage = false;

    public PageOrEdge()
    {
        page = null;
        id = null;
    }

    public PageOrEdge(WikiPage p)
    {
        page = p;
        id = null;
        isPage = true;
    }

    public PageOrEdge(Integer i)
    {
        page = null;
        id = i;
        isPage = false;
    }

    public boolean isPage()
    {
        return isPage;
    }

    public boolean isEdge()
    {
        return !isPage;
    }

    public void write(DataOutput out)
    throws IOException
    {
        out.writeBoolean(isPage);
        if (isPage)
        {
            page.write(out);
        }
        else
        {
            out.writeInt(id);
        }
    }

    public void readFields(DataInput in)
    throws IOException
    {
        isPage = in.readBoolean();
        if (isPage)
        {
            page = new WikiPage();
            page.readFields(in);
        }
        else
        {
            id = in.readInt();
        }
    }
}

