package ru.hse.shugurov.bi_application.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.hse.shugurov.bi_application.ImageLoader;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.ImageViewProxy;
import ru.hse.shugurov.bi_application.model.ProjectItem;

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
        imageLoader = ImageLoader.instance();
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
        imageLoader.displayImage(items[position].getPictureUrl(), new ImageViewProxy(imageView, width));
        return resultViews;
    }
}
