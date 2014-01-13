package ru.hse.shugurov.model;

/**
 * Created by Иван on 04.01.14.
 */
public class Icon
{
    public int getDefaultIcon()
    {
        return defaultIcon;
    }

    public int getSelectedIcon()
    {
        return selectedIcon;
    }

    public Icon(int defaultIcon, int selectedIcon)
    {
        this.defaultIcon = defaultIcon;
        this.selectedIcon = selectedIcon;
    }

    private final int defaultIcon;
    private final int selectedIcon;
}
