package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class CalendarFragment extends BaseFragment//TODO сохранять состояние календаря
{
    private final String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    private ViewGroup container;
    private TextView currentMonthTextView;
    private GridView calendarView;

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
            public void pushResult(String[] results)
            {
                if (results == null)
                {
                    Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();//TODO перенести в базовый класс сообщение о невозможности загрузить?
                } else
                {
                    Map<Calendar, NewsItem[]> dateToEvents = new HashMap<Calendar, NewsItem[]>(results.length);
                    for (int i = 0; i < results.length; i++)//TODO делаю в главном потоке(
                    {
                        if (results[i] != null)
                        {
                            Calendar currentCalendar = Calendar.getInstance();
                            currentCalendar.setTime(eventDates[i]);
                            dateToEvents.put(currentCalendar, Parser.parseNews(results[i]));
                        }
                    }
                    if (isAdded())
                    {
                        showCalendar(dateToEvents);
                    }
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
                        handleLoadProblem();
                    }
                } else
                {
                    handleLoadProblem();
                }
            }
        });
        downloader.execute(getSection().getCalendarURL());
    }

    private void showCalendar(final Map<Calendar, NewsItem[]> dateToEvents)
    {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        View calendarContainer = getLayoutInflater(null).inflate(R.layout.calendar_layout, container, false);
        calendarView = (GridView) calendarContainer.findViewById(R.id.calendar);
        currentMonthTextView = (TextView) calendarContainer.findViewById(R.id.current_month);
        setCalendarAdapter(dateToEvents, calendar, currentMonthTextView, calendarView);
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
                        calendar.add(Calendar.MONTH, -1);
                        break;
                    case R.id.next_month:
                        calendar.add(Calendar.MONTH, 1);
                        break;
                    default:
                        return;
                }

                setCalendarAdapter(dateToEvents, calendar, currentMonthTextView, calendarView);
            }
        };
        previousMonth.setOnClickListener(listener);
        nextMonth.setOnClickListener(listener);
        container.removeAllViews();
        container.addView(calendarContainer);
    }

    private void setCalendarAdapter(Map<Calendar, NewsItem[]> dateToEvents, Calendar calendar, TextView currentMonth, GridView calendarView)
    {
        GridCellAdapter adapter = new GridCellAdapter(getActivity(), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), dateToEvents);
        currentMonth.setText(months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
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
