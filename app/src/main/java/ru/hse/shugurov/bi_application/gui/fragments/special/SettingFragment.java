package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;

/**
 * Created by Иван on 28.01.14.
 */
public class SettingFragment extends BaseFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) ((ViewStub) rootView.findViewById(R.id.fragment_list_stub)).inflate();
        refreshLayout.setEnabled(false);
        final ListView listView = (ListView) refreshLayout.findViewById(R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.settings, android.R.layout.simple_list_item_multiple_choice);
        listView.setAdapter(adapter);
        listView.setItemChecked(0, preferences.getBoolean("enrolee", false));
        listView.setItemChecked(1, preferences.getBoolean("bs", false));
        listView.setItemChecked(2, preferences.getBoolean("ms_enrolee", false));
        listView.setItemChecked(3, preferences.getBoolean("ms", false));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                SharedPreferences.Editor editor = preferences.edit();
                switch (position)
                {
                    case 0:
                        editor.putBoolean("enrolee", !preferences.getBoolean("enrolee", false));
                        editor.apply();
                        break;
                    case 1:
                        editor.putBoolean("bs", !preferences.getBoolean("bs", false));
                        editor.apply();
                        break;
                    case 2:
                        editor.putBoolean("ms_enrolee", !preferences.getBoolean("ms_enrolee", false));
                        editor.apply();
                        break;
                    case 3:
                        editor.putBoolean("ms", !preferences.getBoolean("ms", false));
                        editor.apply();
                        break;
                }
            }
        });
        return rootView;
    }
}
