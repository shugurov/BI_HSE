package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import ru.hse.shugurov.CallBack;
import ru.hse.shugurov.Downloader;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.adapters.AdvertAdapter;
import ru.hse.shugurov.model.Advert;
import ru.hse.shugurov.model.Parser;
import ru.hse.shugurov.sections.MultipleAdaptersViewSection;

/**
 * Created by Иван on 13.01.14.
 */
public class BillboardPlaceholderFragment extends PlaceholderFragment implements View.OnClickListener
{
    private LinearLayout root;
    private View currentView;

    public BillboardPlaceholderFragment(Context context, MainActivity.FragmentChanged fragmentChanged, MultipleAdaptersViewSection section, int sectionNumber)//TODO а проверять ли правильность секции?
    {
        super(context, fragmentChanged, section, sectionNumber);
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

                } else
                {
                    Downloader downloader = new Downloader(new CallBack()//TODO скачиваю только в одном из случаев. может во всех проверять?
                    {
                        @Override
                        public void call(String[] results)
                        {
                            if (results != null) //TODO что делать, если null?
                            {
                                Advert[] adverts = Parser.parseAdverts(results[0]);
                                ArrayList<Advert> bs1 = new ArrayList<Advert>();
                                ArrayList<Advert> bs2 = new ArrayList<Advert>();
                                ArrayList<Advert> bs3 = new ArrayList<Advert>();
                                ArrayList<Advert> bs4 = new ArrayList<Advert>();
                                ArrayList<Advert> ms1 = new ArrayList<Advert>();
                                ArrayList<Advert> ms2 = new ArrayList<Advert>();

                                for (Advert advert : adverts)
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
                                currentView = inflater.inflate(R.layout.list, root, false);
                                ((ListView) currentView).setAdapter(section.getAdapter(0));
                                root.addView(currentView, 0);
                            }
                        }
                    });
                    currentView = inflater.inflate(R.layout.progress, root, false);
                    root.addView(currentView, 0);
                    downloader.execute(((MultipleAdaptersViewSection) getSection()).getUrl());
                }
                break;
            case 1:
                ((ImageView) root.findViewById(R.id.bs2)).setImageDrawable(getResources().getDrawable(R.drawable.course2_pressed));
                break;
            case 2:
                ((ImageView) root.findViewById(R.id.bs3)).setImageDrawable(getResources().getDrawable(R.drawable.course3_pressed));
                break;
            case 3:
                ((ImageView) root.findViewById(R.id.bs4)).setImageDrawable(getResources().getDrawable(R.drawable.course4_pressed));
                break;
            case 4:
                ((ImageView) root.findViewById(R.id.ms1)).setImageDrawable(getResources().getDrawable(R.drawable.course1_pressed));
                break;
            case 5:
                ((ImageView) root.findViewById(R.id.ms2)).setImageDrawable(getResources().getDrawable(R.drawable.course2_pressed));
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
                    ((ListView) currentView).setAdapter(section.getAdapter(0));
                }
                break;
            case R.id.bs2:
                if (section.getCurrentState() != 1)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.bs2)).setImageDrawable(getResources().getDrawable(R.drawable.course2_pressed));
                    section.setCurrentState(1);
                    ((ListView) currentView).setAdapter(section.getAdapter(1));
                }
                break;
            case R.id.bs3:
                if (section.getCurrentState() != 2)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.bs3)).setImageDrawable(getResources().getDrawable(R.drawable.course3_pressed));
                    section.setCurrentState(2);
                    ((ListView) currentView).setAdapter(section.getAdapter(2));
                }
                break;
            case R.id.bs4:
                if (section.getCurrentState() != 3)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.bs4)).setImageDrawable(getResources().getDrawable(R.drawable.course4_pressed));
                    section.setCurrentState(3);
                    ((ListView) currentView).setAdapter(section.getAdapter(3));
                }
                break;
            case R.id.ms1:
                if (section.getCurrentState() != 4)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.ms1)).setImageDrawable(getResources().getDrawable(R.drawable.course1_pressed));
                    section.setCurrentState(4);
                    ((ListView) currentView).setAdapter(section.getAdapter(4));
                }
                break;
            case R.id.ms2:
                if (section.getCurrentState() != 5)
                {
                    releaseButton();
                    ((ImageView) getView().findViewById(R.id.ms2)).setImageDrawable(getResources().getDrawable(R.drawable.course2_pressed));
                    section.setCurrentState(5);
                    ((ListView) currentView).setAdapter(section.getAdapter(5));
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
}
