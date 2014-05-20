package ru.hse.shugurov.bi_application.gui.fragments.lists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.hse.shugurov.bi_application.Downloader;
import ru.hse.shugurov.bi_application.FileDescription;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.adapters.NewsAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.NewsItemFragment;
import ru.hse.shugurov.bi_application.model.NewsItem;
import ru.hse.shugurov.bi_application.sections.EventsScreen;

/**
 * Created by Иван on 09.01.14.
 */
public class EventsFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener//TODO календаря нет в кэше
{
    public static final String CURRENT_SCREEN_TAG = "current_screen";
    private int lastPressedButton;
    private EventsScreen currentScreen;
    private LinearLayout root;
    private ListView list;
    private View currentView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = (LinearLayout) inflater.inflate(R.layout.events, container, false);
        //select appropriate button
        switch (lastPressedButton)
        {
            case R.id.events_announce_image:
                root.findViewById(R.id.events_calendar_image).setOnClickListener(this);
                ImageView imageView = (ImageView) root.findViewById(R.id.events_announce_image);
                imageView.setOnClickListener(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.anons_button_pressed));
                root.findViewById(R.id.events_archives_image).setOnClickListener(this);
                break;
            case R.id.events_calendar_image:
                imageView = (ImageView) root.findViewById(R.id.events_calendar_image);
                imageView.setOnClickListener(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.calendar_button_pressed));
                root.findViewById(R.id.events_announce_image).setOnClickListener(this);
                root.findViewById(R.id.events_archives_image).setOnClickListener(this);
                break;
            case R.id.events_archives_image:
                root.findViewById(R.id.events_calendar_image).setOnClickListener(this);
                root.findViewById(R.id.events_announce_image).setOnClickListener(this);
                imageView = (ImageView) root.findViewById(R.id.events_archives_image);
                imageView.setOnClickListener(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.archive_button_pressed));
                break;
        }
        list = (ListView) inflater.inflate(R.layout.list, root, false);
        list.setOnItemClickListener(this);
        switch (currentScreen.getCurrentState())
        {
            case ANNOUNCES:
                if (currentScreen.getAnnounceAdapter() == null)
                {
                    loadAnnounces();
                } else
                {
                    setAdapterToList(currentScreen.getAnnounceAdapter());
                }
                break;
            case CALENDAR:
                if (currentScreen.getCalendarAdapter() == null)
                {
                    loadCalendar();
                } else
                {
                    ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);//TODO копипаст(
                    if (currentScreen.getCalendarAdapter() == null)
                    {
                        loadCalendar();
                    } else
                    {
                        expandableListView.setAdapter(currentScreen.getCalendarAdapter());
                        root.addView(expandableListView, 0);
                        currentView = expandableListView;
                    }
                }
                break;
            case ARCHIVE:
                if (currentScreen.getArchiveAdapter() == null)
                {
                    loadArchive();
                } else
                {
                    setAdapterToList(currentScreen.getArchiveAdapter());
                }
                break;
        }
        return root;
    }

    @Override
    public void onClick(View view)
    {
        ListAdapter adapter;
        switch (view.getId())
        {
            case R.id.events_announce_image://TODO проверить, корректно ли работает
                if (lastPressedButton == R.id.events_announce_image)
                {
                    return;
                } else
                {
                    ((ImageView) getView().findViewById(R.id.events_announce_image)).setImageDrawable(getResources().getDrawable(R.drawable.anons_button_pressed));
                    releaseButton(lastPressedButton);
                    lastPressedButton = R.id.events_announce_image;
                    currentScreen.setCurrentState(EventsScreen.EventScreenState.ANNOUNCES);
                    adapter = currentScreen.getAnnounceAdapter();
                    removeCurrentView();
                    if (adapter != null)
                    {
                        setAdapterToList(adapter);
                    } else
                    {
                        loadAnnounces();
                    }
                }
                break;
            case R.id.events_calendar_image:
                if (lastPressedButton == R.id.events_calendar_image)
                {
                    return;
                } else
                {
                    ((ImageView) getView().findViewById(R.id.events_calendar_image)).setImageDrawable(getResources().getDrawable(R.drawable.calendar_button_pressed));
                    releaseButton(lastPressedButton);
                    lastPressedButton = R.id.events_calendar_image;
                    currentScreen.setCurrentState(EventsScreen.EventScreenState.CALENDAR);
                    removeCurrentView();
                    if (currentScreen.getCalendarAdapter() != null)
                    {

                        ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);
                        expandableListView.setAdapter(currentScreen.getCalendarAdapter());
                        root.addView(expandableListView, 0);
                        currentView = expandableListView;
                    } else
                    {
                        loadCalendar();
                    }
                }
                break;
            case R.id.events_archives_image:
                if (lastPressedButton == R.id.events_archives_image)
                {
                    return;
                } else
                {
                    removeCurrentView();
                    ((ImageView) getView().findViewById(R.id.events_archives_image)).setImageDrawable(getResources().getDrawable(R.drawable.archive_button_pressed));
                    releaseButton(lastPressedButton);
                    lastPressedButton = R.id.events_archives_image;
                    currentScreen.setCurrentState(EventsScreen.EventScreenState.ARCHIVE);
                    adapter = currentScreen.getArchiveAdapter();
                    if (adapter != null)
                    {
                        if (list != null)
                        {
                            list.setAdapter(adapter);
                            currentView = list;
                            root.addView(list, 0);
                        }
                    } else
                    {
                        loadArchive();
                    }
                }
                break;
        }
    }

    private void releaseButton(int id)
    {
        switch (id)
        {
            case R.id.events_announce_image:
                ((ImageView) getView().findViewById(R.id.events_announce_image)).setImageDrawable(getResources().getDrawable(R.drawable.anons_button));
                break;
            case R.id.events_calendar_image:
                ((ImageView) getView().findViewById(R.id.events_calendar_image)).setImageDrawable(getResources().getDrawable(R.drawable.calendar_button));
                break;
            case R.id.events_archives_image:
                ((ImageView) getView().findViewById(R.id.events_archives_image)).setImageDrawable(getResources().getDrawable(R.drawable.archive_button));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        NewsAdapter adapter;
        switch (currentScreen.getCurrentState())
        {
            case ANNOUNCES:
                adapter = (NewsAdapter) currentScreen.getAnnounceAdapter();
                break;
            case ARCHIVE:
                adapter = (NewsAdapter) currentScreen.getArchiveAdapter();
                break;
            default:
                return;
        }
        Object item = adapter.getItem(position);
        if (item instanceof NewsItem)
        {
            Fragment newsItemPlaceholderFragment = new NewsItemFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable(NewsItemFragment.SECTION_TAG, getSection());
            arguments.putSerializable(NewsItemFragment.ITEM_TAG, (NewsItem) item);
            newsItemPlaceholderFragment.setArguments(arguments);
            showChildFragment(newsItemPlaceholderFragment);
        }
    }


    private void loadAnnounces()
    {
        currentView = getLayoutInflater(null).inflate(R.layout.progress, root, false);
        root.addView(currentView, 0);
        Runnable loadingAnnounces = new Runnable()
        {
            @Override
            public void run()
            {
                /*final FileCache fileCache = FileCache.instance();TODO проверять по-другому
                String value = fileCache.get(getSection().getTitle() + "_events");
                if (value == null)
                {
                    /*Downloader downloader = new Downloader(new CallBack()//TODO remove?
                    {
                        @Override
                        public void call(String[] results)
                        {
                            if (results != null && results[0] != null)
                            {
                                final ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(results[0]));
                                currentScreen.setAnnounceAdapter(adapter);
                                fileCache.add(getSection().getTitle() + "_events", results[0]);
                                if (currentScreen.getCurrentState() == EventsScreen.EventScreenState.ANNOUNCES)
                                {
                                    changeAdapters(adapter);
                                }
                            } else
                            {
                                Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/
                String fileName = getSection().getTitle() + "_events";
                String url = currentScreen.getAnnouncesURL();
                FileDescription description = new FileDescription(fileName, url);
                List<FileDescription> descriptionsList = new ArrayList<FileDescription>();
                descriptionsList.add(description);
                Downloader downloader = new Downloader(getActivity(), descriptionsList, new Downloader.DownloadCallback()
                {
                    @Override
                    public void downloadFinished()//TODO как-то проверять успешность
                    {
                            /*final ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(results[0]));TODO раскомментировать
                            currentScreen.setAnnounceAdapter(adapter);
                            if (currentScreen.getCurrentState() == EventsScreen.EventScreenState.ANNOUNCES)
                            {
                                changeAdapters(adapter);
                            }*/
                    }
                });
                downloader.execute();
                /*} else
                {
                    ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(value));
                    currentScreen.setAnnounceAdapter(adapter);
                    if (currentScreen.getCurrentState() == EventsScreen.EventScreenState.ANNOUNCES)
                    {
                        changeAdapters(adapter);
                    }
                }TODO раскомментировать*/
            }
        };
        new Thread(loadingAnnounces).start();
    }

    private void loadCalendar()
    {
        final ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);
        if (currentScreen.getCalendarAdapter() != null)
        {
            removeCurrentView();
            expandableListView.setAdapter(currentScreen.getCalendarAdapter());
            root.addView(expandableListView);
        } else
        {
            currentView = getLayoutInflater(null).inflate(R.layout.progress, root, false);
            root.addView(currentView, 0);
            Runnable loading = new Runnable()
            {
                @Override
                public void run()//TODO в чём смысл запускать поток в отдельном потоке?
                {
                    /*Downloader downloader = new Downloader(new CallBack()TODO новый скачиватель
                    {
                        @Override
                        public void call(String[] results)
                        {
                            if (results != null && results[0] != null)
                            {
                                try
                                {
                                    String[] datesInStringFormat = Parser.parseEventDates(results[0]);
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.M.yy");
                                    Date[] eventDates = new Date[datesInStringFormat.length];
                                    for (int i = 0; i < datesInStringFormat.length; i++)
                                    {
                                        eventDates[i] = dateFormat.parse(datesInStringFormat[i]);
                                    }
                                    downloadEventsForDates(eventDates);

                                } catch (JSONException e)
                                {
                                    Toast.makeText(getActivity(), "Ошибка в ходе загрузки данных", Toast.LENGTH_SHORT).show();
                                } catch (ParseException e)
                                {
                                    Toast.makeText(getActivity(), "Ошибка в ходе загрузки данных", Toast.LENGTH_SHORT).show();
                                }
                            } else
                            {
                                Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    FileDescription eventsFileDescription = new FileDescription("",currentScreen.getCalendarURL());
                    downloader.execute(currentScreen.getCalendarURL());TODO раскомментировать*/

                }
            };
            new Thread(loading).start();
        }
    }


    private void downloadEventsForDates(final Date[] eventDates)
    {
        /*Downloader downloader = new Downloader(new CallBack()
        {
            @Override
            public void call(String[] results)
            {
                if (results != null)
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
                    CalendarAdapter adapter = new CalendarAdapter(getActivity(), dateToNews);
                    currentScreen.setCalendarAdapter(adapter);
                    if (currentScreen.getCurrentState() == EventsScreen.EventScreenState.CALENDAR)
                    {
                        ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);
                        expandableListView.setAdapter(adapter);
                        root.addView(expandableListView, 0);
                        currentView = expandableListView;
                    }
                } else
                {
                    Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                }
            }
        });
        downloader.execute(formRequests(eventDates)); TODO раскомментировать*/
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


    private void loadArchive()
    {
        currentView = getLayoutInflater(null).inflate(R.layout.progress, root, false);
        root.addView(currentView, 0);
        Runnable loading = new Runnable()
        {
            @Override
            public void run()
            {
                /*final FileCache fileCache = FileCache.instance();
                String value = fileCache.get(getSection().getTitle() + "_archive");
                if (value == null)
                {
                    Downloader downloader = new Downloader(new CallBack()
                    {
                        @Override
                        public void call(String[] results)
                        {
                            if (results != null && results[0] != null)
                            {
                                final ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(results[0]));
                                fileCache.add(getSection().getTitle() + "_archive", results[0]);
                                currentScreen.setArchiveAdapter(adapter);
                                if (currentScreen.getCurrentState() == EventsScreen.EventScreenState.ARCHIVE)
                                {
                                    changeAdapters(adapter);
                                }
                            } else
                            {
                                Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    downloader.execute(currentScreen.getArchiveURL());
                } else
                {

                    //TODO что делать если есть кэшированный файл с календарём?
                    ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(value));
                    currentScreen.setArchiveAdapter(adapter);
                    if (currentScreen.getCurrentState() == EventsScreen.EventScreenState.ARCHIVE)
                    {
                        changeAdapters(adapter);
                    }
                }TODO раскомментировать, поправить*/
            }
        };
        new Thread(loading).start();
    }

    private void changeAdapters(final ListAdapter adapter)
    {
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                setAdapterToList(adapter);
            }
        });
    }

    private void setAdapterToList(ListAdapter adapter)//TODO а зачем постоянно удалять список?(
    {
        removeCurrentView();
        list.setAdapter(adapter);
        root.addView(list, 0);
        currentView = list;
    }

    private void removeCurrentView()
    {
        if (currentView != null)
        {
            root.removeView(currentView);
        }
        currentView = null;
    }

    @Override
    protected void readStateFromBundle(Bundle args)
    {
        super.readStateFromBundle(args);
        currentScreen = (EventsScreen) args.get(CURRENT_SCREEN_TAG);
        switch (currentScreen.getCurrentState())
        {
            case ANNOUNCES:
                lastPressedButton = R.id.events_announce_image;
                break;
            case CALENDAR:
                lastPressedButton = R.id.events_calendar_image;
                break;
            case ARCHIVE:
                lastPressedButton = R.id.events_archives_image;
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CURRENT_SCREEN_TAG, currentScreen);
    }
}
