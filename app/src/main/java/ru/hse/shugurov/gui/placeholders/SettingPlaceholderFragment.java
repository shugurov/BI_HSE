package ru.hse.shugurov.gui.placeholders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.sections.Section;

/**
 * Created by Иван on 28.01.14.
 */
public class SettingPlaceholderFragment extends PlaceholderFragment
{
    private SharedPreferences preferences;

    public SettingPlaceholderFragment(Context context, MainActivity.FragmentListener fragmentListener, Section section, int sectionNumber)
    {
        super(context, fragmentListener, section);
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        final ListView listView = (ListView) ((ViewStub) rootView.findViewById(R.id.fragment_list_stub)).inflate();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.settings, android.R.layout.simple_list_item_multiple_choice);
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
                        editor.commit();
                        break;
                    case 1:
                        editor.putBoolean("bs", !preferences.getBoolean("bs", false));
                        editor.commit();
                        break;
                    case 2:
                        editor.putBoolean("ms_enrolee", !preferences.getBoolean("ms_enrolee", false));
                        editor.commit();
                        break;
                    case 3:
                        editor.putBoolean("ms", !preferences.getBoolean("ms", false));
                        editor.commit();
                        break;
                }
            }
        });
        return rootView;
    }
}
