package ru.hse.shugurov.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.hse.shugurov.R;
import ru.hse.shugurov.model.AdvertItem;

/**
 * Created by Иван on 18.01.14.
 */
public class AdvertAdapter extends BaseAdapter
{
    private ArrayList<AdvertItem> advertItems;
    private LayoutInflater inflater;

    public AdvertAdapter(Context context, ArrayList<AdvertItem> advertItems)
    {
        this.advertItems = advertItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return advertItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return advertItems.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View resultView = convertView;
        if (resultView == null)
        {
            resultView = inflater.inflate(R.layout.advert_item, parent, false);
        }
        ((TextView) resultView.findViewById(R.id.advert_item_title)).setText(advertItems.get(position).getTitle());
        ((TextView) resultView.findViewById(R.id.advert_item_text)).setText(advertItems.get(position).getText());
        return resultView;
    }
}
