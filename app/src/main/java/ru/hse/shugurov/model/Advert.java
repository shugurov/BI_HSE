package ru.hse.shugurov.model;

/**
 * Created by Иван on 18.01.14.
 */
public class Advert
{
    private final String title;
    private final String text;
    private final int course;

    public Advert(String title, String text, int course)
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
