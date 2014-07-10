package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;

import ru.hse.shugurov.bi_application.Downloader;
import ru.hse.shugurov.bi_application.FileManager;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.adapters.AdvertAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.AdvertItemFragment;
import ru.hse.shugurov.bi_application.model.AdvertItem;
import ru.hse.shugurov.bi_application.model.Parser;
import ru.hse.shugurov.bi_application.sections.MultipleAdaptersViewSection;

/**
 * Created by Иван on 13.01.14.
 */
public class BillboardFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener //TODO я сразу качаю или вначале проверяю кэш?
{//TODO при перевороте падает
    private LinearLayout root;
    private View currentView;
    private ImageView bs1Button;
    private ImageView bs2Button;
    private ImageView bs3Button;
    private ImageView bs4Button;
    private ImageView ms1Button;
    private ImageView ms2Button;
    private SwipeRefreshLayout refreshLayout;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View entireView = inflater.inflate(R.layout.billboard, container, false);
        root = (LinearLayout) entireView.findViewById(R.id.billboard_container);
        if (refreshLayout != null)
        {
            refreshLayout = null;
        }
        setUpButtons(entireView);
        int drawableId = -1;
        ImageView selectedButton = null;
        switch (getSection().getCurrentState())
        {
            case 0:
                selectedButton = bs1Button;
                drawableId = R.drawable.course1_pressed;
                if (getSection().getAdapter(0) == null)//TODO делаю в том же потоке(
                {
                    FileManager fileManager = FileManager.instance();
                    try
                    {
                        String value = fileManager.getFileContent(getSection().getTitle());
                        if (value != null)
                        {
                            setAdapters(value, inflater);
                            break;
                        }
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    loadContent(inflater);
                } else
                {
                    setAdapter(inflater, 0);
                }
                break;
            case 1:
                selectedButton = bs2Button;
                drawableId = R.drawable.course2_pressed;
                setAdapter(inflater, 1);
                break;
            case 2:
                selectedButton = bs3Button;
                drawableId = R.drawable.course3_pressed;
                setAdapter(inflater, 2);
                break;
            case 3:
                selectedButton = bs4Button;
                drawableId = R.drawable.course4_pressed;
                setAdapter(inflater, 3);
                break;
            case 4:
                selectedButton = ms1Button;
                drawableId = R.drawable.course1_pressed;
                setAdapter(inflater, 4);
                break;
            case 5:
                selectedButton = ms2Button;
                drawableId = R.drawable.course2_pressed;
                setAdapter(inflater, 5);
                break;
        }
        if (selectedButton != null || drawableId > 0)
        {
            Drawable drawable = getResources().getDrawable(drawableId);
            selectedButton.setImageDrawable(drawable);
        }
        return entireView;
    }

