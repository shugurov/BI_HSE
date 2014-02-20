package ru.hse.shugurov;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by User on 20.02.14.
 */
public class FlexibleImageView extends ImageView {
    private ImageView realImageView;
    private float leftMargin;
    private float rightMargin;
    private int screenWidth;

    public FlexibleImageView(ImageView realImageView, int screenWidth) {
        super(realImageView.getContext());
        this.realImageView = realImageView;
        this.screenWidth = screenWidth;
    }

    public FlexibleImageView(ImageView realImageView, int screenWidth, float leftMargin, float rightMargin)
    {
        this(realImageView, screenWidth);
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        float width = screenWidth - leftMargin - rightMargin;
        float i =  width / ((float) bitmap.getWidth());
        float imageHeight = i * (bitmap.getHeight());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)width, (int) imageHeight);
        layoutParams.setMargins((int)leftMargin, 0, 0, 0);
        realImageView.setLayoutParams(layoutParams);
        realImageView.setImageBitmap(bitmap);
    }

}
