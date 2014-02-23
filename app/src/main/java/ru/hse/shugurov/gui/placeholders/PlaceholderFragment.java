package ru.hse.shugurov.gui.placeholders;

/**
 * Created by Иван on 29.12.13.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.sections.Section;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    //private static final String ARG_SECTION_NUMBER = "section_number";
    private Context context;
    private Section section;
    private MainActivity.FragmentListener fragmentListener;

    public PlaceholderFragment(Context context, MainActivity.FragmentListener fragmentListener, Section section)
    {
        this.context = context;
        this.fragmentListener = fragmentListener;
        this.fragmentListener.setCurrentFragment(this);
        this.section = section;
        fragmentListener.setSectionTitle(section.getTitle());
        /*Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        this.setArguments(args);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("ok");
        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        //((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        //fragmentListener.setSectionTitle(section.getTitle());
    }


    protected Context getContext()
    {
        return context;
    }

    protected MainActivity.FragmentListener getFragmentListener()
    {
        return fragmentListener;
    }

    //возвращет true, если эту операцию выполняет MainActivity
    public boolean moveBack()
    {
        return true;
    }

    public Section getSection()
    {
        return section;
    }
}
