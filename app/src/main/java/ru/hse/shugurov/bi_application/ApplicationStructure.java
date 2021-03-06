package ru.hse.shugurov.bi_application;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.hse.shugurov.bi_application.model.Developer;
import ru.hse.shugurov.bi_application.model.Icon;
import ru.hse.shugurov.bi_application.model.Parser;
import ru.hse.shugurov.bi_application.sections.AboutAppSection;
import ru.hse.shugurov.bi_application.sections.AboutUsSection;
import ru.hse.shugurov.bi_application.sections.EventsSection;
import ru.hse.shugurov.bi_application.sections.MultipleAdaptersViewSection;
import ru.hse.shugurov.bi_application.sections.ReferencesSection;
import ru.hse.shugurov.bi_application.sections.Section;
import ru.hse.shugurov.bi_application.sections.SingleViewSection;

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
        sections = sectionList.toArray(new Section[sectionList.size()]);
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
        List<Section> sections = new ArrayList<Section>();
        next();
        next();
        try
        {
            parser.require(XmlPullParser.START_TAG, null, "structure");
            while (next() != XmlPullParser.END_TAG)
            {
                if (parser.getEventType() != XmlPullParser.START_TAG)
                {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("section"))
                {
                    sections.add(readSection());
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
        return sections;
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
        Section section;
        String title = "";
        Icon icon = null;
        String url = null;
        ArrayList<String> urls = null;
        int type = -1;
        try
        {
            parser.require(XmlPullParser.START_TAG, null, "section");
            next();
            title = readTitle();
            type = getType();
            icon = getIcon();
            boolean isFirst = true;
            while (parser.getName().equals("url"))
            {
                if (url != null)
                {
                    if (isFirst)
                    {
                        isFirst = false;
                        urls = new ArrayList<String>();
                        urls.add(url);
                    }
                    urls.add(readUrl());
                } else
                {
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
        if (urls == null)
        {
            switch (type)
            {
                case ContentTypes.BILLBOARD:
                    section = new MultipleAdaptersViewSection(title, icon.getDefaultIcon(), icon.getSelectedIcon(), type, 6, url);
                    break;
                case ContentTypes.SCHEDULE:
                    section = new ReferencesSection(title, icon.getDefaultIcon(), icon.getSelectedIcon(), type, 6);
                    Parser.parseSchedule(context, (ReferencesSection) section, url, "temp");//TOD а почему temp?
                    break;
                case ContentTypes.ABOUT_APP:
                    section = new AboutAppSection(title, icon.getDefaultIcon(), icon.getSelectedIcon(), type, getImageID(), getDevelopers());
                    break;
                case ContentTypes.ABOUT_US:
                    String heading = readText();
                    next();
                    String text = readText();
                    next();
                    section = new AboutUsSection(title, icon.getDefaultIcon(), icon.getSelectedIcon(), type, heading, text, getImageID());
                    break;
                default:
                    section = new SingleViewSection(title, icon.getDefaultIcon(), icon.getSelectedIcon(), url, type);
                    break;
            }
        } else
        {
            section = new EventsSection(title, icon.getDefaultIcon(), icon.getSelectedIcon(), urls.toArray(new String[urls.size()]), type);

        }

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
        Icon icon;
        next();
        next();
        String defaultString = readText();
        next();
        String selectedString = readText();
        int defaultId = context.getResources().getIdentifier(defaultString, "drawable", context.getPackageName());
        int selectedId = context.getResources().getIdentifier(selectedString, "drawable", context.getPackageName());
        next();
        next();
        icon = new Icon(defaultId, selectedId);
        return icon;
    }

    private String readUrl()
    {
        String url;
        next();
        url = readText();
        next();
        boolean isFirst = true;
        while (parser.getName().equals("url-parameter"))
        {
            next();
            NameValuePair pair = getParam();
            if (isFirst)
            {
                url += pair.getName() + "=" + pair.getValue();
                isFirst = false;
            } else
            {
                url += "&" + pair.getName() + "=" + pair.getValue();
            }
            next();
            next();
        }
        next();
        return url;
    }

    private int getType()
    {
        int type;
        next();
        String str = readText();
        type = Integer.parseInt(str);
        return type;
    }

    private String readText()
    {
        String text = "";
        if (next() == XmlPullParser.TEXT)
        {
            text = parser.getText();
            next();
        }
        return text;
    }

    private NameValuePair getParam()
    {

        String key = readText();
        next();
        String value = readText();
        return new BasicNameValuePair(key, value);
    }

    private int next()
    {
        try
        {
            return parser.next();
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    private Developer[] getDevelopers()
    {
        ArrayList<Developer> developers = new ArrayList<Developer>();
        next();
        while (parser.getName().equals("developer"))
        {
            next();
            String name = readText();
            next();
            String position = readText();
            next();
            developers.add(new Developer(getImageID(), name, position));
            next();
        }
        next();
        return developers.toArray(new Developer[developers.size()]);
    }

    private int getImageID()
    {
        String imageName = readText();
        int imageID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        next();
        return imageID;
    }
}
