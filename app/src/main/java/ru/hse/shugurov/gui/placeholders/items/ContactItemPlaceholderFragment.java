package ru.hse.shugurov.gui.placeholders.items;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ru.hse.shugurov.ImageLoader;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.adapters.ContactAdapter;
import ru.hse.shugurov.model.ContactItem;
import ru.hse.shugurov.sections.Section;
import ru.hse.shugurov.sections.SingleViewSection;

/**
 * Created by Иван on 07.01.14.
 */
public class ContactItemPlaceholderFragment extends SpecificItemPlaceholder
{
    private ContactItem contactItem;
    private Context context;

    public ContactItemPlaceholderFragment(Context context, ContactItem contactItem, MainActivity.FragmentListener fragmentListener, Section section)
    {
        super(context, fragmentListener, section);
        this.contactItem = contactItem;
        this.context = context;
    }

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
        ImageLoader imageLoader = ((ContactAdapter) ((SingleViewSection) getSection()).getAdapter()).getImageLoader();
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
                    callIntent.setData(Uri.parse("tel: " + contactItem.getTelephone()));
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
