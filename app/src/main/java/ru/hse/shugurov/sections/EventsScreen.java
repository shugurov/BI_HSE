package ru.hse.shugurov.sections;

import android.widget.ListAdapter;

import ru.hse.shugurov.gui.adapters.CalendarAdapter;

/**
 * Created by Иван on 11.01.14.
 */
public class EventsScreen extends Section
{
    private String[] urls;
    private ListAdapter announceAdapter;
    private CalendarAdapter calendarAdapter;
    private ListAdapter archiveAdapter;
    private EventScreenState state;

    public EventsScreen(String title, int iconDefault, int iconSelected, String[] urls, int type)
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

    public ListAdapter getAnnounceAdapter()
    {
        return announceAdapter;
    }

    public void setAnnounceAdapter(ListAdapter announceAdapter)
    {
        this.announceAdapter = announceAdapter;
    }

    public CalendarAdapter getCalendarAdapter()
    {
        return calendarAdapter;
    }

    public void setCalendarAdapter(CalendarAdapter calendarAdapter)
    {
        this.calendarAdapter = calendarAdapter;
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
