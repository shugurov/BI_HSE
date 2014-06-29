package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.lists.AnnouncesFragment;
import ru.hse.shugurov.bi_application.gui.fragments.lists.ArchiveFragment;
import ru.hse.shugurov.bi_application.sections.EventsSection;

/**
 * Created by Иван on 09.01.14.
 */
public class EventsFragment extends BaseFragment implements View.OnClickListener//TODO календаря нет в кэше
{
    private int lastPressedButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.events, container, false);
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
        Fragment fragmentToBeShown = null;
        Bundle arguments = new Bundle();
        switch (getSection().getCurrentState())
        {
            case ANNOUNCES:
                fragmentToBeShown = new AnnouncesFragment();
                break;
            case CALENDAR:
                fragmentToBeShown = new CalendarFragment();
                break;
            case ARCHIVE:
                fragmentToBeShown = new ArchiveFragment();
                break;
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        arguments.putSerializable(BaseFragment.SECTION_TAG, getSection());
        fragmentToBeShown.setArguments(arguments);
        transaction.replace(R.id.events_container, fragmentToBeShown);
        transaction.commit();
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
                    getSection().setCurrentState(EventsSection.EventScreenState.ANNOUNCES);
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
                    getSection().setCurrentState(EventsSection.EventScreenState.CALENDAR);
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
                    getSection().setCurrentState(EventsSection.EventScreenState.ARCHIVE);
                    fragmentToBeShown = new ArchiveFragment();
                }
                break;
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
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
    protected void readStateFromBundle(Bundle args)
    {
        super.readStateFromBundle(args);
        switch (getSection().getCurrentState())
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
    public EventsSection getSection()
    {
        return (EventsSection) super.getSection();
    }
}