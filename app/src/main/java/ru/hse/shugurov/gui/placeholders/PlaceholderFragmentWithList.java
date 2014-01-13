package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.model.Contact;
import ru.hse.shugurov.model.NewsItem;
import ru.hse.shugurov.model.ProjectItem;
import ru.hse.shugurov.model.Section;

/**
 * Created by Иван on 29.12.13.
 */
public class PlaceholderFragmentWithList extends PlaceholderFragment
{
    private Context context;
    private ListView listView;
    private ListAdapter adapter;


    public PlaceholderFragmentWithList(Context context, MainActivity.FragmentChanged fragmentChanged, Section section, int sectionNumber)
    {
        super(context, fragmentChanged, section, sectionNumber);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        listView = (ListView) inflater.inflate(ru.hse.shugurov.R.layout.fragment_list, container, false);
        if (adapter != null)
        {
            listView.setAdapter(adapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Object item = adapter.getItem(position);
                if (item instanceof NewsItem)
                {
                    NewsItemPlaceholderFragment newsItemPlaceholderFragment = new NewsItemPlaceholderFragment(context, (NewsItem) item, getFragmentChanged(), getSection(), getSectionNumber());
                    newsItemPlaceholderFragment.setAdapter(adapter);
                    getFragmentManager().beginTransaction().replace(R.id.container, newsItemPlaceholderFragment).commit();
                } else
                {
                    if (item instanceof ProjectItem)
                    {
                        ProjectItemPlaceholderFragment projectItemPlaceholderFragment = new ProjectItemPlaceholderFragment(getContext(), (ProjectItem) item, getFragmentChanged(), getSection(), getSectionNumber());
                        projectItemPlaceholderFragment.setAdapter(adapter);
                        getFragmentManager().beginTransaction().replace(R.id.container, projectItemPlaceholderFragment).commit();
                    } else
                    {
                        if (item instanceof Contact)
                        {
                            ContactPlaceholderFragment contactPlaceholderFragment = new ContactPlaceholderFragment(context, (Contact) item, getFragmentChanged(), getSection(), getSectionNumber());
                            getFragmentManager().beginTransaction().replace(R.id.container, contactPlaceholderFragment).commit();
                        } else
                        {
                            getFragmentManager().beginTransaction().replace(R.id.container, new PlaceholderFragment(getContext(), getFragmentChanged(), getSection(), getSectionNumber())).commit();
                        }
                    }
                }
            }
        });
        return listView;
    }

    public void setAdapter(ListAdapter adapter)
    {
        if (listView != null)
        {
            this.adapter = adapter;
            listView.setAdapter(adapter);
        } else
        {
            this.adapter = adapter;
        }
    }
}
