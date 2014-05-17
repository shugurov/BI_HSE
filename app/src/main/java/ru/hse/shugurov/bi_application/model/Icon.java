package ru.hse.shugurov.bi_application.model;

import java.io.Serializable;

/**
 * Created by Иван on 04.01.14.
 */
public class Icon implements Serializable
{
    private final int defaultIcon;
    private final int selectedIcon;

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


}
