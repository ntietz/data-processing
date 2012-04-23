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

    public PageOrEdge()
    {
        page = null;
        id = null;
    }

    public PageOrEdge(WikiPage p)
    {
        page = p;
        id = null;
    }

    public PageOrEdge(Integer i)
    {
        page = null;
        id = i;
    }

    public boolean isPage()
    {
        return page != null;
    }

    public boolean isEdge()
    {
        return id != null;
    }

    public void write(DataOutput out)
    throws IOException
    {
        if (page != null)
        {
            out.writeUTF("p");
            page.write(out);
        }
        else
        {
            out.writeUTF("e");
            out.writeInt(id);
        }
    }

    public void readFields(DataInput in)
    throws IOException
    {
        String type = in.readUTF();
        if (type.equals("p"))
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

