package ru.hse.shugurov.bi_application.gui.fragments.items;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.shugurov.bi_application.R;
import ru.hse.shugurov.bi_application.gui.ImageViewProxy;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.sections.AboutUsSection;

/**
 * Created by Иван on 09.02.14.
 */
public class AboutUsFragment extends BaseFragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.about_us, container, false);
        AboutUsSection section = getSection();
        ImageView imageView = (ImageView) rootView.findViewById(R.id.about_us_image);
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), getSection().getImage());
        int screenWidth = getScreenWidth();//TODO попробовать брать ширину контейнера
        ImageViewProxy imageViewProxy = new ImageViewProxy(imageView, screenWidth / 2);
        imageViewProxy.setImageBitmap(bitmap);
        ((TextView) rootView.findViewById(R.id.about_us_heading)).setText(section.getHeading());
        ((TextView) rootView.findViewById(R.id.about_us_text)).setText(section.getText());
        return rootView;
    }

    @TargetApi(13)
    private int getScreenSizeAfterAPI13(Display display)
    {
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @SuppressWarnings("deprecation")
    private int getScreenSizeBeforeAPI13(Display display)
    {
        return display.getWidth();
    }

    /*determines api level and calls corresponding method to get screen width*/
    private int getScreenWidth()
    {
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 13)
        {
            return getScreenSizeAfterAPI13(display);
        } else
        {
            return getScreenSizeBeforeAPI13(display);
        }
    }

    @Override
    public AboutUsSection getSection()
    {
        return (AboutUsSection) super.getSection();
    }
}
