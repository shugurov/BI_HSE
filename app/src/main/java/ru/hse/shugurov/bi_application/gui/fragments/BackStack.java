package ru.hse.shugurov.bi_application.gui.fragments;

import java.util.LinkedList;

/**
 * Created by Иван on 07.06.2014.
 */
public class BackStack
{
    private LinkedList<BaseFragment> backStack = new LinkedList<BaseFragment>();
    private boolean avoidAddingToBackStack;

    public void addFragmentToBackStack(BaseFragment fragment)
    {
        if (avoidAddingToBackStack)
        {
            avoidAddingToBackStack = false;
        } else
        {
            backStack.add(fragment);
        }
    }

    public BaseFragment getFragmentToBeShown()
    {
        avoidAddingToBackStack = true;
        BaseFragment result;
        if (backStack.isEmpty())
        {
            result = null;
        } else
        {
            result = backStack.getLast();
            backStack.removeLast();
        }
        return result;
    }
}
