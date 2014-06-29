package ru.hse.shugurov.bi_application.gui.fragments.lists;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import ru.hse.shugurov.bi_application.gui.adapters.NewsAdapter;
import ru.hse.shugurov.bi_application.model.Parser;
import ru.hse.shugurov.bi_application.sections.EventsSection;


/**
 * Created by Иван on 29.06.2014.
 */
public class AnnouncesFragment extends FragmentWithList
{
    private static final String FILE_CACHE_NAME = "announces";

    @Override
    protected String getFileCacheName()
    {
        return FILE_CACHE_NAME;
    }

    @Override
    protected String getDataUrl()
    {
        return getSection().getAnnouncesURL();
    }

    @Override
    protected ListAdapter getCurrentAdapter()
    {
        return getSection().getAnnouncesAdapter();
    }

    @Override
    protected ListAdapter getAdapter(String data)
    {
        NewsAdapter adapter = new NewsAdapter(getActivity(), Parser.parseNews(data));
        return adapter;
    }

    @Override
    protected void setSectionAdapter(ListAdapter adapter)
    {
        getSection().setAnnouncesAdapter(adapter);
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
