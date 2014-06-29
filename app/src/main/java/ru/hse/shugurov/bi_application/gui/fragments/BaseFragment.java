package ru.hse.shugurov.bi_application.gui.fragments;

/**
 * Created by Иван on 29.12.13.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.sections.Section;


public abstract class BaseFragment extends Fragment//TODO запись в файл перенести сюда
{
    public final static String SECTION_TAG = "section";
    private Section section;
    private BackStack backStack;

    protected void readStateFromBundle(Bundle args)
    {
        section = (Section) args.getSerializable(SECTION_TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (backStack == null)
        {
            backStack = new BackStack();
        }
        backStack.addFragmentToBackStack(this);
        if (savedInstanceState == null)
        {
            Bundle arguments = getArguments();
            readStateFromBundle(arguments);
        } else
        {
            readStateFromBundle(savedInstanceState);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String title = section.getTitle();
        getActivity().setTitle(title);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SECTION_TAG, section);
    }

    public Section getSection()
    {
        return section;
    }

    public void setSection(Section section)
    {
        this.section = section;
    }

    protected void showNextFragment(BaseFragment childFragment)
    {
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, childFragment);
        childFragment.setBackStack(backStack);
        transaction.commit();
    }

    public BaseFragment popPreviousFragment()
    {
        return backStack.popPreviousFragment();
    }

    public BaseFragment popCurrentFragment()
    {
        return backStack.popCurrentFragment();
    }

    public BackStack getBackStack()
    {
        return backStack;
    }

    void setBackStack(BackStack backStack)
    {
        this.backStack = backStack;
    }

    protected void handleLoadProblem()
    {
        if (isAdded())
        {
            Toast.makeText(getActivity(), "Не удалось загрузить данные", Toast.LENGTH_SHORT).show();
        }
    }
}
