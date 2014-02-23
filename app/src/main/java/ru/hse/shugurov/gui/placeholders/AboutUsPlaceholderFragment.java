package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.sections.AboutUsSection;
import ru.hse.shugurov.sections.Section;

/**
 * Created by Иван on 09.02.14.
 */
public class AboutUsPlaceholderFragment extends PlaceholderFragment
{
    public AboutUsPlaceholderFragment(Context context, MainActivity.FragmentListener fragmentListener, Section section, int sectionNumber)
    {
        super(context, fragmentListener, section);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.about_us, container, false);
        AboutUsSection section = (AboutUsSection) getSection();
        ((ImageView) rootView.findViewById(R.id.about_us_image)).setImageDrawable(getResources().getDrawable(section.getImage()));
        ((TextView) rootView.findViewById(R.id.about_us_heading)).setText(section.getHeading());
        ((TextView) rootView.findViewById(R.id.about_us_text)).setText(section.getText());
        return rootView;
    }
}
