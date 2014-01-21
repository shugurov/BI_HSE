package ru.hse.shugurov.sections;

/**
 * Created by Иван on 19.01.14.
 */
public class ReferencesSection extends Section
{
    private String[] references;

    public ReferencesSection(String title, int iconDefault, int iconSelected, int type, int numberOfReferences)
    {
        super(title, iconDefault, iconSelected, type);
        references = new String[numberOfReferences];
        for (int i = 0; i < numberOfReferences; i++)
        {
            references[i] = null;
        }
    }

    private String getReference(int position)
    {
        return references[position];
    }

    private void setReference(int position, String reference)
    {
        references[position] = reference;
    }

    public int getNumberOfReferences()
    {
        return references.length;
    }
}
