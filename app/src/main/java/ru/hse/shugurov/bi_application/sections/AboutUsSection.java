package ru.hse.shugurov.bi_application.sections;

/**
 * Created by Иван on 09.02.14.
 */
public class AboutUsSection extends Section
{
    private final String heading;
    private final String text;
    private final int image;

    public AboutUsSection(String title, int iconDefault, int iconSelected, int type, String heading, String text, int image)
    {
        super(title, iconDefault, iconSelected, type);
        this.heading = heading;
        this.text = text;
        this.image = image;
    }

    public String getText()
    {
        return text;
    }

    public int getImage()
    {
        return image;
    }

    public String getHeading()
    {
        return heading;
    }
}
