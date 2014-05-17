package ru.hse.shugurov.bi_application.sections;

import ru.hse.shugurov.bi_application.model.Developer;

/**
 * Created by Иван on 09.02.14.
 */
public class AboutAppSection extends Section
{
    private final int image;
    private final Developer[] developers;

    public AboutAppSection(String title, int iconDefault, int iconSelected, int type, int image, Developer[] developers)
    {
        super(title, iconDefault, iconSelected, type);
        this.image = image;
        this.developers = developers;
    }

    public int getNumberOfDevelopers()
    {
        return developers.length;
    }

    public Developer getDeveloper(int position)
    {
        return developers[position];
    }


    public int getImage()
    {
        return image;
    }

    public Developer[] getDevelopers()
    {
        return developers;
    }
}
