package ru.hse.shugurov.bi_application.gui.fragments.lists;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.Arrays;

import ru.hse.shugurov.bi_application.gui.adapters.NewsAdapter;
import ru.hse.shugurov.bi_application.model.NewsItem;

/**
 * Created by Иван on 30.06.2014.
 */
public class CalendarNewsFragmentList extends FragmentWithList//TODO не могу переворачивать(
{//TODO упало при открытии одного из окон после ночи
    public static final String NEWS_ITEMS = "calendar news";
    private NewsItem[] items;

    @Override
    protected String getDataUrl()
    {
        return null;
    }

    @Override
    protected ListAdapter getCurrentAdapter()
    {
        return new NewsAdapter(getActivity(), items);
    }

    @Override
    protected ListAdapter getAdapter(String data)
    {
        return null;
    }

    @Override
    protected void setSectionAdapter(ListAdapter adapter)
    {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        //TODO create method
    }

    @Override
    protected void readStateFromBundle(Bundle args)
    {
        super.readStateFromBundle(args);
        Parcelable[] array = args.getParcelableArray(NEWS_ITEMS);
        items = Arrays.copyOf(array, array.length, NewsItem[].class);
    }
}
