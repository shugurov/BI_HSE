package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.sections.ReferencesSection;
import ru.hse.shugurov.sections.Section;

/**
 * Created by Иван on 20.01.14.
 */
public class SchedulePlaceholderFragment extends PlaceholderFragment implements View.OnClickListener
{


    public SchedulePlaceholderFragment(Context context, MainActivity.FragmentChanged fragmentChanged, Section section, int sectionNumber)
    {
        super(context, fragmentChanged, section, sectionNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.schedule, container, false);
        ReferencesSection referencesSection = (ReferencesSection) getSection();
        rootView.findViewById(R.id.schedule_bs1).setOnClickListener(this);
        rootView.findViewById(R.id.schedule_bs2).setOnClickListener(this);
        rootView.findViewById(R.id.schedule_bs3).setOnClickListener(this);
        rootView.findViewById(R.id.schedule_bs4).setOnClickListener(this);
        rootView.findViewById(R.id.schedule_ms1).setOnClickListener(this);
        rootView.findViewById(R.id.schedule_ms2).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view)
    {

    }
}
