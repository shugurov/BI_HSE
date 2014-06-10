package ru.hse.shugurov.bi_application.gui.fragments;

import java.util.LinkedList;

/**
 * Created by Иван on 07.06.2014.
 */
public class BackStack//TODO не работает при перевороте
{
    private LinkedList<BaseFragment> backStack = new LinkedList<BaseFragment>();

    public void addFragmentToBackStack(BaseFragment fragment)
    {
        backStack.add(fragment);
    }

    public BaseFragment getPreviousFragment()
    {
        if (backStack.size() < 2)
        {
            return null;
        }
        backStack.removeLast();
        return backStack.pollLast();
    }

    public BaseFragment getCurrentFragment()
    {
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
