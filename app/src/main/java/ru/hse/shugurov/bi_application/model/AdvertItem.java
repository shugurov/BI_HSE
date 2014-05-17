package ru.hse.shugurov.bi_application.model;

import java.io.Serializable;

/**
 * Created by Иван on 18.01.14.
 */
public class AdvertItem implements Serializable
{
    private final String title;
    private final String text;
    private final int course;

    public AdvertItem(String title, String text, int course)
    {
        this.title = title;
        this.text = text;
        this.course = course;
    }

    public int getCourse()
    {
        return course;
    }

    public String getTitle()
    {
        return title;
    }

    public String getText()
    {
        return text;
    }
}
