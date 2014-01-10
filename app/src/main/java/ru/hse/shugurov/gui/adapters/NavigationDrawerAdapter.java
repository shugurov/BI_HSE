package ru.hse.shugurov.gui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.model.Section;

/**
 * Created by Иван on 02.01.14.
 */
public class NavigationDrawerAdapter extends BaseAdapter
{
    private Context context;
    private Section[] sections;
    private LayoutInflater inflater;

    public NavigationDrawerAdapter(Context context, Section[] sections)
    {
        this.context = context;
        this.sections = sections;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return sections.length;
    }

    @Override
    public Object getItem(int position)
    {
        return sections[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;  //Может стоит как-то по-другому получать id?
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View responseView = convertView;
        if (responseView == null)
        {
            responseView = inflater.inflate(R.layout.drawer_item, parent, false);
        }
        ((TextView) responseView.findViewById(R.id.drawer_item_text)).setText(sections[position].getTitle());
        Drawable icon = context.getResources().getDrawable(sections[position].getIconDefault());
        ((ImageView) responseView.findViewById(R.id.drawer_item_icon)).setImageDrawable(icon);
        return responseView;
    }
}
