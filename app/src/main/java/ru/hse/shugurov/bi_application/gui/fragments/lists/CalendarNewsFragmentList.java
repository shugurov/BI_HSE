package ru.hse.shugurov.bi_application.gui.fragments.lists;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.Arrays;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.adapters.NewsAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.NewsItemFragment;
import ru.hse.shugurov.bi_application.model.NewsItem;

/**
 * Created by Иван on 30.06.2014.
 */
public class CalendarNewsFragmentList extends FragmentWithList
{
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
    protected boolean isPullToRefreshAvailable()
    {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        BaseFragment newsFragment = new NewsItemFragment();
        NewsItem item = getSelectedItem(parent, position);
        Bundle arguments = new Bundle();
        arguments.putParcelable(NewsItemFragment.ITEM_TAG, item);
        arguments.putSerializable(BaseFragment.SECTION_TAG, getSection());
        newsFragment.setArguments(arguments);
        newsFragment.setBackStack(getBackStack());
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newsFragment);
        transaction.commit();
    }

    @Override
    protected void readStateFromBundle(Bundle args)
    {
        super.readStateFromBundle(args);
        Parcelable[] array = args.getParcelableArray(NEWS_ITEMS);
        items = Arrays.copyOf(array, array.length, NewsItem[].class);
    }
}
