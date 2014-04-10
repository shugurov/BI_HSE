package ru.hse.shugurov.gui.placeholders.items;

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
import ru.hse.shugurov.gui.FlexibleImageView;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.model.NewsItem;
import ru.hse.shugurov.sections.Section;

/**
 * Created by Иван on 06.01.14.
 */
public class NewsItemPlaceholderFragment extends PlaceholderFragment
{
    private NewsItem item;

    public NewsItemPlaceholderFragment(NewsItem item, MainActivity.FragmentListener fragmentListener, Section section)
    {
        super(fragmentListener, section);
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
        textView.setText(spannedString);
        ImageLoader imageLoader = ImageLoader.instance();
        ImageView imageView = (ImageView) rootView.findViewById(R.id.news_layout_picture);
        View newsContainer = rootView.findViewById(R.id.news_layout_container);
        int paddingLeft = newsContainer.getPaddingLeft();
        int paddingRight = newsContainer.getPaddingRight();
        int width = container.getWidth() - paddingLeft - paddingRight;
        imageLoader.displayImage(item.getPicture(), new FlexibleImageView(imageView, width));
        return rootView;
    }

}
