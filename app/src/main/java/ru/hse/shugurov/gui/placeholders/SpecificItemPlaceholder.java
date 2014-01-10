package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.widget.ListAdapter;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;

/**
 * Created by Иван on 07.01.14.
 */
public class SpecificItemPlaceholder extends PlaceholderFragment
{
    private ListAdapter adapter;

    public SpecificItemPlaceholder(Context context, MainActivity.FragmentChanged fragmentChanged, int sectionNumber)
    {
        super(context, fragmentChanged, sectionNumber);
        getFragmentChanged().setCurrentFragment(this);
    }

    @Override
    public boolean moveBack()
    {
        if (adapter == null)
        {
            return true;
        }
        else
        {
            PlaceholderFragmentWithList placeholder = new PlaceholderFragmentWithList(getContext(), getFragmentChanged() , getSectionNumber());
            placeholder.setAdapter(adapter);
            getFragmentManager().beginTransaction().replace(R.id.container, placeholder).commit();
            return  false;
        }
    }

    public ListAdapter getAdapter()
    {
        return adapter;
    }

    public void setAdapter(ListAdapter adapter)
    {
        this.adapter = adapter;
    }
}
