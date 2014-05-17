package ru.hse.shugurov.bi_application.gui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.sections.Section;

/**
 * Created by Иван on 02.01.14.
 */
public class NavigationDrawerAdapter extends BaseAdapter
{
    private Context context;
    private Section[] sections;
    private LayoutInflater inflater;
    private View[] contentViews;

    public NavigationDrawerAdapter(Context context, Section[] sections)
    {
        this.context = context;
        this.sections = sections;
        inflater = LayoutInflater.from(context);
        contentViews = new View[sections.length];
        Arrays.fill(contentViews, null);
    }

    @Override
    public int getCount()
    {
        return sections.length;
    }

    @Override
    public Section getItem(int position)
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
        if (contentViews[position] != null)
        {
            return convertView;
        } else
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

    public void checkItem(int position)
    {
        Section section = getItem(position);
        View checkerView = contentViews[position];
        if (checkerView != null)
        {
            checkerView.setBackgroundColor(Color.RED);
        }
    }
}
