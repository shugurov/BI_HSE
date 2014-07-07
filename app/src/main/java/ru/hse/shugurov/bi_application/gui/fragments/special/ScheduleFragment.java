package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;

import ru.hse.shugurov.bi_application.FileDescription;
import ru.hse.shugurov.bi_application.FileDownloader;
import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.sections.ReferencesSection;

/**
 * Created by Иван on 20.01.14.
 */
public class ScheduleFragment extends BaseFragment implements View.OnClickListener
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.schedule, container, false);
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
        switch (view.getId())//TODO почему все ссылки null?
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
            handleLoadProblem();
            return;
        }
        FileDownloader downloader = new FileDownloader(getActivity(), new FileDownloader.DownloadCallback()
        {
            @Override
            public void downloadFinished()
            {//TODO прогресс бар?
                File file = new File(getActivity().getFilesDir(), "lol.xlsx");//TODO ;(
                if (file.exists())
                {
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.setDataAndType(Uri.fromFile(file), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    try
                    {
                        Intent chooser = Intent.createChooser(target, "Open a file");
                        getActivity().startActivity(chooser);
                    } catch (ActivityNotFoundException e)
                    {
                        Toast.makeText(getActivity(), "Нет приложений, способных открыть этот тип фалов", Toast.LENGTH_SHORT).show();
                    }

                } else
                {
                    Toast.makeText(getActivity(), "Не удалось открыть файл", Toast.LENGTH_SHORT).show();
                }
            }
        });
        FileDescription fileDescription = new FileDescription("lol.xlsx", url);//TODO file name
        downloader.execute(fileDescription);
    }
}
