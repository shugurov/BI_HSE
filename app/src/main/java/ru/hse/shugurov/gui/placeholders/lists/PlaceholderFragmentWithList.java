package ru.hse.shugurov.gui.placeholders.lists;

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
import ru.hse.shugurov.FileCache;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.adapters.ContactAdapter;
import ru.hse.shugurov.gui.adapters.NewsAdapter;
import ru.hse.shugurov.gui.adapters.ProjectAdapter;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.items.ContactItemPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.items.NewsItemPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.items.ProjectItemPlaceholderFragment;
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
    private LinearLayout rootView;
    private ListView listView;
    private View progressDialog;
    private ViewGroup container;

    public PlaceholderFragmentWithList(MainActivity.FragmentListener fragmentListener, Section section)
    {
        super(fragmentListener, section);
        if (!(section instanceof SingleViewSection))
        {
            throw new IllegalArgumentException("precondition violated in PlaceHolderWithList. SingleViewSection is supposed to be hear");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        this.container = container;
        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_list, container, false);
        ListAdapter adapter = ((SingleViewSection) getSection()).getAdapter();
        if (adapter != null)
        {
            if (getSection().getType() == ContentTypes.NEWS)
            {
                SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
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
            setListener();
        } else
        {
            progressDialog = inflater.inflate(R.layout.progress, rootView, false);
            rootView.addView(progressDialog);
            Runnable loadContent = new Runnable()
            {
                @Override
                public void run()
                {
                    Downloader downloader;
                    final FileCache fileCache = FileCache.instance();
                    if (fileCache != null)
                    {
                        final String data = fileCache.get(getSection().getTitle());
                        if (data == null)
                        {
                            downloader = new Downloader(new CallBack()
                            {
                                @Override
                                public void call(String[] results)
                                {
                                    if (results != null && results[0] != null)
                                    {
                                        fileCache.add(getSection().getTitle(), results[0]);
                                        fillList(results[0]);
                                    } else
                                    {
                                        Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            if (getSection().getType() == ContentTypes.NEWS)
                            {
                                String filter = getFilter();
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
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
                        } else
                        {
                            fillList(data);
                        }
                    }

                }
            };
            new Thread(loadContent).start();
        }

        return rootView;
    }

    private void setListener()
    {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Object item = ((SingleViewSection) getSection()).getAdapter().getItem(position);
                if (item instanceof NewsItem)
                {
                    NewsItemPlaceholderFragment newsItemPlaceholderFragment = new NewsItemPlaceholderFragment(getActivity(), (NewsItem) item, getFragmentListener(), getSection());
                    getFragmentManager().beginTransaction().replace(R.id.container, newsItemPlaceholderFragment).commit();
                } else
                {
                    if (item instanceof ProjectItem)
                    {
                        ProjectItemPlaceholderFragment projectItemPlaceholderFragment = new ProjectItemPlaceholderFragment((ProjectItem) item, getFragmentListener(), getSection());
                        getFragmentManager().beginTransaction().replace(R.id.container, projectItemPlaceholderFragment).commit();
                    } else
                    {
                        if (item instanceof ContactItem)
                        {
                            ContactItemPlaceholderFragment contactItemPlaceholderFragment = new ContactItemPlaceholderFragment((ContactItem) item, getFragmentListener(), getSection());
                            getFragmentManager().beginTransaction().replace(R.id.container, contactItemPlaceholderFragment).commit();
                        }
                    }
                }
            }
        });
    }

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

    /*have to be run on GUI thread*/
    private void setAdapterInsteadProgressDialog(final ListAdapter adapter)
    {
        listView.setAdapter(adapter);
        rootView.removeView(progressDialog);
        rootView.addView(listView);
        progressDialog = null;
    }

    private void fillList(String data)
    {
        final ListAdapter adapter;
        switch (getSection().getType())
        {
            case ContentTypes.NEWS:
                NewsItem[] newsItems = Parser.parseNews(data);
                adapter = new NewsAdapter(getActivity(), newsItems);
                break;
            case ContentTypes.PROJECTS_VOLUNTEERING:
                ProjectItem[] projectItems = Parser.parseProjects(data);
                adapter = new ProjectAdapter(getActivity(), projectItems);
                break;
            case ContentTypes.CONTACTS:
            case ContentTypes.TEACHERS:
                ContactItem[] contactItems = Parser.parseContacts(data);
                adapter = new ContactAdapter(getActivity(), contactItems);

                break;
            default:
                return;
        }
        ((SingleViewSection) getSection()).setAdapter(adapter);
        Runnable listCreation = new Runnable()
        {
            @Override
            public void run()
            {
                listView = (ListView) getLayoutInflater(getArguments()).inflate(R.layout.list, container, false);
                setListener();
                setAdapterInsteadProgressDialog(adapter);
            }
        };
        getActivity().runOnUiThread(listCreation);
    }
}
