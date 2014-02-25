package ru.hse.shugurov.gui.placeholders.lists;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import ru.hse.shugurov.CallBack;
import ru.hse.shugurov.Downloader;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
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
    private View progress;
    private Downloader downloader;

    public EventsPlaceholderFragment(Context context, MainActivity.FragmentListener fragmentListener, MultipleViewScreen currentScreen)
    {
        super(context, fragmentListener, currentScreen);
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
        progress = inflater.inflate(R.layout.progress, root, false);
        currentView = progress;
        root.addView(progress, 0);
        list = (ListView) inflater.inflate(R.layout.list, root, false);
        list.setOnItemClickListener(this);
        downloader = new Downloader(new CallBack()
        {
            @Override
            public void call(String[] results)
            {
                if (results != null)
                {
                    root.removeView(progress);
                    ListAdapter adapter = new NewsAdapter(getContext(), Parser.parseNews(results[0]));
                    currentScreen.setAdapter(currentScreen.getCurrentState(), adapter);
                    list.setAdapter(adapter);
                    root.addView(list, 0);
                    currentView = list;
                    downloader = null;
                } else
                {
                    Toast.makeText(getContext(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (currentScreen.getCurrentState() != 1 && currentScreen.getAdapter(currentScreen.getCurrentState()) == null)
        {
            downloader.execute(currentScreen.getUrl(currentScreen.getCurrentState()));
        } else
        {
            root.removeView(progress);
            list.setAdapter(currentScreen.getAdapter(currentScreen.getCurrentState()));
            currentView = list;
            root.addView(list, 0);
        }
        return root;
    }

    @Override
    public void onClick(View view)
    {
        ListAdapter adapter;
        CallBack callBack = null;
        String url = null;
        switch (view.getId())
        {
            case R.id.events_announce_image:
                if (lastPressedButton == R.id.events_announce_image)
                {
                    return;
                } else
                {
                    if (downloader != null)
                    {
                        downloader.cancel(false);//TODO а проверяю ли я отмену?
                    }
                    ((ImageView) getView().findViewById(R.id.events_announce_image)).setImageDrawable(getResources().getDrawable(R.drawable.anons_button_pressed));
                    releaseButton(lastPressedButton);
                    lastPressedButton = R.id.events_announce_image;
                    currentScreen.setCurrentState(0);
                    adapter = currentScreen.getAdapter(0);
                    root.removeView(currentView);
                    if (adapter != null)
                    {
                        if (list != null)
                        {
                            list.setAdapter(adapter);

                            root.addView(list, 0);
                        }
                    } else
                    {
                        callBack = new CallBack()
                        {
                            @Override
                            public void call(String[] results)
                            {
                                if (results != null)
                                {
                                    root.removeView(progress);
                                    ListAdapter adapter = new NewsAdapter(getContext(), Parser.parseNews(results[0]));
                                    currentScreen.setAdapter(0, adapter);
                                    list.setAdapter(adapter);
                                    root.addView(list, 0);
                                    currentView = list;
                                    downloader = null;
                                } else
                                {
                                    Toast.makeText(getContext(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        url = currentScreen.getUrl(0);
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
                    root.removeView(currentView);
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
                    ((ImageView) getView().findViewById(R.id.events_archives_image)).setImageDrawable(getResources().getDrawable(R.drawable.archive_button_pressed));
                    releaseButton(lastPressedButton);
                    lastPressedButton = R.id.events_archives_image;
                    currentScreen.setCurrentState(2);
                    adapter = currentScreen.getAdapter(2);
                    root.removeView(currentView);
                    if (adapter != null)
                    {
                        if (list != null)
                        {
                            list.setAdapter(adapter);
                            root.addView(list, 0);
                        }
                    } else
                    {
                        callBack = new CallBack()
                        {
                            @Override
                            public void call(String[] results)
                            {
                                if (results != null)
                                {
                                    root.removeView(progress);
                                    ListAdapter adapter = new NewsAdapter(getContext(), Parser.parseNews(results[0]));
                                    currentScreen.setAdapter(2, adapter);
                                    list.setAdapter(adapter);
                                    root.addView(list, 0);
                                    currentView = list;
                                    downloader = null;
                                } else
                                {
                                    Toast.makeText(getContext(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        url = currentScreen.getUrl(2);
                    }
                }

                break;
        }
        if (callBack != null)
        {
            if (currentView != null)
            {
                root.removeView(currentView);
            }
            currentView = progress;
            downloader = new Downloader(callBack);
            root.addView(progress, 0);
            downloader.execute(url);
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
            NewsItemPlaceholderFragment newsItemPlaceholderFragment = new NewsItemPlaceholderFragment(getContext(), (NewsItem) item, getFragmentListener(), getSection());
            getFragmentManager().beginTransaction().replace(R.id.container, newsItemPlaceholderFragment).commit();
        }
    }
}
