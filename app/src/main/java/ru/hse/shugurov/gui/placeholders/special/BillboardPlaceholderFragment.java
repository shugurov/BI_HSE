package ru.hse.shugurov.gui.placeholders.special;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import ru.hse.shugurov.CallBack;
import ru.hse.shugurov.Downloader;
import ru.hse.shugurov.FileCache;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.adapters.AdvertAdapter;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.items.AdvertItemPlaceholderFragment;
import ru.hse.shugurov.model.AdvertItem;
import ru.hse.shugurov.model.Parser;
import ru.hse.shugurov.sections.MultipleAdaptersViewSection;

/**
 * Created by Иван on 13.01.14.
 */
public class BillboardPlaceholderFragment extends PlaceholderFragment implements View.OnClickListener, AdapterView.OnItemClickListener
{
    private LinearLayout root;
    private View currentView;

    public BillboardPlaceholderFragment(Context context, MainActivity.FragmentListener fragmentListener, MultipleAdaptersViewSection section)//TODO а проверять ли правильность секции?
    {
        super(context, fragmentListener, section);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = (LinearLayout) inflater.inflate(R.layout.billboard, container, false);
        root.findViewById(R.id.bs1).setOnClickListener(this);
        root.findViewById(R.id.bs2).setOnClickListener(this);
        root.findViewById(R.id.bs3).setOnClickListener(this);
        root.findViewById(R.id.bs4).setOnClickListener(this);
        root.findViewById(R.id.ms1).setOnClickListener(this);
        root.findViewById(R.id.ms2).setOnClickListener(this);
        switch (((MultipleAdaptersViewSection) getSection()).getCurrentState())
        {
            case 0:
                ((ImageView) root.findViewById(R.id.bs1)).setImageDrawable(getResources().getDrawable(R.drawable.course1_pressed));
                if (((MultipleAdaptersViewSection) getSection()).getAdapter(0) != null)
                {
                    setAdapter(inflater, 0);
                } else
                {
                    final FileCache fileCache = FileCache.instance();
                    if (fileCache != null)
                    {
                        String value = fileCache.get(getSection().getTitle());
                        if (value == null)
                        {
                            Downloader downloader = new Downloader(new CallBack()//TODO скачиваю только в одном из случаев. может во всех проверять?
                            {
                                @Override
                                public void call(String[] results)
                                {
                                    if (results != null && results[0] != null)
                                    {
                                        fileCache.add(getSection().getTitle(), results[0]);//TODO далить этот позор
                                        setAdapters(results[0], inflater);
                                    } else
                                    {
                                        Toast.makeText(getContext(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            currentView = inflater.inflate(R.layout.progress, root, false);
                            root.addView(currentView, 0);
                            downloader.execute(((MultipleAdaptersViewSection) getSection()).getUrl());
                        } else
                        {
                            setAdapters(value, inflater);
                        }
                    }//TODO что делать, если FileCache не существует
                }
                break;
            case 1:
                ((ImageView) root.findViewById(R.id.bs2)).setImageDrawable(getResources().getDrawable(R.drawable.course2_pressed));
                setAdapter(inflater, 1);
                break;
            case 2:
                ((ImageView) root.findViewById(R.id.bs3)).setImageDrawable(getResources().getDrawable(R.drawable.course3_pressed));
                setAdapter(inflater, 2);
                break;
            case 3:
                ((ImageView) root.findViewById(R.id.bs4)).setImageDrawable(getResources().getDrawable(R.drawable.course4_pressed));
                setAdapter(inflater, 3);
                break;
            case 4:
                ((ImageView) root.findViewById(R.id.ms1)).setImageDrawable(getResources().getDrawable(R.drawable.course1_pressed));
                setAdapter(inflater, 4);
                break;
            case 5:
                ((ImageView) root.findViewById(R.id.ms2)).setImageDrawable(getResources().getDrawable(R.drawable.course2_pressed));
                setAdapter(inflater, 5);
                break;
        }
        return root;
    }

    @Override
    public void onClick(View view)
    {
        MultipleAdaptersViewSection section = (MultipleAdaptersViewSection) getSection();
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
        MultipleAdaptersViewSection section = (MultipleAdaptersViewSection) getSection();
        switch (section.getCurrentState())
        {
            case 0:
                ((ImageView) getView().findViewById(R.id.bs1)).setImageDrawable(getResources().getDrawable(R.drawable.course1));
                break;
            case 1:
                ((ImageView) getView().findViewById(R.id.bs2)).setImageDrawable(getResources().getDrawable(R.drawable.course2));
                break;
            case 2:
                ((ImageView) getView().findViewById(R.id.bs3)).setImageDrawable(getResources().getDrawable(R.drawable.course3));
                break;
            case 3:
                ((ImageView) getView().findViewById(R.id.bs4)).setImageDrawable(getResources().getDrawable(R.drawable.course4));
                break;
            case 4:
                ((ImageView) getView().findViewById(R.id.ms1)).setImageDrawable(getResources().getDrawable(R.drawable.course1));
                break;
            case 5:
                ((ImageView) getView().findViewById(R.id.ms2)).setImageDrawable(getResources().getDrawable(R.drawable.course2));
                break;
        }
    }

    private void setAdapter(LayoutInflater inflater, int index)
    {
        currentView = inflater.inflate(R.layout.list, root, false);
        ((ListView) currentView).setAdapter(((MultipleAdaptersViewSection) getSection()).getAdapter(index));
        ((ListView) currentView).setOnItemClickListener(this);
        root.addView(currentView, 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        MultipleAdaptersViewSection currentSection = (MultipleAdaptersViewSection) getSection();
        AdvertAdapter adapter = (AdvertAdapter) currentSection.getAdapter(currentSection.getCurrentState()); //TODO подумать про getCurrentAdapter()
        AdvertItemPlaceholderFragment advertItemPlaceholderFragment;
        advertItemPlaceholderFragment = new AdvertItemPlaceholderFragment(getContext(), getFragmentListener(), getSection(), (AdvertItem) adapter.getItem(position));
        getFragmentManager().beginTransaction().replace(R.id.container, advertItemPlaceholderFragment).commit();
    }

    private void setAdapters(String result, LayoutInflater inflater)//TODO а может не надо тут это делать?(
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
        MultipleAdaptersViewSection section = (MultipleAdaptersViewSection) getSection();
        section.setAdapter(new AdvertAdapter(getContext(), bs1), 0);
        section.setAdapter(new AdvertAdapter(getContext(), bs2), 1);
        section.setAdapter(new AdvertAdapter(getContext(), bs3), 2);
        section.setAdapter(new AdvertAdapter(getContext(), bs4), 3);
        section.setAdapter(new AdvertAdapter(getContext(), ms1), 4);
        section.setAdapter(new AdvertAdapter(getContext(), ms2), 5);
        root.removeView(currentView);
        setAdapter(inflater, 0);
    }
}
