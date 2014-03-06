package ru.hse.shugurov.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.hse.shugurov.ImageLoader;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.FlexibleImageView;
import ru.hse.shugurov.model.ProjectItem;

/**
 * Created by Иван on 06.01.14.
 */
public class ProjectAdapter extends BaseAdapter
{
    private ProjectItem[] items;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public ProjectAdapter(Context context, ProjectItem[] items)
    {
        this.items = items;
        inflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(context);
    }

    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }

    @Override
    public int getCount()
    {
        return items.length;
    }

    @Override
    public Object getItem(int position)
    {
        return items[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View resultViews;
        resultViews = convertView;
        if (resultViews == null)
        {
            resultViews = inflater.inflate(R.layout.project_item, parent, false);
        }
        ((TextView) resultViews.findViewById(R.id.project_item_headline)).setText(items[position].getHeadline());
        ImageView imageView = (ImageView) resultViews.findViewById(R.id.project_item_image);
        imageView.setImageBitmap(null);
        float weightSum = ((LinearLayout) resultViews).getWeightSum();
        int width = (int) ((parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight()) * (1 / weightSum));
        imageLoader.displayImage(items[position].getPictureUrl(), new FlexibleImageView(imageView, width));
        //imageLoader.displayImage(items[position].getPictureUrl(), (ImageView) resultViews.findViewById(R.id.project_item_image));
        return resultViews;
    }
}
