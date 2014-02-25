package ru.hse.shugurov.gui.placeholders.special;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.MainActivity;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.sections.ReferencesSection;
import ru.hse.shugurov.sections.Section;

/**
 * Created by Иван on 20.01.14.
 */
public class SchedulePlaceholderFragment extends PlaceholderFragment implements View.OnClickListener
{


    public SchedulePlaceholderFragment(Context context, MainActivity.FragmentListener fragmentListener, Section section, int sectionNumber)
    {
        super(context, fragmentListener, section);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.schedule, container, false);
        ReferencesSection referencesSection = (ReferencesSection) getSection();
        rootView.findViewById(R.id.schedule_bs1).setOnClickListener(this);
        rootView.findViewById(R.id.schedule_bs2).setOnClickListener(this);
        rootView.findViewById(R.id.schedule_bs3).setOnClickListener(this);
        rootView.findViewById(R.id.schedule_bs4).setOnClickListener(this);
        rootView.findViewById(R.id.schedule_ms1).setOnClickListener(this);
        rootView.findViewById(R.id.schedule_ms2).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view)
    {
        String url = null;
        switch (view.getId())
        {
            case R.id.schedule_bs1:
                url = ((ReferencesSection) getSection()).getReference(0);
                break;
            case R.id.schedule_bs2:
                url = ((ReferencesSection) getSection()).getReference(1);
                break;
            case R.id.schedule_bs3:
                url = ((ReferencesSection) getSection()).getReference(2);
                break;
            case R.id.schedule_bs4:
                url = ((ReferencesSection) getSection()).getReference(3);
                break;
            case R.id.schedule_ms1:
                url = ((ReferencesSection) getSection()).getReference(4);
                break;
            case R.id.schedule_ms2:
                url = ((ReferencesSection) getSection()).getReference(5);
                break;
        }
        if (url == null)
        {
            Toast.makeText(getContext(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.parse(url);
        DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(new DownloadManager.Request(uri));
    }
}
