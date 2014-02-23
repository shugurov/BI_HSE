package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.shugurov.ImageLoader;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.adapters.NewsAdapter;
import ru.hse.shugurov.model.NewsItem;
import ru.hse.shugurov.sections.MultipleViewScreen;
import ru.hse.shugurov.sections.Section;
import ru.hse.shugurov.sections.SingleViewSection;

/**
 * Created by Иван on 06.01.14.
 */
public class NewsItemPlaceholderFragment extends SpecificItemPlaceholder
{
    private NewsItem item;

    public NewsItemPlaceholderFragment(Context context, NewsItem item, MainActivity.FragmentListener fragmentListener, Section section)
    {
        super(context, fragmentListener, section);
        this.item = item;
        fragmentListener.setSectionTitle(item.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.news_layout, container, false);
        ((TextView) rootView.findViewById(R.id.news_layout_date)).setText(item.getDate());
        TextView textView = ((TextView) rootView.findViewById(R.id.news_layout_text));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spannedString = new SpannableString(item.getText());
        Linkify.addLinks(spannedString, Linkify.ALL);
        //textView.setText(item.getText());
        textView.setText(spannedString);
        ImageLoader imageLoader;
        if (getSection() instanceof SingleViewSection)
        {
            imageLoader = ((NewsAdapter) ((SingleViewSection) getSection()).getAdapter()).getImageLoader();
        } else
        {
            imageLoader = ((NewsAdapter) ((MultipleViewScreen) getSection()).getAdapter(((MultipleViewScreen) getSection()).getCurrentState())).getImageLoader();
        }
        ImageView imageView = (ImageView) rootView.findViewById(R.id.news_layout_picture);
        imageLoader.displayImage(item.getPicture(), imageView);
        return rootView;
    }

}
