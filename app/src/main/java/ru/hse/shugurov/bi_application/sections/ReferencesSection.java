package ru.hse.shugurov.bi_application.sections;

/**
 * Created by Иван on 19.01.14.
 */
public class ReferencesSection extends Section
{
    private String[] references;

    public ReferencesSection(String title, int iconDefault, int iconSelected, int type, int size)
    {
        super(title, iconDefault, iconSelected, type);
        this.references = new String[size];
        for (int i = 0; i < references.length; i++)
        {
            references[i] = null;
        }
    }

    public String getReference(int position)
    {
        return references[position];
    }

    public void setReference(int position, String reference)
    {
        references[position] = reference;
    }

    public int getNumberOfReferences()
    {
        return references.length;
    }

}
