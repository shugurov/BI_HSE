package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ru.hse.shugurov.bi_application.Downloader;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.adapters.GridCellAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.model.NewsItem;
import ru.hse.shugurov.bi_application.model.Parser;
import ru.hse.shugurov.bi_application.sections.EventsSection;

/**
 * Created by Иван on 17.06.2014.
 */
public class CalendarFragment extends BaseFragment//TODo пока всё делаю в 1 потоке(
{
    private ViewGroup container;
    //BEGIN TODO remove? hide?
    private int month;
    private int year;

    //END TODO
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        this.container = container;
        loadCalendar();
        return inflater.inflate(R.layout.progress, container, false);
    }

    private void downloadEventsForDates(final Date[] eventDates)
    {
        Downloader downloader = new Downloader(new Downloader.MultipleRequestResultCallback()
        {
            @Override
            public void pushResult(String[] results)//TODO не проверяю isAdded
            {
                if (results == null)
                {
                    Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();//TODO перенести в базовый класс сообщение о невозможности загрузить?
                } else
                {
                    Map<Calendar, NewsItem[]> dateToEvents = new HashMap<Calendar, NewsItem[]>(results.length);
                    for (int i = 0; i < results.length; i++)
                    {
                        if (results[i] != null)
                        {
                            Calendar currentCalendar = Calendar.getInstance();
                            currentCalendar.setTime(eventDates[i]);
                            dateToEvents.put(currentCalendar, Parser.parseNews(results[i]));
                        }
                    }
                    showCalendar(dateToEvents);
                }
            }
        });
        downloader.execute(formRequests(eventDates));
    }

    private void loadCalendar()
    {
        Downloader downloader = new Downloader(new Downloader.RequestResultCallback()
        {
            @Override
            public void pushResult(String result)//TODO не проверяю isAdded
            {
                if (result != null)
                {
                    try
                    {
                        String[] datesInStringFormat = Parser.parseEventDates(result);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.M.yy");
                        Date[] eventDates = new Date[datesInStringFormat.length];
                        for (int i = 0; i < datesInStringFormat.length; i++)
                        {
                            eventDates[i] = dateFormat.parse(datesInStringFormat[i]);
                        }
                        downloadEventsForDates(eventDates);

                    } catch (Exception e)
                    {
                        Toast.makeText(getActivity(), "Ошибка в ходе загрузки данных", Toast.LENGTH_SHORT).show();
                    }
                } else
                {
                    Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                }
            }
        });
        downloader.execute(getSection().getCalendarURL());
    }

    private void showCalendar(final Map<Calendar, NewsItem[]> dateToEvents)//TODO лаги в выборе месяца
    {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        View calendarContainer = getLayoutInflater(null).inflate(R.layout.calendar_layout, container, false);
        final GridView calendarView = (GridView) calendarContainer.findViewById(R.id.calendar);
        final GridCellAdapter adapter = new GridCellAdapter(getActivity(), month, year, dateToEvents);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
        final TextView currentMonth = (TextView) calendarContainer.findViewById(R.id.current_month);
        currentMonth.setText(adapter.getMonthDescription());
        View previousMonth = calendarContainer.findViewById(R.id.previous_month);
        View nextMonth = calendarContainer.findViewById(R.id.next_month);
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
                Log.d("calendar", Integer.toString(month));
                GridCellAdapter adapter = new GridCellAdapter(getActivity(), month, year, dateToEvents);
                calendar.set(year, month - 1, calendar.get(Calendar.DAY_OF_MONTH));
                currentMonth.setText(adapter.getMonthDescription());
                adapter.notifyDataSetChanged();
                calendarView.setAdapter(adapter);
            }
        };
        previousMonth.setOnClickListener(listener);
        nextMonth.setOnClickListener(listener);
        container.removeAllViews();
        container.addView(calendarContainer);
    }

    private String[] formRequests(Date[] dates)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] requests = new String[dates.length];
        String requestBeginning = getRequestBeginning();
        for (int i = 0; i < requests.length; i++)
        {
            requests[i] = requestBeginning + dateFormat.format(dates[i]);
        }
        return requests;
    }

    private String getRequestBeginning()
    {
        String[] requestParts = getActivity().getResources().getStringArray(R.array.day_info_request);
        StringBuilder requestBuilder = new StringBuilder();
        for (int i = 0; i < requestParts.length - 1; i++)
        {
            requestBuilder.append(requestParts[i]);
            requestBuilder.append('&');
        }
        requestBuilder.append(requestParts[requestParts.length - 1]);
        return requestBuilder.toString();
    }

    @Override
    public EventsSection getSection()
    {
        return (EventsSection) super.getSection();
    }
}
