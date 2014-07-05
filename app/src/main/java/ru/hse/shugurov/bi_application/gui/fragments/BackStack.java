package ru.hse.shugurov.bi_application.gui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.util.LinkedList;

/**
 * Created by Иван on 07.06.2014.
 */
public class BackStack implements Parcelable//TODO не работает при перевороте
{
    public static final Creator<BackStack> CREATOR = new Creator<BackStack>()
    {
        @Override
        public BackStack createFromParcel(Parcel source)
        {
            return new BackStack(source);
        }

        @Override
        public BackStack[] newArray(int size)
        {
            return new BackStack[size];
        }
    };
    private LinkedList<BaseFragment> backStack = new LinkedList<BaseFragment>();
    private LinkedList<FragmentInfo> fragmentsInformation;

    public BackStack()
    {
    }

    protected BackStack(Parcel source)
    {
        fragmentsInformation = new LinkedList<FragmentInfo>();
        int stackLength = source.readInt();
        for (int i = 0; i < stackLength; i++)
        {
            FragmentInfo info = source.readParcelable(FragmentInfo.class.getClassLoader());
            fragmentsInformation.add(info);
        }
    }

    public void restoreBackStack(Context context)
    {
        if (fragmentsInformation != null)
        {
            for (FragmentInfo fragmentInfo : fragmentsInformation)
            {
                BaseFragment fragment = (BaseFragment) Fragment.instantiate(context, fragmentInfo.getClassName(), fragmentInfo.getArguments());
                backStack.add(fragment);
                fragment.setBackStack(this);
            }
            fragmentsInformation = null;
        }
    }

    public void addFragmentToBackStack(BaseFragment fragment)
    {
        if (backStack.peekLast() != fragment)
        {
            backStack.add(fragment);
        }
    }

    public BaseFragment popPreviousFragment()
    {
        backStack.pollLast();//TODO
        return backStack.peekLast();
    }

    public BaseFragment popCurrentFragment()
    {
        return backStack.pollLast();
    }

    public BaseFragment peekCurrentFragment()
    {
        return backStack.peekLast();
    }

    public BaseFragment getMainFragment()
    {
        return backStack.getFirst();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(backStack.size());
        for (BaseFragment fragment : backStack)
        {
            Bundle arguments = fragment.getArguments();
            String className = ((Object) fragment).getClass().getName();
            FragmentInfo info = new FragmentInfo(arguments, className);
            dest.writeParcelable(info, flags);
        }
    }
}

class FragmentInfo implements Parcelable
{

    public static final Creator<FragmentInfo> CREATOR = new Creator<FragmentInfo>()
    {
        @Override
        public FragmentInfo createFromParcel(Parcel source)
        {
            return new FragmentInfo(source);
        }

        @Override
        public FragmentInfo[] newArray(int size)
        {
            return new FragmentInfo[size];
        }
    };
    private Bundle arguments;
    private String className;

    FragmentInfo(Bundle arguments, String className)
    {
        this.arguments = arguments;
        this.className = className;
    }

    FragmentInfo(Parcel source)
    {
        arguments = source.readBundle();
        className = source.readString();
    }

    public String getClassName()
    {
        return className;
    }

    public Bundle getArguments()
    {
        return arguments;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeBundle(arguments);
        dest.writeString(className);
    }
}
