package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.model.Section;

/**
 * Created by Иван on 13.01.14.
 */
public class BillboardPlaceholderFragment extends PlaceholderFragment
{
    private View root;

    public BillboardPlaceholderFragment(Context context, MainActivity.FragmentChanged fragmentChanged, Section section, int sectionNumber)
    {
        super(context, fragmentChanged, section, sectionNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.billboard, container, false);
        return root;
    }
}
