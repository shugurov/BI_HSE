package ru.hse.shugurov.bi_application.gui.fragments.lists;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import ru.hse.shugurov.bi_application.gui.adapters.NewsAdapter;
import ru.hse.shugurov.bi_application.model.Parser;
import ru.hse.shugurov.bi_application.sections.EventsSection;

/**
 * Created by Иван on 29.06.2014.
 */
public class ArchiveFragment extends FragmentWithList
{
    private static final String FILE_CACHE_NAME = "archive";

    @Override
    protected String getFileCacheName()
    {
        return FILE_CACHE_NAME;
    }

    @Override
    protected String getDataUrl()
    {
        Log.d("archive", "data url is requested");
        Log.d("archive", "data url: " + getSection().getArchiveURL());
        return getSection().getArchiveURL();
    }

    @Override
    protected ListAdapter getCurrentAdapter()
    {
        Log.d("archive", "current adapter is requested");
        return getSection().getArchiveAdapter();
    }

    @Override
    protected ListAdapter getAdapter(String data)
    {
        Log.d("archive", "adapter creation is requested");
        ListAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(data));
        return adapter;
    }

    @Override
    protected void setSectionAdapter(ListAdapter adapter)
    {
        Log.d("archive", "section adapter is set");
        getSection().setArchiveAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        //TODO create method(
    }

    @Override
    public EventsSection getSection()
    {
        return (EventsSection) super.getSection();
    }
}
