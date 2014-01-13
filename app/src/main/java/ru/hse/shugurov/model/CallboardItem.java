package ru.hse.shugurov.model;

/**
 * Created by Иван on 13.01.14.
 */
public class CallboardItem
{
    public static final int UNDERGRADUATE_1 = 1;
    public static final int UNDERGRADUATE_2 = 2;
    public static final int UNDERGRADUATE_3 = 3;
    public static final int UNDERGRADUATE_4 = 4;
    public static final int GRADUATE_1 = 5;
    public static final int GRADUATE_2 = 6;
    private final String date;
    private final String text;
    private final int course;

    public CallboardItem(String date, String text, int course)
    {
        this.date = date;
        this.text = text;
        this.course = course;
    }

    public int getCourse()
    {
        return course;
    }

    public String getDate()
    {
        return date;
    }

    public String getText()
    {
        return text;
    }

}
