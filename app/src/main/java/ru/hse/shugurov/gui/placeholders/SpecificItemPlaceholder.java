package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.widget.ListAdapter;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.model.Section;

/**
 * Created by Иван on 07.01.14.
 */
public class SpecificItemPlaceholder extends PlaceholderFragment
{
    private ListAdapter adapter;

    public SpecificItemPlaceholder(Context context, MainActivity.FragmentChanged fragmentChanged, Section section, int sectionNumber)
    {
        super(context, fragmentChanged, section, sectionNumber);
        getFragmentChanged().setCurrentFragment(this);
    }

    @Override
    public boolean moveBack()//TODO поправить, чтобы в событиях нормально возвращалось назад
    {
        if (adapter == null)
        {
            return true;
        } else
        {
            PlaceholderFragmentWithList placeholder = new PlaceholderFragmentWithList(getContext(), getFragmentChanged(), getSection(), getSectionNumber());
            placeholder.setAdapter(adapter);
            getFragmentManager().beginTransaction().replace(R.id.container, placeholder).commit();
            return false;
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
