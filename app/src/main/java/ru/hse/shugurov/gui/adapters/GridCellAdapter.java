package ru.hse.shugurov.gui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import ru.hse.shugurov.R;
import ru.hse.shugurov.model.DayDescription;

/**
 * Created by Иван on 09.03.14.
 */
public class GridCellAdapter extends BaseAdapter implements View.OnClickListener
{
    private static final int DAY_OFFSET = 1;
    private final Context context;
    private final List<DayDescription> listOfDaysOnScreen;
    private final String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private int currentMonth; //using traditional numeration. January - 1
    private int currentYear;
    private Button gridcell;
    private Map<DayDescription, String> events;

    public GridCellAdapter(Context context, int month, int year, Map<DayDescription, String> events)
    {
        super();
        this.context = context;
        this.listOfDaysOnScreen = new ArrayList<DayDescription>();
        this.events = events;
        Calendar calendar = Calendar.getInstance();
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        currentWeekDay =  calendar.get(Calendar.DAY_OF_WEEK);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentYear = calendar.get(Calendar.YEAR);
        printMonth(month, year);

    }


    private int getNumberOfDaysOfMonth(int i)
    {
        return daysOfMonth[i];
    }

    public DayDescription getItem(int position)
    {
        return listOfDaysOnScreen.get(position);
    }

    @Override
    public int getCount()
    {
        return listOfDaysOnScreen.size();
    }


    private void printMonth(int mm, int yy)
    {
        int trailingSpaces;
        int daysInPrevMonth;
        int prevMonth;
        int prevYear;
        int nextYear;

        int currentMonth = mm - 1;
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

        if (currentMonth == 11)
        {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            prevYear = yy;
            nextYear = yy + 1;
        } else if (currentMonth == 0)
        {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        } else
        {
            prevMonth = currentMonth - 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
        {
            if (mm == 2)
            {
                ++daysInMonth;
            } else if (mm == 3)
            {
                ++daysInPrevMonth;
            }
        }

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++)
        {
            listOfDaysOnScreen.add(new DayDescription(daysInPrevMonth - trailingSpaces + DAY_OFFSET, months[prevMonth], prevYear, R.color.lightgray));
        }

        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++)
        {
            if (i == currentDayOfMonth && this.currentMonth == mm && currentYear == yy)
            {
                listOfDaysOnScreen.add(new DayDescription(i, months[currentMonth], yy, R.color.orrange));
            } else
            {
                listOfDaysOnScreen.add(new DayDescription(i, months[currentMonth], yy, R.color.lightgray02));
            }
        }

        // Leading Month days
        for (int i = 0; i < listOfDaysOnScreen.size() % 7; i++)
        {
            listOfDaysOnScreen.add(new DayDescription(i, months[currentMonth], nextYear, R.color.lightgray));
        }
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.screen_gridcell, parent, false);
        }

        gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
        gridcell.setOnClickListener(this);

        DayDescription dayToBeShown = listOfDaysOnScreen.get(position);

        if (!events.isEmpty())
        {
            if (events.containsKey(currentWeekDay))
            {
                events.remove(Integer.toString(dayToBeShown.getDay()));
                gridcell.setBackgroundColor(Color.RED);
            }
        }

        // Set the Day GridCell
        gridcell.setText(Integer.toString(dayToBeShown.getDay()));
        gridcell.setTag(Integer.toString(dayToBeShown.getDay()) + "-" + dayToBeShown.getMonth() + "-" + Integer.toString(dayToBeShown.getYear()));
        gridcell.setTextColor(context.getResources().getColor(dayToBeShown.getColor()));
        return row;
    }

    @Override
    public void onClick(View view)
    {
        String date_month_year = (String) view.getTag();
        try
        {
            Date parsedDate = dateFormatter.parse(date_month_year);

        } catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

}


