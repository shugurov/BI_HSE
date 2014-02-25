package ru.hse.shugurov.gui;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Иван on 25.02.14.
 */
public class FlexibleImageView extends ImageView
{
    private ImageView realImage;
    private int width;

    public FlexibleImageView(ImageView realImage, int width)
    {
        super(realImage.getContext());
        this.realImage = realImage;
        this.width = width;
    }


    @Override
    public void setImageBitmap(Bitmap bitmap)
    {
        double aspectRatio = ((double) bitmap.getHeight()) / bitmap.getWidth();
        int height = (int) Math.round(width * aspectRatio);
        realImage.getLayoutParams().height = height;
        realImage.setImageBitmap(bitmap);
    }
}
