package ru.hse.shugurov.bi_application.gui.fragments.special;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;

import ru.hse.shugurov.bi_application.Downloader;
import ru.hse.shugurov.bi_application.FileDescription;
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
            Toast.makeText(getActivity(), "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
            return;
        }
        /*Downloader downloader = new Downloader(new CallBack() //TODO remove?
        {
            @Override
            public void call(String[] results)
            {
                FileManager fileManager = FileManager.instance();
                //fileCache.add("lol.xlsx", results[0]);
                File file = new File(getActivity().getCacheDir(), "lol.xlsx");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(file.getAbsolutePath()));
                try
                {
                    startActivity(intent);
                } catch (Exception e)
                {
                    Toast.makeText(getActivity(), "Не получилось открыть файл", Toast.LENGTH_SHORT).show();
                }
            }
        });
        downloader.execute(url);*/

        Downloader downloader = new Downloader(getActivity(), new Downloader.DownloadCallback()
        {
            @Override
            public void downloadFinished()
            {
                File file = new File(getActivity().getFilesDir(), "lol.xlsx");//TODO
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(file.getAbsolutePath()));
                try
                {
                    startActivity(intent);
                } catch (Exception e)
                {
                    Toast.makeText(getActivity(), "Не получилось открыть файл", Toast.LENGTH_SHORT).show();
                }
            }
        });
        FileDescription fileDescription = new FileDescription("lol.xlsx", url);//TODO file name
        downloader.execute(fileDescription);
    }
}
