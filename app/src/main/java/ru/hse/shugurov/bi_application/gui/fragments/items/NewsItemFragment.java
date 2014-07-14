package ru.hse.shugurov.bi_application.gui.fragments.items;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.shugurov.bi_application.ImageLoader;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.ImageViewProxy;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.model.NewsItem;

/**
 * Created by Иван on 06.01.14.
 */
public class NewsItemFragment extends BaseFragment
{

    public static final String ITEM_TAG = "news_fragment_concrete_item";
    private NewsItem item;

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
        imageLoader.displayImage(item.getPicture(), new ImageViewProxy(imageView, width));
        return rootView;
    }

    @Override
    protected void readStateFromBundle(Bundle args)
    {
        super.readStateFromBundle(args);
        item = args.getParcelable(ITEM_TAG);
    }

    @Override
    protected void setTitle()
    {
        String title = item.getTitle();
        getActivity().setTitle(title);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ITEM_TAG, item);
    }
}
