package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;

/**
 * Created by Иван on 09.01.14.
 */
public class EventsPlaceholderFragment extends PlaceholderFragment implements View.OnClickListener
{
    public EventsPlaceholderFragment(Context context, MainActivity.FragmentChanged fragmentChanged, int sectionNumber)
    {
        super(context, fragmentChanged, sectionNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.events, container, false);
        return rootView;
    }

    @Override
    public void onClick(View v)
    {

    }
}
