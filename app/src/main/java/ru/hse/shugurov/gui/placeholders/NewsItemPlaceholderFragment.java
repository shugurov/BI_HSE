package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.model.NewsItem;

/**
 * Created by Иван on 06.01.14.
 */
public class NewsItemPlaceholderFragment extends SpecificItemPlaceholder
{
    private NewsItem item;
    public NewsItemPlaceholderFragment(Context context, NewsItem item, MainActivity.FragmentChanged fragmentChanged, int sectionNumber)
    {
        super(context, fragmentChanged, sectionNumber);
        this.item = item;
        getFragmentChanged().setCurrentFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.news_layout, container, false);
        ((TextView)rootView.findViewById(R.id.news_layout_date)).setText(item.getDate());
        ((TextView)rootView.findViewById(R.id.news_layout_text)).setText(item.getText());
        return rootView;
    }
}
