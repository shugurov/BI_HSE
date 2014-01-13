package ru.hse.shugurov.gui.placeholders;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.model.Contact;
import ru.hse.shugurov.model.Section;

/**
 * Created by Иван on 07.01.14.
 */
public class ContactPlaceholderFragment extends SpecificItemPlaceholder
{
    private Contact contact;
    private Context context;

    public ContactPlaceholderFragment(Context context, Contact contact, MainActivity.FragmentChanged fragmentChanged, Section section, int sectionNumber)
    {
        super(context, fragmentChanged, section, sectionNumber);
        this.contact = contact;
        this.context = context;
        getFragmentChanged().setCurrentFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View resultView = null;
        resultView = inflater.inflate(R.layout.contact_layout, container, false);
        ((TextView) resultView.findViewById(R.id.contact_layout_name)).setText(contact.getName());
        ((TextView) resultView.findViewById(R.id.contact_layout_telephone)).setText(contact.getTelephone());
        ((TextView) resultView.findViewById(R.id.contact_layout_email)).setText(contact.getEmail());
        ((TextView) resultView.findViewById(R.id.contact_layout_department)).setText(contact.getDepartment());
        ((TextView) resultView.findViewById(R.id.contact_layout_address)).setText(getResources().getString(R.string.address_prefix) + contact.getAddress());
        resultView.findViewById(R.id.contact_layout_send_email).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.getEmail()});
                Intent mailer = Intent.createChooser(emailIntent, "");
                if (mailer != null)
                {
                    startActivity(mailer);
                } else
                {
                    Toast.makeText(context, "Не получается отправить email", Toast.LENGTH_SHORT).show();
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
                    callIntent.setData(Uri.parse("tel: " + contact.getTelephone()));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException e)
                {
                    Toast.makeText(getContext(), "Невозможно позвонить", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return resultView;
    }
}
