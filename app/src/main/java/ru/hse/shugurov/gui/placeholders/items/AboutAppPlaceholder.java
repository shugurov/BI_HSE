package ru.hse.shugurov.gui.placeholders.items;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.FlexibleImageView;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.model.Developer;
import ru.hse.shugurov.sections.AboutAppSection;
import ru.hse.shugurov.sections.Section;


public class AboutAppPlaceholder extends PlaceholderFragment
{
    public AboutAppPlaceholder(MainActivity.FragmentListener fragmentListener, Section section)
    {
        super(fragmentListener, section);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.about_app, container, false);
        ImageView imageView = ((ImageView) rootView.findViewById(R.id.about_app_image));
        int paddingLeft = imageView.getPaddingLeft();
        int paddingRight = imageView.getPaddingRight();
        int width = container.getWidth() - paddingLeft - paddingRight;
        new FlexibleImageView(imageView, width).setImageBitmap(BitmapFactory.decodeResource(getResources(), ((AboutAppSection) getSection()).getImage()));
        LinearLayout developersContainer = (LinearLayout) rootView.findViewById(R.id.about_app_container);
        for (int i = 0; i < ((AboutAppSection) getSection()).getNumberOfDevelopers(); i++)
        {
            Developer developer = ((AboutAppSection) getSection()).getDeveloper(i);
            View developerView = inflater.inflate(R.layout.developer, developersContainer, false);
            ((ImageView) developerView.findViewById(R.id.developer_photo)).setImageDrawable(getActivity().getResources().getDrawable(developer.getPhoto()));
            ((TextView) developerView.findViewById(R.id.developer_name)).setText(developer.getName());
            ((TextView) developerView.findViewById(R.id.developer_position)).setText(developer.getPosition());
            developersContainer.addView(developerView, i);
        }
        return rootView;
    }
}
