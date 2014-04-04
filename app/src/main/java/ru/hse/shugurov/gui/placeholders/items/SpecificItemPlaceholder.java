package ru.hse.shugurov.gui.placeholders.items;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.lists.EventsPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.lists.PlaceholderFragmentWithList;
import ru.hse.shugurov.gui.placeholders.special.BillboardPlaceholderFragment;
import ru.hse.shugurov.sections.EventsScreen;
import ru.hse.shugurov.sections.MultipleAdaptersViewSection;
import ru.hse.shugurov.sections.Section;
import ru.hse.shugurov.sections.SingleViewSection;

/**
 * Created by Иван on 07.01.14.
 */
public class SpecificItemPlaceholder extends PlaceholderFragment
{
    public SpecificItemPlaceholder(MainActivity.FragmentListener fragmentListener, Section section)
    {
        super(fragmentListener, section);
    }


    @Override
    public boolean moveBack()
    {
        if (getSection() instanceof SingleViewSection)
        {
            PlaceholderFragmentWithList placeholder;
            placeholder = new PlaceholderFragmentWithList(getFragmentListener(), getSection());
            getFragmentManager().beginTransaction().replace(R.id.container, placeholder).commit();
        } else
        {
            if (getSection() instanceof MultipleAdaptersViewSection)
            {
                BillboardPlaceholderFragment placeholder;
                placeholder = new BillboardPlaceholderFragment(getFragmentListener(), (MultipleAdaptersViewSection) getSection());
                getFragmentManager().beginTransaction().replace(R.id.container, placeholder).commit();
            } else
            {
                EventsPlaceholderFragment placeholder;
                placeholder = new EventsPlaceholderFragment(getFragmentListener(), (EventsScreen) getSection());
                getFragmentManager().beginTransaction().replace(R.id.container, placeholder).commit();
            }
        }
        return false;
    }
}
