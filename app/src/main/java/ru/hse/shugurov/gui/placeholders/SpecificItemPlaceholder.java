package ru.hse.shugurov.gui.placeholders;

import android.content.Context;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.sections.MultipleAdaptersViewSection;
import ru.hse.shugurov.sections.MultipleViewScreen;
import ru.hse.shugurov.sections.Section;
import ru.hse.shugurov.sections.SingleViewSection;

/**
 * Created by Иван on 07.01.14.
 */
public class SpecificItemPlaceholder extends PlaceholderFragment
{
    public SpecificItemPlaceholder(Context context, MainActivity.FragmentListener fragmentListener, Section section)
    {
        super(context, fragmentListener, section);

    }

    @Override
    public boolean moveBack()
    {
        if (getSection() instanceof SingleViewSection)
        {
            PlaceholderFragmentWithList placeholder;
            placeholder = new PlaceholderFragmentWithList(getContext(), getFragmentListener(), getSection());
            getFragmentManager().beginTransaction().replace(R.id.container, placeholder).commit();
        } else
        {
            if (getSection() instanceof MultipleAdaptersViewSection)
            {
                BillboardPlaceholderFragment placeholder;
                placeholder = new BillboardPlaceholderFragment(getContext(), getFragmentListener(), (MultipleAdaptersViewSection) getSection());
                getFragmentManager().beginTransaction().replace(R.id.container, placeholder).commit();
            } else
            {
                EventsPlaceholderFragment placeholder;
                placeholder = new EventsPlaceholderFragment(getContext(), getFragmentListener(), (MultipleViewScreen) getSection());
                getFragmentManager().beginTransaction().replace(R.id.container, placeholder).commit();
            }
        }
        return false;
    }
}
