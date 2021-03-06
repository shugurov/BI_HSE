package ru.hse.shugurov.bi_application.model;

import java.io.Serializable;

/**
 * Created by Иван on 17.03.14.
 */
public class DayDescription implements Serializable
{
    private final int day;
    private final int month;
    private final int year;
    private final int color;

    public DayDescription(int day, int month, int year, int color)
    {
        this.day = day;
        this.month = month;
        this.year = year;
        this.color = color;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

    public int getColor()
    {
        return color;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || !(o instanceof DayDescription))
        {
            return false;
        }

        DayDescription that = (DayDescription) o;

        if (color != that.color)
        {
            return false;
        }
        if (day != that.day)
        {
            return false;
        }
        if (month != that.month)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = day + month;
        result = 31 * result + color;
        return result;
    }

    public int getYear()
    {
        return year;
    }
}
