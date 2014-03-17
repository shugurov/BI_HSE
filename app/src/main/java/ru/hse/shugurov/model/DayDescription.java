package ru.hse.shugurov.model;

/**
 * Created by Иван on 17.03.14.
 */
public class DayDescription
{
    private final int day;
    private final String month;
    private final int color;
    private final int year;

    public DayDescription(int day, String month, int year, int color)
    {
        this.day = day;
        this.month = month;
        this.color = color;
        this.year = year;
    }

    public int getYear()
    {
        return year;
    }

    public int getDay()
    {
        return day;
    }

    public String getMonth()
    {
        return month;
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
        if (o == null || getClass() != o.getClass())
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
        if (year != that.year)
        {
            return false;
        }
        if (month != null ? !month.equals(that.month) : that.month != null)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = day;
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + color;
        result = 31 * result + year;
        return result;
    }
}
