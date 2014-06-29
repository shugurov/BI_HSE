package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.adapters.NewsAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.NewsItemFragment;
import ru.hse.shugurov.bi_application.gui.fragments.lists.AnnouncesFragment;
import ru.hse.shugurov.bi_application.gui.fragments.lists.ArchiveFragment;
import ru.hse.shugurov.bi_application.model.NewsItem;
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

                break;
            case CALENDAR:

                break;
            case ARCHIVE:

                break;
        }
        return root;
    }

    @Override
    public void onClick(View view)
    {
        Fragment fragmentToBeShown = null;
        Bundle arguments = new Bundle();
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
                    fragmentToBeShown = new AnnouncesFragment();

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
                    currentScreen.setCurrentState(EventsSection.EventScreenState.CALENDAR);
                    fragmentToBeShown = new CalendarFragment();
                }
                break;
            case R.id.events_archives_image:
                if (lastPressedButton == R.id.events_archives_image)
                {
                    return;
                } else
                {
                    ((ImageView) getView().findViewById(R.id.events_archives_image)).setImageDrawable(getResources().getDrawable(R.drawable.archive_button_pressed));
                    releaseButton(lastPressedButton);
                    lastPressedButton = R.id.events_archives_image;
                    currentScreen.setCurrentState(EventsSection.EventScreenState.ARCHIVE);
                    fragmentToBeShown = new ArchiveFragment();
                }
                break;
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        arguments.putSerializable(BaseFragment.SECTION_TAG, getSection());
        fragmentToBeShown.setArguments(arguments);
        transaction.replace(R.id.events_container, fragmentToBeShown);
        transaction.commit();
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
                adapter = (NewsAdapter) currentScreen.getAnnouncesAdapter();
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