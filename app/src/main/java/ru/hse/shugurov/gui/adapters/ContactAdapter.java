package ru.hse.shugurov.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.shugurov.ImageLoader;
import ru.hse.shugurov.R;
import ru.hse.shugurov.model.ContactItem;

/**
 * Created by Иван on 07.01.14.
 */
public class ContactAdapter extends BaseAdapter
{
    private ContactItem[] contactItems;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public ContactAdapter(Context context, ContactItem[] contactItems)
    {
        this.contactItems = contactItems;
        inflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(context, context.getResources().getDrawable(R.drawable.ic_launcher));
    }

    @Override
    public int getCount()
    {
        return contactItems.length;
    }

    @Override
    public Object getItem(int position)
    {
        return contactItems[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View resultView;
        resultView = convertView;
        if (resultView == null)
        {
            resultView = inflater.inflate(R.layout.contact_item, parent, false);
        }
        ((TextView) resultView.findViewById(R.id.contact_item_name)).setText(contactItems[position].getName());
        ((TextView) resultView.findViewById(R.id.contact_item_department)).setText(contactItems[position].getDepartment());
        ((TextView) resultView.findViewById(R.id.contact_item_telephone)).setText(contactItems[position].getTelephone());
        ((TextView) resultView.findViewById(R.id.contact_item_email)).setText(contactItems[position].getEmail());
        imageLoader.displayImage(contactItems[position].getPicture(), ((ImageView) resultView.findViewById(R.id.contact_item_picture)));
        return resultView;
    }
}
