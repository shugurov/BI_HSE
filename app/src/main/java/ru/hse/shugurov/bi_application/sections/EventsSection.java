package ru.hse.shugurov.bi_application.sections;

import android.widget.ListAdapter;


/**
 * Created by Иван on 11.01.14.
 */
public class EventsSection extends Section
{
    private String[] urls;
    private transient ListAdapter announcesAdapter;
    private transient ListAdapter archiveAdapter;
    private EventScreenState state;

    public EventsSection(String title, int iconDefault, int iconSelected, String[] urls, int type)
    {
        super(title, iconDefault, iconSelected, type);
        if (urls.length != 3)
        {
            throw new IllegalArgumentException("Precondition violated in EventScreen." + " Incorrect number of urls.");
        }
        this.urls = urls;
        state = EventScreenState.ANNOUNCES;
    }


    public String getAnnouncesURL()
    {
        return urls[0];
    }

    public String getCalendarURL()
    {
        return urls[1];
    }

    public String getArchiveURL()
    {
        return urls[2];
    }


    public EventScreenState getCurrentState()
    {
        return state;
    }

    public void setCurrentState(EventScreenState currentState)
    {
        this.state = currentState;
    }

    public ListAdapter getAnnouncesAdapter()
    {
        return announcesAdapter;
    }

    public void setAnnouncesAdapter(ListAdapter announcesAdapter)
    {
        this.announcesAdapter = announcesAdapter;
    }

    public ListAdapter getArchiveAdapter()
    {
        return archiveAdapter;
    }

    public void setArchiveAdapter(ListAdapter archiveAdapter)
    {
        this.archiveAdapter = archiveAdapter;
    }

    public enum EventScreenState
    {
        ANNOUNCES, CALENDAR, ARCHIVE;
    }
}
