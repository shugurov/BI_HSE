package ru.hse.shugurov.bi_application.gui.fragments.items;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ru.hse.shugurov.bi_application.ImageLoader;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.model.ContactItem;

/**
 * Created by Иван on 07.01.14.
 */
public class ContactItemFragment extends BaseFragment
{
    public final static String CONTACT_ITEM_TAG = "contact_item";
    private ContactItem contactItem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View resultView;
        resultView = inflater.inflate(R.layout.contact_layout, container, false);
        ((TextView) resultView.findViewById(R.id.contact_layout_name)).setText(contactItem.getName());
        ((TextView) resultView.findViewById(R.id.contact_layout_telephone)).setText(contactItem.getTelephone());
        ((TextView) resultView.findViewById(R.id.contact_layout_email)).setText(contactItem.getEmail());
        ((TextView) resultView.findViewById(R.id.contact_layout_department)).setText(contactItem.getDepartment());
        ((TextView) resultView.findViewById(R.id.contact_layout_address)).setText(getResources().getString(R.string.address_prefix) + contactItem.getAddress());
        ImageView imageView = (ImageView) resultView.findViewById(R.id.contact_layout_photo);
        ImageLoader imageLoader = ImageLoader.instance();
        imageLoader.displayImage(contactItem.getPicture(), imageView);
        resultView.findViewById(R.id.contact_layout_send_email).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{contactItem.getEmail()});
                Intent mailer = Intent.createChooser(emailIntent, "");
                if (mailer != null)
                {
                    startActivity(mailer);
                } else
                {
                    Toast.makeText(getActivity(), "Не получается отправить email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        resultView.findViewById(R.id.contact_layout_make_call).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel: " + contactItem.getTelephone()));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException e)
                {
                    Toast.makeText(getActivity(), "Невозможно позвонить", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return resultView;
    }

    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);
        readFromBundle(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        readFromBundle(savedInstanceState);
    }

    private void readFromBundle(Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
        {
            contactItem = (ContactItem) savedInstanceState.getSerializable(CONTACT_ITEM_TAG);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CONTACT_ITEM_TAG, contactItem);
    }
}
