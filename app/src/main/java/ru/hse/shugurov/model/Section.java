package ru.hse.shugurov.model;

import android.widget.ListAdapter;

import ru.hse.shugurov.ContentTypes;

/**
 * Created by Иван on 02.01.14.
 */
public class Section
{
    private String title;
    private int iconDefault;
    private int iconSelected;
    private String url;
    private int type;
    private ListAdapter[] adapters;

    public Section(String title, int iconDefault, int iconSelected, String url, int type)
    {
        this.title = title;
        this.iconDefault = iconDefault;
        this.iconSelected = iconSelected;
        this.url = url;
        this.type = type;
        switch (type)
        {
            case ContentTypes.NEWS:
            case ContentTypes.PROJECTS_VOLUNTEERING:
            case ContentTypes.CONTACTS:
                adapters = new ListAdapter[1];
                adapters[0] = null;
                break;
        }
    }

    public int getType()
    {
        return type;
    }

    public String getTitle()
    {
        return title;
    }

    public int getIconDefault()
    {
        return iconDefault;
    }

    public int getIconSelected()
    {
        return iconSelected;
    }

    public String getUrl()
    {
        return url;
    }

    public ListAdapter[] getAdapters()
    {
        return adapters;
    }

    public void setAdapter(int index, ListAdapter adapter)
    {
        adapters[index] = adapter;
    }
}
