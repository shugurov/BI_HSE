package ru.hse.shugurov.model;

/**
 * Created by Иван on 01.01.14.
 */
public class NewsItem //TODO где и как я использую сеттеры?(
{
    private String title;
    private String text;
    private String date;
    private String picture;
    private String summary;
    private int type;

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public void setType(int type)
    {
        this.type = type;
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


    public NewsItem(String title, String text, String date, String picture, String summary, int type)
    {
        this.title = title;
        this.text = text;
        this.date = date;
        this.picture = picture;
        this.summary = summary;
        this.type = type;
    }
}
