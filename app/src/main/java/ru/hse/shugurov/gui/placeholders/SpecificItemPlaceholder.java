package ru.hse.shugurov.gui.placeholders;

import android.content.Context;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.sections.MultipleViewScreen;
import ru.hse.shugurov.sections.Section;
import ru.hse.shugurov.sections.SingleViewSection;

/**
 * Created by Иван on 07.01.14.
 */
public class SpecificItemPlaceholder extends PlaceholderFragment
{
    public SpecificItemPlaceholder(Context context, MainActivity.FragmentChanged fragmentChanged, Section section, int sectionNumber)
    {
        super(context, fragmentChanged, section, sectionNumber);
    }

    @Override
    public boolean moveBack()//TODO поправить, чтобы в событиях нормально возвращалось назад
    {
        if (getSection() instanceof SingleViewSection)
        {
            PlaceholderFragmentWithList placeholder = new PlaceholderFragmentWithList(getContext(), getFragmentChanged(), getSection(), getSectionNumber());
            getFragmentManager().beginTransaction().replace(R.id.container, placeholder).commit();
        } else
        {
            EventsPlaceholderFragment placeholder = new EventsPlaceholderFragment(getContext(), getFragmentChanged(), (MultipleViewScreen) getSection(), getSectionNumber());
            getFragmentManager().beginTransaction().replace(R.id.container, placeholder).commit();
        }
        return false;
    }
}
