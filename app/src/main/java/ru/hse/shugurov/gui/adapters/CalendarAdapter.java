package ru.hse.shugurov.gui.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import ru.hse.shugurov.R;
import ru.hse.shugurov.model.DayDescription;

/**
 * Created by Иван on 09.03.14.
 */
public class CalendarAdapter extends BaseExpandableListAdapter
{
    private String[] groupNames = {"Календарь", "Мероприятия"};
    private LinearLayout calendarWrapper;
    private ListView listView;
    private LayoutInflater inflater;
    private Context context;
    private int year;
    private int month;

    public CalendarAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount()
    {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.group_name, parent, false);
        }
        ((TextView) convertView).setText(groupNames[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        if (groupPosition == 0)
        {
            final Calendar calendar = Calendar.getInstance(Locale.getDefault());
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);
            convertView = inflater.inflate(R.layout.calendar_layout, parent, false);
            final GridView calendarView = (GridView) convertView.findViewById(R.id.calendar);
            GridCellAdapter adapter = new GridCellAdapter(context, month, year, new HashMap<DayDescription, String>());
            adapter.notifyDataSetChanged();
            calendarView.setAdapter(adapter);
            final TextView currentMonth = (TextView) convertView.findViewById(R.id.current_month);
            currentMonth.setText(DateFormat.format("MMMM yyyy", calendar.getTime()));
            View previousMonth = convertView.findViewById(R.id.previous_month);
            View nextMonth = convertView.findViewById(R.id.next_month);
            View.OnClickListener listener = new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    switch (v.getId())
                    {
                        case R.id.previous_month:
                            if (month <= 1)
                            {
                                month = 12;
                                year--;
                            } else
                            {
                                month--;
                            }
                            break;
                        case R.id.next_month:
                            if (month > 11)
                            {
                                month = 1;
                                year++;
                            } else
                            {
                                month++;
                            }
                            break;
                        default:
                            return;
                    }
                    GridCellAdapter adapter = new GridCellAdapter(context, month, year, new HashMap<DayDescription, String>());
                    calendar.set(year, month - 1, calendar.get(Calendar.DAY_OF_MONTH));
                    currentMonth.setText(DateFormat.format("MMMM yyyy", calendar.getTime()));
                    adapter.notifyDataSetChanged();
                    calendarView.setAdapter(adapter);
                }
            };
            previousMonth.setOnClickListener(listener);
            nextMonth.setOnClickListener(listener);
        } else
        {
            convertView = inflater.inflate(R.layout.group_name, parent, false);
            ((TextView) convertView).setText("child view");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
