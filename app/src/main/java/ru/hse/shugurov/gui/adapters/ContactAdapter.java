package ru.hse.shugurov.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.model.Contact;

/**
 * Created by Иван on 07.01.14.
 */
public class ContactAdapter extends BaseAdapter
{
    private Contact[] contacts;
    private LayoutInflater inflater;

    public ContactAdapter(Context context, Contact[] contacts)
    {
        this.contacts = contacts;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return contacts.length;
    }

    @Override
    public Object getItem(int position)
    {
        return contacts[position];
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
        ((TextView) resultView.findViewById(R.id.contact_item_name)).setText(contacts[position].getName());
        ((TextView) resultView.findViewById(R.id.contact_item_department)).setText(contacts[position].getDepartment());
        ((TextView) resultView.findViewById(R.id.contact_item_telephone)).setText(contacts[position].getTelephone());
        ((TextView) resultView.findViewById(R.id.contact_item_email)).setText(contacts[position].getEmail());
        return resultView;
    }
}
