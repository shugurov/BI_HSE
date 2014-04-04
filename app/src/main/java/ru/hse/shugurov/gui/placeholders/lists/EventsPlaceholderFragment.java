package ru.hse.shugurov.gui.placeholders.lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import ru.hse.shugurov.CallBack;
import ru.hse.shugurov.Downloader;
import ru.hse.shugurov.FileCache;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.adapters.CalendarAdapter;
import ru.hse.shugurov.gui.adapters.NewsAdapter;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.items.NewsItemPlaceholderFragment;
import ru.hse.shugurov.model.NewsItem;
import ru.hse.shugurov.model.Parser;
import ru.hse.shugurov.sections.EventsScreen;

/**
 * Created by Иван on 09.01.14.
 */
public class EventsPlaceholderFragment extends PlaceholderFragment implements View.OnClickListener, AdapterView.OnItemClickListener//TODO календаря нету в кэше
{
    private int lastPressedButton;
    private EventsScreen currentScreen;
    private LinearLayout root;
    private ListView list;
    private View currentView;

    public EventsPlaceholderFragment(MainActivity.FragmentListener fragmentListener, EventsScreen currentScreen)
    {
        super(fragmentListener, currentScreen);
        this.currentScreen = currentScreen;
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
                    expandableListView.setAdapter(new CalendarAdapter(getActivity()));
                    root.addView(expandableListView, 0);
                    currentView = expandableListView;
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
                        expandableListView.setAdapter(new CalendarAdapter(getActivity()));
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
            NewsItemPlaceholderFragment newsItemPlaceholderFragment = new NewsItemPlaceholderFragment((NewsItem) item, getFragmentListener(), getSection());
            getFragmentManager().beginTransaction().replace(R.id.container, newsItemPlaceholderFragment).commit();
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
                final FileCache fileCache = FileCache.instance();
                String value = fileCache.get(getSection().getTitle() + "_events");
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
                    });
                    downloader.execute(currentScreen.getAnnouncesURL());
                } else
                {
                    ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(value));
                    currentScreen.setAnnounceAdapter(adapter);
                    if (currentScreen.getCurrentState() == EventsScreen.EventScreenState.ANNOUNCES)
                    {
                        changeAdapters(adapter);
                    }
                }
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
                public void run()
                {
                    final FileCache fileCache = FileCache.instance();
                    String datesString = fileCache.get(getSection().getTitle() + "_calendar_dates");
                    if (datesString != null)
                    {
                        String[] eventDates = datesString.split(" ");
                        downloadEventsForDates(eventDates);
                    } else
                    {
                        Downloader downloader = new Downloader(new CallBack()
                        {
                            @Override
                            public void call(String[] results)
                            {
                                if (results != null && results[0] != null)
                                {
                                    try
                                    {
                                        String[] eventsDates = Parser.parseEventDates(results[0]);
                                        downloadEventsForDates(eventsDates);

                                    } catch (JSONException e)
                                    {
                                        Toast.makeText(getActivity(), "Ошибка в ходе загрузки данных", Toast.LENGTH_SHORT).show();
                                    }
                                } else
                                {
                                    Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        downloader.execute(currentScreen.getCalendarURL());
                    }
                }
            };
            new Thread(loading).start();
        }
    }


    private void downloadEventsForDates(String[] eventDates)
    {
        Downloader downloader = new Downloader(new CallBack()
        {
            @Override
            public void call(String[] results)
            {
                if (results != null)
                {
                    removeCurrentView();
                    CalendarAdapter adapter = new CalendarAdapter(getActivity());
                    currentScreen.setCalendarAdapter(adapter);
                    if (currentScreen.getCurrentState() == EventsScreen.EventScreenState.CALENDAR)
                    {
                        ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);
                        expandableListView.setAdapter(adapter);
                        root.addView(expandableListView, 0);//TODO почему тут не в главном потоке добавляю?
                        currentView = expandableListView;
                    }
                } else
                {
                    Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                }
            }
        });
        downloader.execute(formRequests(eventDates));
    }

    private String[] formRequests(String[] dates)
    {
        String[] requests = new String[dates.length];
        String requestBeginning = getRequestBeginning();
        for (int i = 0; i < requests.length; i++)
        {
            requests[i] = requestBeginning + dates[i];
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
                final FileCache fileCache = FileCache.instance();
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
                }
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
}
