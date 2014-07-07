package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.os.Bundle;
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
{//TODO подтормаживает меню
    //TODO не меняю заголовок в action bar при переходе на специфичные фрагменты
    //TODO если открыть событие, перейти на другой экран, а потом сново открыть, то будет грустно(
    private int lastPressedButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.events, container, false);
        BaseFragment fragmentToBeShown = null;
        Bundle arguments = new Bundle();
        switch (getSection().getCurrentState())
        {
            case ANNOUNCES:
                lastPressedButton = R.id.events_announce_image;
                ImageView imageView = (ImageView) root.findViewById(R.id.events_announce_image);
                imageView.setOnClickListener(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.anons_button_pressed));
                fragmentToBeShown = new AnnouncesFragment();
                break;
            case CALENDAR:
                lastPressedButton = R.id.events_calendar_image;
                imageView = (ImageView) root.findViewById(R.id.events_calendar_image);
                imageView.setOnClickListener(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.calendar_button_pressed));
                fragmentToBeShown = new CalendarFragment();
                break;
            case ARCHIVE:
                lastPressedButton = R.id.events_archives_image;
                imageView = (ImageView) root.findViewById(R.id.events_archives_image);
                imageView.setOnClickListener(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.archive_button_pressed));
                fragmentToBeShown = new ArchiveFragment();
                break;
        }
        root.findViewById(R.id.events_archives_image).setOnClickListener(this);
        root.findViewById(R.id.events_calendar_image).setOnClickListener(this);
        root.findViewById(R.id.events_announce_image).setOnClickListener(this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();//TODO овторяю один код дважды(
        arguments.putSerializable(BaseFragment.SECTION_TAG, getSection());
        fragmentToBeShown.setArguments(arguments);
        transaction.replace(R.id.events_container, fragmentToBeShown);
        transaction.commit();
        return root;
    }

    @Override
    public void onClick(View view)
    {
        BaseFragment fragmentToBeShown = null;
        Bundle arguments = new Bundle();
        if (view.getId() == lastPressedButton)
        {
            return;
        }
        switch (view.getId())
        {
            case R.id.events_announce_image:
                ((ImageView) getView().findViewById(R.id.events_announce_image)).setImageDrawable(getResources().getDrawable(R.drawable.anons_button_pressed));
                releaseButton(lastPressedButton);
                lastPressedButton = R.id.events_announce_image;
                getSection().setCurrentState(EventsSection.EventScreenState.ANNOUNCES);
                fragmentToBeShown = new AnnouncesFragment();
                break;
            case R.id.events_calendar_image:
                ((ImageView) getView().findViewById(R.id.events_calendar_image)).setImageDrawable(getResources().getDrawable(R.drawable.calendar_button_pressed));
                releaseButton(lastPressedButton);
                lastPressedButton = R.id.events_calendar_image;
                getSection().setCurrentState(EventsSection.EventScreenState.CALENDAR);
                fragmentToBeShown = new CalendarFragment();

                break;
            case R.id.events_archives_image:
                ((ImageView) getView().findViewById(R.id.events_archives_image)).setImageDrawable(getResources().getDrawable(R.drawable.archive_button_pressed));
                releaseButton(lastPressedButton);
                lastPressedButton = R.id.events_archives_image;
                getSection().setCurrentState(EventsSection.EventScreenState.ARCHIVE);
                fragmentToBeShown = new ArchiveFragment();

                break;
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        arguments.putSerializable(BaseFragment.SECTION_TAG, getSection());
        fragmentToBeShown.setArguments(arguments);
        fragmentToBeShown.setBackStack(getBackStack());
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
    public EventsSection getSection()
    {
        return (EventsSection) super.getSection();
    }
}