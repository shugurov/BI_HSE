package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import ru.hse.shugurov.CallBack;
import ru.hse.shugurov.ContentTypes;
import ru.hse.shugurov.Downloader;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.adapters.ContactAdapter;
import ru.hse.shugurov.gui.adapters.NewsAdapter;
import ru.hse.shugurov.gui.adapters.ProjectAdapter;
import ru.hse.shugurov.model.ContactItem;
import ru.hse.shugurov.model.NewsItem;
import ru.hse.shugurov.model.Parser;
import ru.hse.shugurov.model.ProjectItem;
import ru.hse.shugurov.sections.Section;
import ru.hse.shugurov.sections.SingleViewSection;

/**
 * Created by Иван on 29.12.13.
 */
public class PlaceholderFragmentWithList extends PlaceholderFragment
{
    private Context context;
    private LinearLayout rootView;
    private ListView listView;
    private View progressDialog;

    public PlaceholderFragmentWithList(Context context, MainActivity.FragmentListener fragmentListener, Section section)
    {
        super(context, fragmentListener, section);
        if (!(section instanceof SingleViewSection))
        {
            throw new IllegalArgumentException("precondition violated in PlaceHolderWithList. SingleViewSection is supposed to be hear");
        }
        this.context = context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final Downloader downloader;
        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_list, container, false);
        ListAdapter adapter = ((SingleViewSection) getSection()).getAdapter();
        if (adapter != null)
        {
            if (getSection().getType() == ContentTypes.NEWS)
            {
                SharedPreferences preferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                String existedFilter = preferences.getString("filter", "&filter=");
                String filter = getFilter();
                if (!existedFilter.equals(filter))
                {
                    ((SingleViewSection) getSection()).setAdapter(null);
                    return onCreateView(inflater, container, savedInstanceState);
                }
            }
            listView = (ListView) ((ViewStub) rootView.findViewById(R.id.fragment_list_stub)).inflate();
            listView.setAdapter(adapter);
            selListener();
        } else
        {
            progressDialog = inflater.inflate(R.layout.progress, rootView, false);
            rootView.addView(progressDialog, 0);
            downloader = new Downloader(new CallBack()
            {
                @Override
                public void call(String[] results)
                {
                    if (results != null)
                    {
                        rootView.removeView(progressDialog);
                        listView = (ListView) ((ViewStub) rootView.findViewById(R.id.fragment_list_stub)).inflate();
                        selListener();
                        switch (getSection().getType())
                        {
                            case ContentTypes.NEWS:
                                NewsItem[] newsItems = Parser.parseNews(results[0]);
                                NewsAdapter newsAdapter = new NewsAdapter(getContext(), newsItems);
                                ((SingleViewSection) getSection()).setAdapter(newsAdapter);
                                listView.setAdapter(newsAdapter);
                                break;
                            case ContentTypes.PROJECTS_VOLUNTEERING:
                                ProjectItem[] projectItems = Parser.parseProjects(results[0]);
                                ProjectAdapter projectAdapter = new ProjectAdapter(getContext(), projectItems);
                                ((SingleViewSection) getSection()).setAdapter(projectAdapter);
                                listView.setAdapter(projectAdapter);
                                break;
                            case ContentTypes.CONTACTS:
                            case ContentTypes.TEACHERS:
                                ContactItem[] contactItems = Parser.parseContacts(results[0]);
                                ContactAdapter contactAdapter = new ContactAdapter(getContext(), contactItems);
                                ((SingleViewSection) getSection()).setAdapter(contactAdapter);
                                listView.setAdapter(contactAdapter);
                                break;
                        }
                    } else
                    {
                        Toast.makeText(getContext(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (getSection().getType() == ContentTypes.NEWS)
            {
                String filter = getFilter();
                SharedPreferences.Editor editor = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
                editor.putString("filter", filter);
                editor.commit();
                if (filter.length() == 8)
                {
                    downloader.execute(((SingleViewSection) getSection()).getUrl());
                } else
                {
                    downloader.execute(((SingleViewSection) getSection()).getUrl() + filter);
                }
            } else
            {
                downloader.execute(((SingleViewSection) getSection()).getUrl());
            }
        }

        return rootView;
    }

    private void selListener()
    {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Object item = ((SingleViewSection) getSection()).getAdapter().getItem(position);
                if (item instanceof NewsItem)
                {
                    NewsItemPlaceholderFragment newsItemPlaceholderFragment = new NewsItemPlaceholderFragment(context, (NewsItem) item, getFragmentListener(), getSection());
                    getFragmentManager().beginTransaction().replace(R.id.container, newsItemPlaceholderFragment).commit();
                } else
                {
                    if (item instanceof ProjectItem)
                    {
                        ProjectItemPlaceholderFragment projectItemPlaceholderFragment = new ProjectItemPlaceholderFragment(getContext(), (ProjectItem) item, getFragmentListener(), getSection());
                        getFragmentManager().beginTransaction().replace(R.id.container, projectItemPlaceholderFragment).commit();
                    } else
                    {
                        if (item instanceof ContactItem)
                        {
                            ContactItemPlaceholderFragment contactItemPlaceholderFragment = new ContactItemPlaceholderFragment(context, (ContactItem) item, getFragmentListener(), getSection());
                            getFragmentManager().beginTransaction().replace(R.id.container, contactItemPlaceholderFragment).commit();
                        } else
                        {
                            getFragmentManager().beginTransaction().replace(R.id.container, new PlaceholderFragment(getContext(), getFragmentListener(), getSection())).commit();
                        }
                    }
                }
            }
        });
    }

    private String getFilter()
    {
        String filter = "&filter=";
        SharedPreferences preferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
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
}
