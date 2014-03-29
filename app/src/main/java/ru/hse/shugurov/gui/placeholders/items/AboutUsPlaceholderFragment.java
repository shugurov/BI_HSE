package ru.hse.shugurov.gui.placeholders.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.sections.AboutUsSection;
import ru.hse.shugurov.sections.Section;

/**
 * Created by Иван on 09.02.14.
 */
public class AboutUsPlaceholderFragment extends PlaceholderFragment
{
    public AboutUsPlaceholderFragment(MainActivity.FragmentListener fragmentListener, Section section)
    {
        super(fragmentListener, section);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.about_us, container, false);
        AboutUsSection section = (AboutUsSection) getSection();
        ImageView imageView = (ImageView) rootView.findViewById(R.id.about_us_image);
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), ((AboutUsSection) getSection()).getImage());
        double aspectRatio = ((double) bitmap.getHeight()) / bitmap.getWidth();
        int height = (int) Math.round(container.getWidth() * aspectRatio);
        imageView.getLayoutParams().height = height;
        imageView.getLayoutParams().width = container.getWidth() / 2;
        imageView.setImageBitmap(bitmap);
        ((TextView) rootView.findViewById(R.id.about_us_heading)).setText(section.getHeading());
        ((TextView) rootView.findViewById(R.id.about_us_text)).setText(section.getText());
        return rootView;
    }
}
