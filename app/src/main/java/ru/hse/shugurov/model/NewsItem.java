package ru.hse.shugurov.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Иван on 01.01.14.
 */
public class NewsItem implements Parcelable
{
    private String title;
    private String text;
    private String date;
    private String picture;
    private String summary;
    private int type;

    private NewsItem(Parcel parcel)
    {
    }

    public NewsItem(String title, String text, String date, String picture, String summary, int type)
    {
        this.title = title;
        this.text = text;
        this.date = date;
        this.picture = picture;
        this.summary = summary;
        this.type = type;
    }

    public String getSummary()
    {
        return summary;
    }


    public String getPicture()
    {
        return picture;
    }

    public String getTitle()
    {
        return title;
    }

    public String getText()
    {
        return text;
    }

    public String getDate()
    {
        return date;
    }

    public int getType()
    {
        return type;
    }

    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>()
    {
        @Override
        public NewsItem createFromParcel(Parcel source)
        {
            return new NewsItem(source);
        }

        @Override
        public NewsItem[] newArray(int size)
        {
            return new NewsItem[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

    }
}
