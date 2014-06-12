package ru.hse.shugurov.bi_application.gui.fragments.items;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.shugurov.bi_application.ImageLoader;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.FlexibleImageView;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.model.ProjectItem;

/**
 * Created by Иван on 06.01.14.
 */
public class ProjectItemPlaceholderFragment extends BaseFragment//TODO почему-то падал при открытии браузера
{
    public final static String PROJECT_ITEM_TAG = "project_item";
    private ProjectItem item;

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
        ImageLoader imageLoader = ImageLoader.instance();
        ImageView imageView = ((ImageView) rootView.findViewById(R.id.project_layout_picture));
        int paddingLeft = imageView.getPaddingLeft();
        int paddingRight = imageView.getPaddingRight();
        int width = container.getWidth() - paddingLeft - paddingRight;
        imageLoader.displayImage(item.getPictureUrl(), new FlexibleImageView(imageView, width));
        return rootView;
    }

    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);
    }

    @Override
    protected void readStateFromBundle(Bundle args)
    {
        super.readStateFromBundle(args);
        item = (ProjectItem) args.get(PROJECT_ITEM_TAG);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PROJECT_ITEM_TAG, item);
    }
}
