package ru.hse.shugurov.model;

/**
 * Created by Иван on 06.01.14.
 */
public class ContactItem
{
    private final String address;
    private final String url;
    private final String name;
    private final String picture;
    private final String email;
    private final String telephone;
    private final String department;

    public String getDepartment()
    {
        return department;
    }

    public String getAddress()
    {
        return address;
    }

    public String getUrl()
    {
        return url;
    }

    public String getName()
    {
        return name;
    }

    public String getPicture()
    {
        return picture;
    }

    public String getEmail()
    {
        return email;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public ContactItem(String address, String url, String name, String picture, String email, String telephone, String department)
    {
        this.address = address;
        this.url = url;
        this.name = name;
        this.picture = picture;
        this.email = email;
        this.telephone = telephone;
        this.department = department;
    }
}
