package ru.hse.shugurov.bi_application.gui.fragments.lists;

import android.os.Bundle;
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
    private ViewGroup container;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();//TODO fix when a progress bar is shown
        super.onCreateView(inflater, container, savedInstanceState);
        this.container = container;
        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_list, container, false);
        ListAdapter adapter = getCurrentAdapter();
        if (adapter != null)
        {
            listView = (ListView) ((ViewStub) rootView.findViewById(R.id.fragment_list_stub)).inflate();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
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

    /*have to be run on GUI thread*/
    protected void setAdapterInsteadProgressDialog(final ListAdapter adapter)
    {
        listView.setAdapter(adapter);
        rootView.removeView(progressDialog);
        rootView.addView(listView);
        progressDialog = null;
    }

    protected abstract ListAdapter getAdapter(String data);

    protected abstract void setSectionAdapter(ListAdapter adapter);

    private void fillList(String data)
    {
        if (isAdded())
        {
            final ListAdapter adapter = getAdapter(data);
            //TODO что делать, если adapter == null
            setSectionAdapter(adapter);
            if (isAdded())
            {
                Runnable listCreation = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        listView = (ListView) getLayoutInflater(getArguments()).inflate(R.layout.list, container, false);
                        listView.setOnItemClickListener(FragmentWithList.this);
                        setAdapterInsteadProgressDialog(adapter);
                    }
                };
                getActivity().runOnUiThread(listCreation);
            }
        }
    }

    protected <T> T getSelectedItem(AdapterView<?> adapterView, int position)
    {
        return (T) adapterView.getItemAtPosition(position);
    }
}
