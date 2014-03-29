package ru.hse.shugurov.gui.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ru.hse.shugurov.R;
import ru.hse.shugurov.model.DayDescription;
import ru.hse.shugurov.model.NewsItem;

/**
 * Created by Иван on 09.03.14.
 */
public class CalendarAdapter extends BaseExpandableListAdapter
{
    private String[] groupNames = {"Календарь", "Мероприятия"};
    private LayoutInflater inflater;
    private Context context;
    private int year;
    private int month;
    private NewsAdapter eventsAdapter;
    private ListView eventsView;

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
        int childrenCount;
        if (groupPosition == 0)
        {
            childrenCount = 1;
        } else
        {
            if (eventsAdapter != null)
            {
                childrenCount = eventsAdapter.getCount();
            } else
            {
                childrenCount = 0;
            }
        }
        return childrenCount;
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
            DayDescription exampleEvent = new DayDescription(1, "Март", 2014, R.color.lightgray02);
            final Map<DayDescription, String> exampleEventsMap = new HashMap();
            exampleEventsMap.put(exampleEvent, "sdsdsds");
            GridCellAdapter adapter = new GridCellAdapter(context, month, year, exampleEventsMap, new CalendarAdapterCallback());
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
                    GridCellAdapter adapter = new GridCellAdapter(context, month, year, exampleEventsMap, new CalendarAdapterCallback());
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
            if (eventsView == null)
            {
                eventsView = (ListView) inflater.inflate(R.layout.group_name, parent, false);
                convertView = eventsView;
            } else
            {
                convertView = eventsView;
            }

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    public class CalendarAdapterCallback
    {
        public void setEventListAdapter(List<String> events)
        {
            if (events == null)
            {
                eventsAdapter = null;
            } else
            {
                eventsAdapter = new NewsAdapter(context, new NewsItem[]{new NewsItem("sdsds", "sdsds", "sdsfsd", "asdasd", "sadasd", 0)});
                if (eventsView != null)
                {
                    eventsView.setAdapter(eventsAdapter);
                    //eventsAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