    @Override
    public void onClick(View view)//TODO слишком много instanceof
    {
        MultipleAdaptersViewSection section = getSection();
        switch (view.getId())
        {
            case R.id.bs1:
                if (section.getCurrentState() != 0)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.bs1)).setImageDrawable(getResources().getDrawable(R.drawable.course1_pressed));
                    section.setCurrentState(0);
                    if (!(currentView instanceof ProgressBar))
                    {
                        ((ListView) currentView).setAdapter(section.getAdapter(0));
                    }
                }
                break;
            case R.id.bs2:
                if (section.getCurrentState() != 1)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.bs2)).setImageDrawable(getResources().getDrawable(R.drawable.course2_pressed));
                    section.setCurrentState(1);
                    if (!(currentView instanceof ProgressBar))
                    {
                        ((ListView) currentView).setAdapter(section.getAdapter(1));
                    }
                }
                break;
            case R.id.bs3:
                if (section.getCurrentState() != 2)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.bs3)).setImageDrawable(getResources().getDrawable(R.drawable.course3_pressed));
                    section.setCurrentState(2);
                    if (!(currentView instanceof ProgressBar))
                    {
                        ((ListView) currentView).setAdapter(section.getAdapter(2));
                    }
                }
                break;
            case R.id.bs4:
                if (section.getCurrentState() != 3)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.bs4)).setImageDrawable(getResources().getDrawable(R.drawable.course4_pressed));
                    section.setCurrentState(3);
                    if (!(currentView instanceof ProgressBar))
                    {
                        ((ListView) currentView).setAdapter(section.getAdapter(3));
                    }
                }
                break;
            case R.id.ms1:
                if (section.getCurrentState() != 4)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.ms1)).setImageDrawable(getResources().getDrawable(R.drawable.course1_pressed));
                    section.setCurrentState(4);
                    if (!(currentView instanceof ProgressBar))
                    {
                        ((ListView) currentView).setAdapter(section.getAdapter(4));
                    }
                }
                break;
            case R.id.ms2:
                if (section.getCurrentState() != 5)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.ms2)).setImageDrawable(getResources().getDrawable(R.drawable.course2_pressed));
                    section.setCurrentState(5);
                    if (!(currentView instanceof ProgressBar))
                    {
                        ((ListView) currentView).setAdapter(section.getAdapter(5));
                    }
                }
                break;
        }
    }

    private void releaseButton()
    {
        MultipleAdaptersViewSection section = getSection();
        ImageView releasedButton = null;
        int drawableId = -1;
        switch (section.getCurrentState())
        {
            case 0:
                releasedButton = bs1Button;
                drawableId = R.drawable.course1;
                break;
            case 1:
                releasedButton = bs2Button;
                drawableId = R.drawable.course2;
                break;
            case 2:
                releasedButton = bs3Button;
                drawableId = R.drawable.course3;
                break;
            case 3:
                releasedButton = bs4Button;
                drawableId = R.drawable.course4;
                break;
            case 4:
                releasedButton = ms1Button;
                drawableId = R.drawable.course1;
                break;
            case 5:
                releasedButton = ms2Button;
                drawableId = R.drawable.course2;
                break;
        }
        if (releasedButton != null && drawableId >= 0)
        {
            Resources resources = getResources();
            Drawable drawable = resources.getDrawable(drawableId);
            releasedButton.setImageDrawable(drawable);
        }

    }

    private void setAdapter(final LayoutInflater inflater, int index)
    {
        if (refreshLayout == null)
        {
            refreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.list, root, false);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    loadContent(inflater);
                }
            });
            refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);
            root.addView(refreshLayout);
        }
        currentView = refreshLayout.findViewById(R.id.list);
        ((ListView) currentView).setAdapter(getSection().getAdapter(index));
        ((ListView) currentView).setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        MultipleAdaptersViewSection currentSection = getSection();
        AdvertAdapter adapter = (AdvertAdapter) currentSection.getAdapter(currentSection.getCurrentState());
        AdvertItemFragment advertItemPlaceholderFragment = new AdvertItemFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(AdvertItemFragment.ADVERT_ITEM_TAG, adapter.getItem(position));
        arguments.putSerializable(AdvertItemFragment.SECTION_TAG, getSection());
        advertItemPlaceholderFragment.setArguments(arguments);
        showNextFragment(advertItemPlaceholderFragment);
    }

    private void setAdapters(String result, final LayoutInflater inflater)
    {
        AdvertItem[] advertItems = Parser.parseAdverts(result);
        ArrayList<AdvertItem> bs1 = new ArrayList<AdvertItem>();
        ArrayList<AdvertItem> bs2 = new ArrayList<AdvertItem>();
        ArrayList<AdvertItem> bs3 = new ArrayList<AdvertItem>();
        ArrayList<AdvertItem> bs4 = new ArrayList<AdvertItem>();
        ArrayList<AdvertItem> ms1 = new ArrayList<AdvertItem>();
        ArrayList<AdvertItem> ms2 = new ArrayList<AdvertItem>();

        for (AdvertItem advert : advertItems)
        {
            switch (advert.getCourse())
            {
                case 1:
                    bs1.add(advert);
                    break;
                case 2:
                    bs2.add(advert);
                    break;
                case 3:
                    bs3.add(advert);
                    break;
                case 4:
                    bs4.add(advert);
                    break;
                case 5:
                    ms1.add(advert);
                    break;
                case 6:
                    ms2.add(advert);
                    break;
            }
        }
        MultipleAdaptersViewSection section = getSection();
        section.setAdapter(new AdvertAdapter(getActivity(), bs1), 0);
        section.setAdapter(new AdvertAdapter(getActivity(), bs2), 1);
        section.setAdapter(new AdvertAdapter(getActivity(), bs3), 2);
        section.setAdapter(new AdvertAdapter(getActivity(), bs4), 3);
        section.setAdapter(new AdvertAdapter(getActivity(), ms1), 4);
        section.setAdapter(new AdvertAdapter(getActivity(), ms2), 5);
        if (isAdded())
        {
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    root.removeView(currentView);
                    setAdapter(inflater, 0);
                }
            });
        }
    }


    private void setUpButtons(View entireView)
    {
        bs1Button = (ImageView) entireView.findViewById(R.id.bs1);
        bs1Button.setOnClickListener(this);
        bs2Button = (ImageView) entireView.findViewById(R.id.bs2);
        bs2Button.setOnClickListener(this);
        bs3Button = (ImageView) entireView.findViewById(R.id.bs3);
        bs3Button.setOnClickListener(this);
        bs4Button = (ImageView) entireView.findViewById(R.id.bs4);
        bs4Button.setOnClickListener(this);
        ms1Button = (ImageView) entireView.findViewById(R.id.ms1);
        ms1Button.setOnClickListener(this);
        ms2Button = (ImageView) entireView.findViewById(R.id.ms2);
        ms2Button.setOnClickListener(this);
    }


    private void loadContent(final LayoutInflater inflater)
    {
        if (refreshLayout == null)
        {
            currentView = inflater.inflate(R.layout.progress, root, false);
            root.addView(currentView);
        }

        Downloader downloader = new Downloader(new Downloader.RequestResultCallback()
        {
            @Override
            public void pushResult(String result)
            {
                if (isAdded())
                {
                    if (refreshLayout != null)
                    {
                        refreshLayout.setRefreshing(false);
                    }
                    if (result == null)
                    {
                        handleLoadProblem();
                    } else
                    {
                        FileManager manager = FileManager.instance();
                        manager.writeToFile(getSection().getTitle(), result);
                        setAdapters(result, inflater);
                    }
                }
            }
        });
        downloader.execute(getSection().getUrl());
    }

    @Override
    public MultipleAdaptersViewSection getSection()
    {
        return (MultipleAdaptersViewSection) super.getSection();
    }
}
