package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

import java.io.IOException;

import ru.hse.shugurov.bi_application.Downloader;
import ru.hse.shugurov.bi_application.FileManager;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.adapters.NewsAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.NewsItemFragment;
import ru.hse.shugurov.bi_application.model.NewsItem;
import ru.hse.shugurov.bi_application.model.Parser;
import ru.hse.shugurov.bi_application.sections.EventsSection;

/**
 * Created by Иван on 09.01.14.
 */
public class EventsFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener//TODO календаря нет в кэше
{
    public static final String CURRENT_SCREEN_TAG = "current_screen";
    private int lastPressedButton;
    private EventsSection currentScreen;
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
               /* if (currentScreen.getCalendarAdapter() == null)TODO что тут происходит?
                {
                    //loadCalendar();  TODO
                } else
                {
                    ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);//TODO копипаст(
                    if (currentScreen.getCalendarAdapter() == null)
                    {
                        //TODO loadCalendar();
                    } else
                    {
                        expandableListView.setAdapter(currentScreen.getCalendarAdapter());
                        root.addView(expandableListView, 0);
                        currentView = expandableListView;
                    }
                }
                break;*/
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
                    currentScreen.setCurrentState(EventsSection.EventScreenState.ANNOUNCES);
                    adapter = currentScreen.getAnnounceAdapter();
                    removeCurrentView();
                    /*if (adapter != null)
                    {
                        setAdapterToList(adapter);
                    } else
                    {
                        Log.d("my log", "load announces from onClick");
                        loadAnnounces();
                    }*/
                }
                break;
            case R.id.events_calendar_image:
                if (lastPressedButton == R.id.events_calendar_image)
                {
                    return;
                } else
                {
                    /* TODO remove
                    releaseButton(lastPressedButton);
                    lastPressedButton = R.id.events_calendar_image;
                    currentScreen.setCurrentState(EventsSection.EventScreenState.CALENDAR);
                    removeCurrentView();
                    if (currentScreen.getCalendarAdapter() != null)
                    {
                        ExpandableListView expandableListView = (ExpandableListView) getLayoutInflater(null).inflate(R.layout.expandable_list, root, false);
                        expandableListView.setAdapter(currentScreen.getCalendarAdapter());
                        root.addView(expandableListView, 0);
                        currentView = expandableListView;
                    } else
                    {
                        //loadCalendar(); TODO
                    }*/
                    ((ImageView) getView().findViewById(R.id.events_calendar_image)).setImageDrawable(getResources().getDrawable(R.drawable.calendar_button_pressed));
                    releaseButton(lastPressedButton);
                    lastPressedButton = R.id.events_calendar_image;
                    currentScreen.setCurrentState(EventsSection.EventScreenState.CALENDAR);
                    removeCurrentView();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    BaseFragment calendarFragment = new CalendarFragment();
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(BaseFragment.SECTION_TAG, getSection());
                    calendarFragment.setArguments(arguments);
                    transaction.replace(R.id.events_container, calendarFragment);
                    transaction.commit();
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
                    currentScreen.setCurrentState(EventsSection.EventScreenState.ARCHIVE);
                    /*adapter = currentScreen.getArchiveAdapter();
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
                    }*/
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
            NewsItemFragment newsItemPlaceholderFragment = new NewsItemFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable(NewsItemFragment.SECTION_TAG, getSection());
            arguments.putSerializable(NewsItemFragment.ITEM_TAG, (NewsItem) item);
            newsItemPlaceholderFragment.setArguments(arguments);
            showNextFragment(newsItemPlaceholderFragment);
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
                final FileManager fileCache = FileManager.instance();
                String value = null;
                try
                {
                    value = fileCache.getFileContent(getSection().getTitle() + "_events");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                if (value == null)
                {
                    Downloader downloader = new Downloader(new Downloader.RequestResultCallback()//TODO I do not save downloaded data, I don't check if activity is added to
                    {
                        @Override
                        public void pushResult(String result)
                        {
                            if (result == null && isAdded())
                            {
                                Toast.makeText(getActivity(), "Нет далось загрузить данные", Toast.LENGTH_SHORT).show();
                            } else
                            {
                                if (isAdded())
                                {
                                    final ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(result));
                                    currentScreen.setAnnounceAdapter(adapter);
                                    if (currentScreen.getCurrentState() == EventsSection.EventScreenState.ANNOUNCES)
                                    {
                                        changeAdapters(adapter);
                                    }
                                }
                                fileCache.writeToFile(getSection().getTitle() + "_events", result);
                            }
                        }
                    });
                    downloader.execute(currentScreen.getAnnouncesURL());

                } else
                {
                    ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(value));
                    currentScreen.setAnnounceAdapter(adapter);
                    if (currentScreen.getCurrentState() == EventsSection.EventScreenState.ANNOUNCES)
                    {
                        changeAdapters(adapter);
                    }
                }
            }
        };
        new Thread(loadingAnnounces).start();
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
                final FileManager fileCache = FileManager.instance();
                String value = null;
                try
                {
                    value = fileCache.getFileContent(getSection().getTitle() + "_archive");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                if (value == null)//TODo не читаю и не записываю в файл в отдельном потоке
                {
                    Downloader downloader = new Downloader(new Downloader.RequestResultCallback()
                    {
                        @Override
                        public void pushResult(String result)
                        {
                            if (result != null)//TODO не прверяю isAdded
                            {
                                final ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(result));
                                fileCache.writeToFile(getSection().getTitle() + "_archive", result);
                                currentScreen.setArchiveAdapter(adapter);
                                if (currentScreen.getCurrentState() == EventsSection.EventScreenState.ARCHIVE)
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
                    if (currentScreen.getCurrentState() == EventsSection.EventScreenState.ARCHIVE)
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

    @Override
    protected void readStateFromBundle(Bundle args)
    {
        super.readStateFromBundle(args);
        currentScreen = (EventsSection) args.get(CURRENT_SCREEN_TAG);
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