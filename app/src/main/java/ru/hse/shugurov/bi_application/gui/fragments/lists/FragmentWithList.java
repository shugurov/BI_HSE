package ru.hse.shugurov.bi_application.gui.fragments.lists;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;

import ru.hse.shugurov.bi_application.Downloader;
import ru.hse.shugurov.bi_application.FileManager;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;

/**
 * Created by Иван on 29.12.13.
 */
public abstract class FragmentWithList extends BaseFragment implements AdapterView.OnItemClickListener
{
    private LinearLayout rootView;
    private ListView listView;
    private View progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isJustCreated;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        isJustCreated = true;
        container.removeAllViews();
        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_list, container, false);
        ListAdapter adapter = getCurrentAdapter();
        if (adapter != null)
        {
            inflateListView(adapter);
        } else
        {
            progressDialog = inflater.inflate(R.layout.progress, rootView, false);
            rootView.addView(progressDialog);
            Runnable loadContent = new Runnable()
            {
                @Override
                public void run()
                {

                    final FileManager fileManager = FileManager.instance();
                    final String data;
                    try
                    {
                        data = fileManager.getFileContent(getFileCacheName());
                        if (data == null)
                        {
                            requestData(getDataUrl());
                        } else
                        {
                            fillList(data);
                        }
                    } catch (IOException e)
                    {
                        requestData(getDataUrl());
                    }


                }
            };
            new Thread(loadContent).start();
        }

        return rootView;
    }

    private void inflateListView(ListAdapter adapter)
    {
        if (listView == null || isJustCreated)
        {
            rootView.removeView(progressDialog);
            swipeRefreshLayout = (SwipeRefreshLayout) ((ViewStub) rootView.findViewById(R.id.fragment_list_stub)).inflate();
            swipeRefreshLayout.setEnabled(isPullToRefreshAvailable());
            swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    requestData(getDataUrl());
                }
            });
            listView = (ListView) swipeRefreshLayout.findViewById(R.id.list);
            listView.setOnItemClickListener(this);
            isJustCreated = false;
        } else
        {
            swipeRefreshLayout.setRefreshing(false);
        }
        listView.setAdapter(adapter);
    }

    protected String getFileCacheName()
    {
        return getSection().getTitle();
    }

    protected abstract String getDataUrl();

    protected void requestData(String url)
    {
        Downloader downloader = new Downloader(new Downloader.RequestResultCallback()
        {
            @Override
            public void pushResult(String result)
            {
                if (result == null)
                {
                    handleLoadProblem();
                } else
                {
                    FileManager fileManager = FileManager.instance();
                    fileManager.writeToFile(getFileCacheName(), result);
                    if (isAdded())
                    {
                        fillList(result);
                    }
                }
            }
        });
        downloader.execute(url);
    }

    protected abstract ListAdapter getCurrentAdapter();

    protected abstract ListAdapter getAdapter(String data);

    protected abstract void setSectionAdapter(ListAdapter adapter);

    private void fillList(String data)
    {
        if (isAdded())
        {
            final ListAdapter adapter = getAdapter(data);
            if (adapter == null)
            {
                return;
            }
            setSectionAdapter(adapter);
                Runnable listCreation = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        inflateListView(adapter);
                    }
                };
                getActivity().runOnUiThread(listCreation);
        }
    }

    protected <T> T getSelectedItem(AdapterView<?> adapterView, int position)
    {
        return (T) adapterView.getItemAtPosition(position);
    }

    @Override
    protected void handleLoadProblem()
    {
        super.handleLoadProblem();
        if (swipeRefreshLayout != null)
        {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    protected boolean isPullToRefreshAvailable()
    {
        return true;
    }
}
