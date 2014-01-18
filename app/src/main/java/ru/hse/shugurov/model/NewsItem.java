package ru.hse.shugurov.model;

/**
 * Created by Иван on 01.01.14.
 */
public class NewsItem
{
    private final String title;
    private final String text;
    private final String date;
    private final String picture;
    private final String summary;
    private final int type;

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
