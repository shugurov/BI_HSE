package ru.hse.shugurov.bi_application.model;

import java.io.Serializable;

/**
 * Created by Иван on 01.01.14.
 */
public class NewsItem implements Serializable
{
    private String title;
    private String text;
    private String date;
    private String picture;
    private String summary;
    private int type;

    public NewsItem(String title, String text, String date, String picture, String summary, int type)
    {
        this.title = title;
        this.text = text;
        this.date = date;
        this.picture = picture;
        this.summary = summary;
        this.type = type;
    }

    public String getSummary()
    {
        return summary;
    }


    public String getPicture()
    {
        return picture;
    }

    public String getTitle()
    {
        return title;
    }

    public String getText()
    {
        return text;
    }

    public String getDate()
    {
        return date;
    }

    public int getType()
    {
        return type;
    }
}
