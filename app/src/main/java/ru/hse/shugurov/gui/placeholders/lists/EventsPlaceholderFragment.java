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
import ru.hse.shugurov.sections.MultipleViewScreen;

/**
 * Created by Иван on 09.01.14.
 */
public class EventsPlaceholderFragment extends PlaceholderFragment implements View.OnClickListener, AdapterView.OnItemClickListener
{
    private int lastPressedButton;
    private MultipleViewScreen currentScreen;
    private LinearLayout root;
    private ListView list;
    private View currentView;
    private Downloader downloader;

    public EventsPlaceholderFragment(MainActivity.FragmentListener fragmentListener, MultipleViewScreen currentScreen)
    {
        super(fragmentListener, currentScreen);
        this.currentScreen = currentScreen;
        if (currentScreen.getAdaptersNumber() != 3 || currentScreen.getUrlsNumber() != 3)
        {
            throw new IllegalArgumentException("precondition violated in EventsPlaceHolder. Incorrect MultipleViewScreen");
        }
        switch (currentScreen.getCurrentState())
        {
            case 0:
                lastPressedButton = R.id.events_announce_image;
                break;
            case 1:
                lastPressedButton = R.id.events_calendar_image;
                break;
            case 2:
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
        if (currentScreen.getCurrentState() != 1 && currentScreen.getAdapter(currentScreen.getCurrentState()) == null)
        {
            loadAnnounces();
        } else
        {
            if (currentScreen.getCurrentState() != 1)
            {
                list.setAdapter(currentScreen.getAdapter(currentScreen.getCurrentState()));
                currentView = list;
                root.addView(list, 0);
            }
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
                    currentScreen.setCurrentState(0);
                    adapter = currentScreen.getAdapter(0);
                    removeCurrentView();
                    if (adapter != null)
                    {
                        if (list != null)
                        {
                            list.setAdapter(adapter);
                            root.addView(list, 0);
                            currentView = list;
                        }
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
                    currentScreen.setCurrentState(1);
                    removeCurrentView();
                    if (currentScreen.getAdapter(1) != null)
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
                    if (downloader != null)
                    {
                        downloader.cancel(false);
                    }
                    removeCurrentView();
                    ((ImageView) getView().findViewById(R.id.events_archives_image)).setImageDrawable(getResources().getDrawable(R.drawable.archive_button_pressed));
                    releaseButton(lastPressedButton);
                    lastPressedButton = R.id.events_archives_image;
                    currentScreen.setCurrentState(2);
                    adapter = currentScreen.getAdapter(2);
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
        NewsAdapter adapter = (NewsAdapter) currentScreen.getAdapter(currentScreen.getCurrentState());
        Object item = adapter.getItem(position);
        if (item instanceof NewsItem)
        {
            NewsItemPlaceholderFragment newsItemPlaceholderFragment = new NewsItemPlaceholderFragment(getActivity(), (NewsItem) item, getFragmentListener(), getSection());
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
                    downloader = new Downloader(new CallBack()
                    {
                        @Override
                        public void call(String[] results)
                        {
                            if (results != null && results[0] != null)
                            {
                                ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(results[0]));
                                currentScreen.setAdapter(currentScreen.getCurrentState(), adapter);
                                fileCache.add(getSection().getTitle() + "_events", results[0]);
                                list.setAdapter(adapter);
                                if (currentScreen.getCurrentState() == 0)
                                {
                                    getActivity().runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            removeCurrentView();
                                            root.addView(list, 0);
                                            currentView = list;
                                        }
                                    });
                                }
                            } else
                            {
                                Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    downloader.execute(currentScreen.getUrl(currentScreen.getCurrentState()));
                } else
                {
                    final ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(value));
                    currentScreen.setAdapter(currentScreen.getCurrentState(), adapter);
                    if (currentScreen.getCurrentState() == 0)
                    {
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                removeCurrentView();
                                currentView = list;
                                list.setAdapter(adapter);
                                root.addView(list, 0);
                            }
                        });
                    }
                }
            }
        };
        new Thread(loadingAnnounces).start();
    }

    private void loadCalendar()
    {
        final ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);
        if (currentScreen.getAdapter(currentScreen.getCurrentState()) != null)
        {
            removeCurrentView();
            expandableListView.setAdapter(currentScreen.getAdapter(currentScreen.getCurrentState()));
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
                        downloader = new Downloader(new CallBack()
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
                        downloader.execute(currentScreen.getUrl(1));
                    }
                }
            };
            new Thread(loading).start();
        }
    }


    private void downloadEventsForDates(String[] eventDates)
    {
        downloader = new Downloader(new CallBack()
        {
            @Override
            public void call(String[] results)
            {
                if (results != null)
                {
                    ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);
                    expandableListView.setAdapter(new CalendarAdapter(getActivity()));
                    root.addView(expandableListView, 0);
                    currentView = expandableListView;
                    expandableListView.setAdapter(new CalendarAdapter(getActivity()));
                    root.addView(expandableListView, 0);
                    currentView = expandableListView;
                } else
                {
                    Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                }
            }
        });
        downloader.execute(eventDates);
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
                    downloader = new Downloader(new CallBack()
                    {
                        @Override
                        public void call(String[] results)
                        {
                            if (results != null && results[0] != null)
                            {
                                ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(results[0]));
                                fileCache.add(getSection().getTitle() + "_archive", results[0]);
                                currentScreen.setAdapter(2, adapter);
                                list.setAdapter(adapter);
                                if (currentScreen.getCurrentState() == 2)
                                {
                                    getActivity().runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            removeCurrentView();
                                            root.addView(list, 0);
                                            currentView = list;
                                        }
                                    });
                                }
                            } else
                            {
                                Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    downloader.execute(currentScreen.getUrl(2));
                } else
                {
                    ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(value));
                    currentScreen.setAdapter(2, adapter);
                    list.setAdapter(adapter);
                    if (currentScreen.getCurrentState() == 2)
                    {
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                removeCurrentView();
                                root.addView(list, 0);
                                currentView = list;
                            }
                        });
                    }
                }
            }
        };
        new Thread(loading).start();
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
