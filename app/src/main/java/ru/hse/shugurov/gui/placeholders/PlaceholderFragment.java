package ru.hse.shugurov.gui.placeholders;

/**
 * Created by Иван on 29.12.13.
 */

import android.support.v4.app.Fragment;

import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.sections.Section;

/**
 * A placeholder fragment containing a simple view.
 */
public abstract class PlaceholderFragment extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    //private static final String ARG_SECTION_NUMBER = "section_number";
    private Section section;
    private MainActivity.FragmentListener fragmentListener;

    public PlaceholderFragment(MainActivity.FragmentListener fragmentListener, Section section)
    {
        this.fragmentListener = fragmentListener;
        this.fragmentListener.setCurrentFragment(this);
        this.section = section;
        fragmentListener.setSectionTitle(section.getTitle());
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
