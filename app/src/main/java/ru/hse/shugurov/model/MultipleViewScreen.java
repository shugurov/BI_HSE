package ru.hse.shugurov.model;

import android.widget.ListAdapter;

/**
 * Created by Иван on 11.01.14.
 */
public class MultipleViewScreen extends Section
{
    private final ListAdapter[] adapters;
    private final String[] urls;
    private int currentState = 0;

    public MultipleViewScreen(String title, int iconDefault, int iconSelected, String[] urls, int type)
    {
        super(title, iconDefault, iconSelected, type);
        this.urls = urls;
        adapters = new ListAdapter[urls.length];
        for (int i = 0; i < adapters.length; i++)
        {
            adapters[i] = null;
        }
    }

    public void setAdapter(int index, ListAdapter adapter)
    {
        adapters[index] = adapter;
    }

    public int getAdaptersNumber()
    {
        return adapters.length;
    }

    public ListAdapter getAdapter(int index)
    {
        return adapters[index];
    }

    public int getUrlsNumber()
    {
        return urls.length;
    }

    public String getUrl(int index)
    {
        return urls[index];
    }

    public int getCurrentState()
    {
        return currentState;
    }

    public void setCurrentState(int currentState)
    {
        this.currentState = currentState;
    }

}
