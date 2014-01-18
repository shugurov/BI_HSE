package ru.hse.shugurov.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Иван on 04.01.14.
 */
public class Parser
{
    public static NewsItem[] parseNews(String str)
    {
        str = str.replaceAll("News\":", "\":");
        str = str.replaceAll("Activity\":", "\":");
        JSONArray array;
        try
        {
            array = new JSONArray(str);

        } catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        NewsItem[] items = new NewsItem[array.length()];
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject jsonObject = null;
            try
            {
                jsonObject = array.getJSONObject(i);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            String title = "";
            try
            {
                title = jsonObject.getString("name");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            String date = "";
            try
            {
                date = jsonObject.getString("date");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            String text = "";
            try
            {
                text = jsonObject.getString("txt");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            String summary = "";
            try
            {
                summary = jsonObject.getString("shorttxt");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            String picture = "";
            try
            {
                picture = jsonObject.getString("img");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            int type = -1;
            try
            {
                type = jsonObject.getInt("type");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            items[i] = new NewsItem(title, text, date, picture, summary, type);
        }
        return items;
    }

    public static ProjectItem[] parseProjects(String jsonString)
    {
        ProjectItem[] items = null;
        jsonString = jsonString.replaceAll("Volunteering\":", "\":"); //TODO replace with regex
        jsonString = jsonString.replaceAll("Projects\":", "\":");
        JSONArray jsonArray = null;
        try
        {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if (jsonArray != null)
        {
            items = new ProjectItem[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject item = null;
                ProjectItem projectItem = null;
                try
                {
                    item = jsonArray.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                if (item != null)
                {
                    String text = "";
                    String url = "";
                    String headline = "";
                    String pictureUrl = "";
                    try
                    {
                        text = item.getString("txt");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        url = item.getString("ref");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        headline = item.getString("name");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        pictureUrl = item.getString("img");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    projectItem = new ProjectItem(text, url, headline, pictureUrl);
                }
                items[i] = projectItem;
            }
        }
        return items;
    }

    public static ContactItem[] parseContacts(String jsonString)
    {
        ContactItem[] contactItems = null;
        JSONArray array = null;
        try
        {
            array = new JSONArray(jsonString);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if (array != null)
        {
            contactItems = new ContactItem[array.length()];
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = null;
                try
                {
                    jsonObject = array.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                if (jsonObject != null)
                {
                    String adress = "";
                    String url = "";
                    String name = "";
                    String picture = "";
                    String email = "";
                    String telephone = "";
                    String department = "";
                    try
                    {
                        adress = jsonObject.getString("adressContacts");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        url = jsonObject.getString("refContacts");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        name = jsonObject.getString("nameContacts");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        picture = jsonObject.getString("imgContacts");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        email = jsonObject.getString("emailContacts");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        telephone = jsonObject.getString("telnumbContacts");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        department = jsonObject.getString("departmentContacts");
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    contactItems[i] = new ContactItem(adress, url, name, picture, email, telephone, department);
                }
            }
        }
        return contactItems;
    }

    public static Advert[] parseAdverts(String str)
    {
        Advert[] advertItems = null;
        JSONArray adverts = null;
        try
        {
            adverts = new JSONArray(str);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if (adverts != null)
        {
            advertItems = new Advert[adverts.length()];
            for (int i = 0; i < advertItems.length; i++)
            {
                JSONObject jsonObject = null;
                String title = "";
                String text = "";
                int course = 0;
                try
                {
                    jsonObject = adverts.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                try
                {
                    title = jsonObject.getString("dayCallboard");
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    text = jsonObject.getString("txtCallboard");
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    course = jsonObject.getInt("courseCallboard");
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                advertItems[i] = new Advert(title, text, course);
            }
        }
        return advertItems;
    }
}
