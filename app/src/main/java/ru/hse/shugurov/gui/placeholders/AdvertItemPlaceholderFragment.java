package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.model.AdvertItem;
import ru.hse.shugurov.sections.Section;

/**
 * Created by Иван on 20.01.14.
 */
public class AdvertItemPlaceholderFragment extends SpecificItemPlaceholder
{
    private AdvertItem advertItem;

    public AdvertItemPlaceholderFragment(Context context, MainActivity.FragmentListener fragmentListener, Section section, AdvertItem advertItem)
    {
        super(context, fragmentListener, section);
        this.advertItem = advertItem;
        fragmentListener.setSectionTitle(advertItem.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.advert_layout, container, false);
        ((TextView) rootView.findViewById(R.id.advert_layout_text)).setText(advertItem.getText());
        return rootView;
    }
}
