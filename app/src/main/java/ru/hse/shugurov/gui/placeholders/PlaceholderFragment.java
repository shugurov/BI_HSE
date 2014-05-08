package ru.hse.shugurov.gui.placeholders;

/**
 * Created by Иван on 29.12.13.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;

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
    }

    public PlaceholderFragment(MainActivity.FragmentListener fragmentListener, Section section)
    {
        this.fragmentListener = fragmentListener;
        this.fragmentListener.setCurrentFragment(this);
        this.section = section;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && section == null)
        {
            section = (Section) savedInstanceState.getSerializable(SECTION_TAG);
            fragmentListener = (MainActivity.FragmentListener) savedInstanceState.getSerializable(FRAGMENT_TAG);
        }
        getActivity().setTitle(section.getTitle());//TODO неправильный заголовок
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SECTION_TAG, section);
        outState.putSerializable(FRAGMENT_TAG, fragmentListener);
    }

    protected MainActivity.FragmentListener getFragmentListener()
    {
        return fragmentListener;
    }//TODO перенести интерфейс сюда

    public Section getSection()
    {
        return section;
    }

    protected void showChildFragment(Fragment childFragment)
    {
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.container, childFragment);
        transaction.commit();
    }
}
