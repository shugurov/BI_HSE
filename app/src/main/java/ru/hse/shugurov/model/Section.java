package ru.hse.shugurov.model;

/**
 * Created by Иван on 02.01.14.
 */
public class Section
{
    private String title;
    private int iconDefault;
    private int iconSelected;
    private int type;


    public Section(String title, int iconDefault, int iconSelected, int type)
    {
        this.title = title;
        this.iconDefault = iconDefault;
        this.iconSelected = iconSelected;
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public String getTitle()
    {
        return title;
    }

    public int getIconDefault()
    {
        return iconDefault;
    }

    public int getIconSelected()
    {
        return iconSelected;
    }


}
