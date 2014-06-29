package ru.hse.shugurov.bi_application.gui.fragments.lists;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import ru.hse.shugurov.bi_application.gui.adapters.ContactAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.ContactItemFragment;
import ru.hse.shugurov.bi_application.model.ContactItem;
import ru.hse.shugurov.bi_application.model.Parser;
import ru.hse.shugurov.bi_application.sections.SingleViewSection;

/**
 * Created by Иван on 15.06.2014.
 */
public class ContactsListFragment extends FragmentWithList
{
    @Override
    protected String getDataUrl()
    {
        return getSection().getUrl();
    }

    @Override
    protected ListAdapter getCurrentAdapter()
    {
        return getSection().getAdapter();
    }

    @Override
    protected ListAdapter getAdapter(String data)
    {
        ContactItem[] contactItems = Parser.parseContacts(data);
        ListAdapter adapter = new ContactAdapter(getActivity(), contactItems);
        return adapter;
    }

    @Override
    protected void setSectionAdapter(ListAdapter adapter)
    {
        getSection().setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        ContactItem item = getSelectedItem(parent, position);
        BaseFragment contactFragment = new ContactItemFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(ContactItemFragment.CONTACT_ITEM_TAG, item);
        arguments.putSerializable(BaseFragment.SECTION_TAG, getSection());
        contactFragment.setArguments(arguments);
        showNextFragment(contactFragment);
    }

    @Override
    public SingleViewSection getSection()
    {
        return (SingleViewSection) super.getSection();
    }
}
