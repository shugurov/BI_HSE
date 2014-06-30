package ru.hse.shugurov.bi_application.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Иван on 01.01.14.
 */
public class NewsItem implements Parcelable
{
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
    private String title;
    private String text;
    private String date;
    private String picture;
    private String summary;
    private int type;

    public NewsItem(String title, String text, String date, String picture, String summary, int type)
    {
        this.title = title;
        this.text = text;
        this.date = date;
        this.picture = picture;
        this.summary = summary;
        this.type = type;
    }

    protected NewsItem(Parcel source)
    {
        title = source.readString();
        text = source.readString();
        date = source.readString();
        picture = source.readString();
        summary = source.readString();
        type = source.readInt();
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

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(date);
        dest.writeString(picture);
        dest.writeString(summary);
        dest.writeInt(type);
    }
}
