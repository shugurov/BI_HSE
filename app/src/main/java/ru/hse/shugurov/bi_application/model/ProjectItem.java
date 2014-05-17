package ru.hse.shugurov.bi_application.model;


import java.io.Serializable;

/**
 * Created by Иван on 06.01.14.
 */
public class ProjectItem implements Serializable
{
    private final String text;
    private final String url;
    private final String headline;
    private final String pictureUrl;

    public ProjectItem(String text, String url, String headline, String pictureUrl)
    {
        this.url = url;
        this.headline = headline;
        this.text = text;
        this.pictureUrl = pictureUrl;
    }

    public String getHeadline()
    {
        return headline;
    }

    public String getUrl()
    {
        return url;
    }

    public String getPictureUrl()
    {
        return pictureUrl;
    }

    public String getText()
    {
        return text;
    }
}
