package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;

/**
 * Created by Иван on 17.06.2014.
 */
public class CalendarFragment extends BaseFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.progress, container, false);
    }

    private void downloadEventsForDates(final Date[] eventDates)
    {
        /*Downloader downloader = new Downloader(new Downloader.MultipleRequestResultCallback()
        {
            @Override
            public void pushResult(String[] results)//TODO не проверяю isAdded
            {
                if (results == null)
                {
                    Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();//TODO перенести в базовый класс сообщение о невозможности загрузить?
                } else
                {
                    removeCurrentView();
                    Map<Calendar, NewsItem[]> dateToNews = new HashMap<Calendar, NewsItem[]>(results.length);
                    for (int i = 0; i < results.length; i++)
                    {
                        if (results[i] != null)
                        {
                            Calendar currentCalendar = Calendar.getInstance();
                            currentCalendar.setTime(eventDates[i]);
                            dateToNews.put(currentCalendar, Parser.parseNews(results[i]));
                        }
                    }
                    /*CalendarAdapter adapter = new CalendarAdapter(getActivity(), dateToNews);
                    currentScreen.setCalendarAdapter(adapter);
                    if (currentScreen.getCurrentState() == EventsScreen.EventScreenState.CALENDAR)
                    {
                        ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);
                        expandableListView.setAdapter(adapter);
                        root.addView(expandableListView, 0);
                        currentView = expandableListView;
                    }

                }
            }
        });
        downloader.execute(formRequests(eventDates));*/
    }

    private void loadCalendar()
    {
       /* final ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);
        if (currentScreen.getCalendarAdapter() != null)
        {
            removeCurrentView();
            expandableListView.setAdapter(currentScreen.getCalendarAdapter());
            root.addView(expandableListView);
        } else
        {
            currentView = getLayoutInflater(null).inflate(R.layout.progress, root, false);
            root.addView(currentView, 0);
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
            FileDescription eventsFileDescription = new FileDescription("", currentScreen.getCalendarURL());//TODO wtf?
            downloader.execute(currentScreen.getCalendarURL());
        }*/
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
}
