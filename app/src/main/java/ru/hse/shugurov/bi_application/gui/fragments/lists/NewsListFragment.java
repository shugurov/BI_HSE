package ru.hse.shugurov.bi_application.gui.fragments.lists;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import ru.hse.shugurov.bi_application.ContentTypes;
import ru.hse.shugurov.bi_application.gui.adapters.NewsAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.NewsItemFragment;
import ru.hse.shugurov.bi_application.model.NewsItem;
import ru.hse.shugurov.bi_application.model.Parser;

/**
 * Created by Иван on 14.06.2014.
 */
public class NewsListFragment extends FragmentWithList
{

    private String getFilter()
    {
        String filter = "&filter=";
        SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (preferences.getBoolean("enrolee", false))
        {
            filter += "1";
        }
        if (preferences.getBoolean("bs", false))
        {
            filter += "2";
        }
        if (preferences.getBoolean("ms_enrolee", false))
        {
            filter += "3";
        }
        if (preferences.getBoolean("ms", false))
        {
            filter += "4";
        }
        return filter;
    }

    private void writePreferences(String filter)
    {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
        editor.putString("filter", filter);
        editor.commit();
    }

    @Override
    protected String getDataUrl()
    {
        String url;
        if (getSection().getType() == ContentTypes.NEWS)
        {
            String filter = getFilter();
            writePreferences(filter);

            if (filter.length() == 8)
            {
                url = getSection().getUrl();
            } else
            {
                url = getSection().getUrl() + filter;
            }
        } else
        {
            url = getSection().getUrl();
        }
        return url;
    }

    @Override
    protected ListAdapter getCurrentAdapter()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        String existedFilter = preferences.getString("filter", "&filter=");
        String filter = getFilter();
        if (!existedFilter.equals(filter))
        {
            writePreferences(filter);
            getSection().setAdapter(null);
        }
        return getSection().getAdapter();
    }

    @Override
    protected ListAdapter getAdapter(String data)
    {
        NewsItem[] newsItems = Parser.parseNews(data);
        return new NewsAdapter(getActivity(), newsItems);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        BaseFragment newsFragment = new NewsItemFragment();
        NewsItem item = getSelectedItem(parent, position);
        Bundle arguments = new Bundle();
        arguments.putSerializable(NewsItemFragment.ITEM_TAG, item);
        arguments.putSerializable(BaseFragment.SECTION_TAG, getSection());
        newsFragment.setArguments(arguments);
        showNextFragment(newsFragment);
    }
}
