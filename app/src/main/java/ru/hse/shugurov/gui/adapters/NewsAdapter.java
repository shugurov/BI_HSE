package ru.hse.shugurov.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.model.NewsItem;

/**
 * Created by Иван on 01.01.14.
 */
public class NewsAdapter extends BaseAdapter
{
    private NewsItem[] items;
    private Context context;
    private LayoutInflater inflater;

    public NewsAdapter(Context context, NewsItem[] items)
    {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        return position; //TODO наверное, стоит поправить id
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
        return view;
    }
}
