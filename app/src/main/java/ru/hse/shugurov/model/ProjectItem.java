package ru.hse.shugurov.model;


/**
 * Created by Иван on 06.01.14.
 */
public class ProjectItem
{
    private String text;
    private String url;
    private String headline;
    private String pictureUrl;

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
