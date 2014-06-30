package ru.hse.shugurov.bi_application.gui.adapters;

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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.model.DayDescription;
import ru.hse.shugurov.bi_application.model.NewsItem;

/**
 * Created by Иван on 09.03.14.
 */
public class GridCellAdapter extends BaseAdapter implements View.OnClickListener
{
    private final Context context;
    private final List<DayDescription> listOfDaysOnScreen;
    private int currentDayOfMonth;
    private int currentMonth; //using traditional numeration. January - 1
    private int currentYear;
    private Map<Calendar, NewsItem[]> events;
    private View currentlySelectedView;
    private GregorianCalendar calendarToBeDisplayed;
    private int givenMonth;
    private int givenYear;

    public GridCellAdapter(Context context, int month, int year, Map<Calendar, NewsItem[]> events)
    {
        super();
        this.context = context;
        this.listOfDaysOnScreen = new ArrayList<DayDescription>();
        this.events = events;
        givenMonth = month;
        givenYear = year;
        Calendar calendar = Calendar.getInstance();
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        printMonth();

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


    private void printMonth()
    {
        int trailingSpaces;

        Calendar previousMonth = new GregorianCalendar(givenYear, givenMonth, 1);
        previousMonth.add(Calendar.MONTH, -1);
        Calendar nextMonth = new GregorianCalendar(givenYear, givenMonth, 1);
        nextMonth.add(Calendar.MONTH, 1);

        calendarToBeDisplayed = new GregorianCalendar(givenYear, givenMonth, 1);
        int currentWeekDay = (calendarToBeDisplayed.get(Calendar.DAY_OF_WEEK) + 5) % 7;
        trailingSpaces = currentWeekDay;

        // Trailing Month days
        int daysInPreviousMonth = previousMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < trailingSpaces; i++)
        {
            listOfDaysOnScreen.add(new DayDescription(daysInPreviousMonth - trailingSpaces + i + 1, previousMonth.get(Calendar.MONTH), R.color.lightgray));
        }

        // Current Month Days
        int numberOfDays = calendarToBeDisplayed.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= numberOfDays; i++)
        {
            if (i == currentDayOfMonth && this.currentMonth == givenMonth && currentYear == givenYear)
            {
                listOfDaysOnScreen.add(new DayDescription(i, givenMonth, R.color.black));
            } else
            {
                listOfDaysOnScreen.add(new DayDescription(i, givenMonth, R.color.lightgray02));
            }
        }

        // Leading Month days
        for (int i = 1; i <= listOfDaysOnScreen.size() % 7; i++)
        {
            listOfDaysOnScreen.add(new DayDescription(i, (givenMonth + 1) % 12, R.color.lightgray));
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
        Calendar calendarRepresentationOfDay = new GregorianCalendar(calendarToBeDisplayed.get(Calendar.YEAR), dayToBeShown.getMonth(), dayToBeShown.getDay());
        if (events.containsKey(calendarRepresentationOfDay))
        {
            gridCell.setBackgroundColor(Color.RED);//TODO если щёлкнуть на мероприятие, то цвет пропадает
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
            /*String event = events.get(selectedDay); TODO тут говорю о нажатие
            callback.setEventListAdapter(Arrays.asList(new String[]{"sdsds"}));*/
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



