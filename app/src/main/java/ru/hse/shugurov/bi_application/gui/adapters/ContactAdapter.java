package ru.hse.shugurov.bi_application.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.shugurov.bi_application.ImageLoader;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.model.ContactItem;

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
        imageLoader = ImageLoader.instance();
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
        ((TextView) resultView.findViewById(R.id.contact_item_email)).setText("E-Mail: " + contactItems[position].getEmail());
        ImageView imageView = (ImageView) resultView.findViewById(R.id.contact_item_picture);
        imageView.setImageBitmap(null);
        imageLoader.displayImage(contactItems[position].getPicture(), ((ImageView) resultView.findViewById(R.id.contact_item_picture)));
        return resultView;
    }
}
