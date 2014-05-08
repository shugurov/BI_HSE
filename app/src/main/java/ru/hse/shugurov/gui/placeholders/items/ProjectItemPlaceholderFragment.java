package ru.hse.shugurov.gui.placeholders.items;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.shugurov.ImageLoader;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.FlexibleImageView;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.adapters.ProjectAdapter;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.model.ProjectItem;
import ru.hse.shugurov.sections.Section;
import ru.hse.shugurov.sections.SingleViewSection;

/**
 * Created by Иван on 06.01.14.
 */
public class ProjectItemPlaceholderFragment extends PlaceholderFragment//TODO почему-то падал при открытии браузера
{
    private final static String PROJECT_ITEM_TAG = "project_item";
    private ProjectItem item;


    public ProjectItemPlaceholderFragment()
    {
    }

    public ProjectItemPlaceholderFragment(ProjectItem item, MainActivity.FragmentListener fragmentListener, Section section)
    {
        super(fragmentListener, section);
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.project_layout, container, false);
        ((TextView) rootView.findViewById(R.id.project_layout_text)).setText(item.getText());
        rootView.findViewById(R.id.project_layout_details).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                startActivity(browserIntent);
            }
        });
        ImageLoader imageLoader = ((ProjectAdapter) ((SingleViewSection) getSection()).getAdapter()).getImageLoader();
        ImageView imageView = ((ImageView) rootView.findViewById(R.id.project_layout_picture));
        int paddingLeft = imageView.getPaddingLeft();
        int paddingRight = imageView.getPaddingRight();
        int width = container.getWidth() - paddingLeft - paddingRight;
        imageLoader.displayImage(item.getPictureUrl(), new FlexibleImageView(imageView, width));
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            item = (ProjectItem) savedInstanceState.getSerializable(PROJECT_ITEM_TAG);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PROJECT_ITEM_TAG, item);
    }
}
