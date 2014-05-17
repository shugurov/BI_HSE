package ru.hse.shugurov.bi_application.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Иван on 09.03.14.
 */
public class NonScrollableGridView extends GridView//TODO do I use it at all?
{
    public NonScrollableGridView(Context context)
    {
        super(context);
    }

    public NonScrollableGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public NonScrollableGridView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
        getLayoutParams().height = getMeasuredHeight();
    }
}
