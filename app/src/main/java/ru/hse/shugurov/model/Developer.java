package ru.hse.shugurov.model;

/**
 * Created by Иван on 09.02.14.
 */
public class Developer
{
    private final int photo;
    private final String name;
    private final String position;

    public Developer(int photo, String name, String position)
    {
        this.photo = photo;
        this.name = name;
        this.position = position;
    }

    public String getPosition()
    {
        return position;
    }

    public int getPhoto()
    {
        return photo;
    }

    public String getName()
    {
        return name;
    }

}
