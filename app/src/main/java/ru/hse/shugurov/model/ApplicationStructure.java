package ru.hse.shugurov.model;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.apache.http.NameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.hse.shugurov.R;

/**
 * Created by Иван on 04.01.14.
 */
public class ApplicationStructure
{
    private static ApplicationStructure structure;
    private static Context context;
    private XmlResourceParser parser;
    private Section[] sections;

    private ApplicationStructure()
    {
        if (context == null)
        {
            throw new IllegalStateException("context can't be equal to null");
        }
        parser = context.getResources().getXml(R.xml.structure);
        List<Section> sectionList = readStructure();
        sections = (Section[]) sectionList.toArray(new Section[sectionList.size()]);
        //anotherRad();
    }

    public static void setContext(Context cont)
    {
        context = cont;
    }

    public static ApplicationStructure getStructure()
    {
        if (structure == null)
        {
            structure = new ApplicationStructure();
        }
        return structure;
    }

    public Section[] getSections()
    {
        return sections;
    }

    private List<Section> readStructure()
    {
        List<Section> entries = new ArrayList<Section>();
        try
        {
            parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            parser.require(XmlPullParser.START_TAG, null, "structure");
            while (parser.next() != XmlPullParser.END_TAG)
            {
                if (parser.getEventType() != XmlPullParser.START_TAG)
                {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("section"))
                {
                    entries.add(readSection());
                } else
                {
                    skip();
                }
            }
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return entries;
    }

    private void skip()
    {
        try
        {
            if (parser.getEventType() == XmlPullParser.END_TAG)
            {
            } else
            {
                int depth = 1;
                while (depth != 0)
                {
                    switch (parser.next())
                    {
                        case XmlPullParser.END_TAG:
                            depth--;
                            break;
                        case XmlPullParser.START_TAG:
                            depth++;
                            break;
                    }
                }
            }
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private Section readSection()
    {
        Section section = null;
        String title = "";
        Icon icon = null;
        String url = "";
        int type = -1;
        try
        {
            parser.require(XmlPullParser.START_TAG, null, "section");
            while (parser.next() != XmlPullParser.END_TAG)
            {
                if (parser.getEventType() != XmlPullParser.START_TAG)
                {
                    continue;
                } else
                {
                    title = readTitle();
                    type = getType();
                    icon = getIcon();
                    url = readUrl();
                }
            }
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        section = new Section(title, icon.getDefaultIcon(), icon.getSelectedIcon(), url, type);
        return section;
    }

    private String readTitle()
    {
        String title = "";
        try
        {
            parser.require(XmlPullParser.START_TAG, null, "title");
            title = readText();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return title;
    }

    private Icon getIcon()
    {
        Icon icon = null;
        try
        {
            parser.require(XmlPullParser.START_TAG, null, "icon");
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        String defaultString = readText();
        try
        {
            parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        String selectedString = readText();
        int defaultId = context.getResources().getIdentifier(defaultString, "drawable", context.getPackageName());
        int selectedId = context.getResources().getIdentifier(selectedString, "drawable", context.getPackageName());
        ;
        icon = new Icon(defaultId, selectedId);
        return icon;
    }

    private String readUrl()
    {
        String url = "";
        try
        {
            parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        url = readText();
        int tagEvent = XmlPullParser.END_TAG;
        try
        {
            tagEvent = parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        while (tagEvent != XmlPullParser.END_TAG)
        {
            getParam();
        }
        return url;
    }

    private int getType()
    {
        int type = -1;
        try
        {
            parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        String str = readText();
        type = Integer.parseInt(str);
        return type;
    }

    private String readText()
    {
        String text = "";
        try
        {
            if (parser.next() == XmlPullParser.TEXT)
            {
                text = parser.getText();
                parser.nextTag();
            }
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    private NameValuePair getParam()
    {
        String key = parser.getText();
        try
        {
            parser.next();
            parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        String value = parser.getName();
        return null;
    }
}
