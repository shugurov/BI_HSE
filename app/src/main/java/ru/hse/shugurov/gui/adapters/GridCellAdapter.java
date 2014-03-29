package ru.hse.shugurov.gui.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentMonth; //using traditional numeration. January - 1
    private int currentYear;
    private Map<DayDescription, String> events;
    private View currentlySelectedView;
    private CalendarAdapter.CalendarAdapterCallback callback;

    public GridCellAdapter(Context context, int month, int year, Map<DayDescription, String> events, CalendarAdapter.CalendarAdapterCallback callback)
    {
        super();
        this.context = context;
        this.listOfDaysOnScreen = new ArrayList<DayDescription>();
        this.events = events;
        this.callback = callback;
        Calendar calendar = Calendar.getInstance();
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
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


    private void printMonth(int givenMonth, int givenYear)
    {
        int trailingSpaces;
        int daysInPrevMonth;
        int prevMonth;
        int prevYear;
        int nextYear;

        int currentMonth = givenMonth - 1;
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        GregorianCalendar cal = new GregorianCalendar(givenYear, currentMonth, 1);

        if (currentMonth == 11)
        {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            prevYear = givenYear;
            nextYear = givenYear + 1;
        } else if (currentMonth == 0)
        {
            prevMonth = 11;
            prevYear = givenYear - 1;
            nextYear = givenYear;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        } else
        {
            prevMonth = currentMonth - 1;
            nextYear = givenYear;
            prevYear = givenYear;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
        {
            if (givenMonth == 2)
            {
                ++daysInMonth;
            } else if (givenMonth == 3)
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
            if (i == currentDayOfMonth && this.currentMonth == givenMonth && currentYear == givenYear)
            {
                listOfDaysOnScreen.add(new DayDescription(i, months[currentMonth], givenYear, R.color.black));
            } else
            {
                listOfDaysOnScreen.add(new DayDescription(i, months[currentMonth], givenYear, R.color.lightgray02));
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

        Button gridCell = (Button) row.findViewById(R.id.calendar_day_gridcell);
        gridCell.setOnClickListener(this);

        DayDescription dayToBeShown = listOfDaysOnScreen.get(position);

        if (!events.isEmpty())
        {
            if (events.containsKey(dayToBeShown))
            {
                gridCell.setBackgroundColor(Color.RED);
            }
        }

        // Set the Day GridCell
        gridCell.setText(Integer.toString(dayToBeShown.getDay()));
        gridCell.setTag(dayToBeShown);
        gridCell.setTextColor(context.getResources().getColor(dayToBeShown.getColor()));
        return row;
    }

    @Override
    public void onClick(View view)
    {
        if (currentlySelectedView != null)
        {
            if (Build.VERSION.SDK_INT >= 16)
            {
                setBackgroundV16Plus(currentlySelectedView, context.getResources().getDrawable(R.drawable.calendar_button_selector));
            }
        }
        DayDescription selectedDay = (DayDescription) view.getTag();
        if (events.containsKey(selectedDay))
        {
            String event = events.get(selectedDay);
            callback.setEventListAdapter(Arrays.asList(new String[]{"sdsds"}));
        } else
        {
            callback.setEventListAdapter(null);
        }
        Drawable newBackground = context.getResources().getDrawable(R.drawable.calendar_bg_orange);
        if (Build.VERSION.SDK_INT >= 16)
        {
            setBackgroundV16Plus(view, newBackground);
        } else
        {
            setBackgroundV16Minus(view, newBackground);
        }
        currentlySelectedView = view;

    }

    @TargetApi(16)
    private void setBackgroundV16Plus(View view, Drawable drawable)
    {
        view.setBackground(drawable);

    }

    @SuppressWarnings("deprecation")
    private void setBackgroundV16Minus(View view, Drawable drawable)
    {
        view.setBackgroundDrawable(drawable);
    }

}

