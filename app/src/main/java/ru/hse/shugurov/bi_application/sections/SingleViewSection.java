package ru.hse.shugurov.bi_application.sections;

import android.widget.ListAdapter;

/**
 * Created by Иван on 11.01.14.
 */
public class SingleViewSection extends Section
{
    private transient ListAdapter adapter;
    private final String url;

    public SingleViewSection(String title, int iconDefault, int iconSelected, String url, int type)
    {
        super(title, iconDefault, iconSelected, type);
        this.url = url;
    }

    public ListAdapter getAdapter()
    {
        return adapter;
    }

    public void setAdapter(ListAdapter adapter)
    {
        this.adapter = adapter;
    }

    public String getUrl()
    {
        return url;
    }
}
