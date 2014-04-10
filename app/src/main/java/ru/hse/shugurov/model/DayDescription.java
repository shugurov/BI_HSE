package ru.hse.shugurov.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Иван on 17.03.14.
 */
public class DayDescription
{
    private Calendar calendar;
    private int color;

    public DayDescription(int day, int month, int year, int color)
    {
        this.color = color;
        calendar = new GregorianCalendar(year, month, day);
    }

    public int getYear()
    {
        return calendar.get(Calendar.YEAR);
    }

    public int getDay()
    {
        return calendar.get(Calendar.DATE);
    }

    public int getMonth()
    {
        return calendar.get(Calendar.MONTH);
    }

    public int getColor()
    {
        return color;
    }

    public Calendar getCalendar()
    {
        return calendar;
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
        if (calendar != null ? !calendar.equals(that.calendar) : that.calendar != null)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = calendar != null ? calendar.hashCode() : 0;
        result = 31 * result + color;
        return result;
    }
}
