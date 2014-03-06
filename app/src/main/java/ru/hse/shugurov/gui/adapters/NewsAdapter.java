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
import ru.hse.shugurov.model.NewsItem;

/**
 * Created by Иван on 01.01.14.
 */
public class NewsAdapter extends BaseAdapter//TODO при отсутствии интернета падать может тут
{
    private NewsItem[] items;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public NewsAdapter(Context context, NewsItem[] items)
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
        View view = convertView;
        if (view == null)
        {
            view = inflater.inflate(R.layout.news_item, parent, false);
        }
        ((TextView) view.findViewById(R.id.news_item_headline)).setText(items[position].getTitle());
        ((TextView) view.findViewById(R.id.news_item_text)).setText(items[position].getSummary());
        ((TextView) view.findViewById(R.id.news_item_date)).setText(items[position].getDate());
        ImageView imageView = (ImageView) view.findViewById(R.id.news_item_picture);
        imageView.setImageBitmap(null);
        float weightSum = ((LinearLayout) view).getWeightSum();
        int width = (int) ((parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight()) * (1 / weightSum));
        imageLoader.displayImage(items[position].getPicture(), new FlexibleImageView(imageView, width));
        return view;
    }
}
