package ru.hse.shugurov.bi_application.gui.fragments.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.model.AdvertItem;

/**
 * Created by Иван on 20.01.14.
 */
public class AdvertItemFragment extends BaseFragment
{
    public static final String ADVERT_ITEM_TAG = "advert_fragment_advert_item_tag";
    private AdvertItem advertItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.advert_layout, container, false);
        ((TextView) rootView.findViewById(R.id.advert_layout_text)).setText(advertItem.getText());
        return rootView;
    }

    @Override
    protected void readStateFromBundle(Bundle args)
    {
        super.readStateFromBundle(args);
        advertItem = (AdvertItem) args.getSerializable(ADVERT_ITEM_TAG);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ADVERT_ITEM_TAG, advertItem);
    }
}
