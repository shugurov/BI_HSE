package ru.hse.shugurov.gui.placeholders;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.model.ProjectItem;

/**
 * Created by Иван on 06.01.14.
 */
public class ProjectItemPlaceholderFragment extends SpecificItemPlaceholder
{
    private ProjectItem item;
    public ProjectItemPlaceholderFragment(Context context, ProjectItem item, MainActivity.FragmentChanged fragmentChanged, int sectionNumber)
    {
        super(context,fragmentChanged ,sectionNumber);
        this.item = item;
        getFragmentChanged().setCurrentFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.project_layout, container, false);
        ((TextView)rootView.findViewById(R.id.project_layout_text)).setText(item.getText());
        rootView.findViewById(R.id.project_layout_details).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                startActivity(browserIntent);
            }
        });
        return rootView;
    }
}
