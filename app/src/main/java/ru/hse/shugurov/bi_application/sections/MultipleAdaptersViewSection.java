package ru.hse.shugurov.bi_application.sections;

import android.widget.ListAdapter;

/**
 * Created by Иван on 18.01.14.
 */
public class MultipleAdaptersViewSection extends Section
{
    private transient ListAdapter[] adapters;
    private String url;
    private int currentState = 0;
    private int numberOfAdapters;


    public MultipleAdaptersViewSection(String title, int iconDefault, int iconSelected, int type, int numberOfAdapters, String url)
    {
        super(title, iconDefault, iconSelected, type);
        this.url = url;
        this.numberOfAdapters = numberOfAdapters;
        checkAdapters();
    }

    public String getUrl()
    {
        return url;
    }

    public ListAdapter getAdapter(int index)
    {
        checkAdapters();
        return adapters[index];
    }

    public void setAdapter(ListAdapter adapter, int index)
    {
        checkAdapters();
        adapters[index] = adapter;
    }

    public int getNumberOfAdapters()
    {
        return numberOfAdapters;
    }

    public int getCurrentState()
    {
        return currentState;
    }

    public void setCurrentState(int currentState)
    {
        this.currentState = currentState;
    }

    private void checkAdapters()
    {
        if (adapters == null)
        {
            adapters = new ListAdapter[numberOfAdapters];
            for (int i = 0; i < numberOfAdapters; i++)
            {
                adapters[i] = null;
            }
        }
    }
}
