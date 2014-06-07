package ru.hse.shugurov.bi_application.gui.fragments;

/**
 * Created by Иван on 29.12.13.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.sections.Section;


public abstract class BaseFragment extends Fragment
{
    public final static String SECTION_TAG = "section";
    private Section section;
    private BackStack backStack;

    protected void readStateFromBundle(Bundle args)
    {
        if (args != null)
        {
            section = (Section) args.getSerializable(SECTION_TAG);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (backStack == null)
        {
            backStack = new BackStack();
        }
        if (savedInstanceState == null)
        {
            Bundle arguments = getArguments();
            readStateFromBundle(arguments);
        } else
        {
            readStateFromBundle(savedInstanceState);
        }

        getActivity().setTitle(section.getTitle());//TODO неправильный заголовок
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SECTION_TAG, section);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        backStack.addFragmentToBackStack(this);

    }

    public Section getSection()
    {
        return section;
    }

    protected void showNextFragment(BaseFragment childFragment)
    {
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, childFragment);
        childFragment.setBackStack(backStack);
        transaction.commit();
    }

    public BaseFragment getFragmentToBeShown()
    {
        return backStack.getFragmentToBeShown();
    }

    public void setBackStack(BackStack backStack)
    {
        this.backStack = backStack;
    }
}
