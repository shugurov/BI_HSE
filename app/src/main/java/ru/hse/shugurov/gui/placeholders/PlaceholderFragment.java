package ru.hse.shugurov.gui.placeholders;

/**
 * Created by Иван on 29.12.13.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.sections.Section;


public abstract class PlaceholderFragment extends Fragment
{

    private final static String FRAGMENT_TAG = "fragment_listener";
    private final static String SECTION_TAG = "section";
    private Section section;
    private MainActivity.FragmentListener fragmentListener;

    public PlaceholderFragment()
    {
        Bundle arguments = getArguments();//TODO delete
        setArguments(new Bundle());
    }

    public PlaceholderFragment(MainActivity.FragmentListener fragmentListener, Section section)
    {
        this();
        initParent(fragmentListener, section);
    }

    protected MainActivity.FragmentListener getFragmentListener()
    {
        return fragmentListener;
    }//TODO перенести интерфейс сюда

    public Section getSection()
    {
        return section;
    }

    protected void initParent(MainActivity.FragmentListener fragmentListener, Section section)
    {
        this.fragmentListener = fragmentListener;
        this.fragmentListener.setCurrentFragment(this);
        this.section = section;
        fragmentListener.setSectionTitle(section.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (section == null)
        {
            MainActivity.FragmentListener listener = (MainActivity.FragmentListener) savedInstanceState.get(FRAGMENT_TAG);
            Section currentSection = (Section) savedInstanceState.get(SECTION_TAG);
            initParent(listener, currentSection);
        } else//TODO  надо ли это делать тут?(
        {
            Bundle arguments = getArguments();
            arguments.putSerializable(FRAGMENT_TAG, fragmentListener);
            arguments.putSerializable(SECTION_TAG, section);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void showChildFragment(Fragment childFragment)
    {
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.container, childFragment);
        transaction.commit();
    }
}
