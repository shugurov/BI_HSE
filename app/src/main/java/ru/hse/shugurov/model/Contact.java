package ru.hse.shugurov.model;

/**
 * Created by Иван on 06.01.14.
 */
public class Contact
{
    private String address;
    private String url;
    private String name;
    private String picture;
    private String email;
    private String telephone;
    private String department;

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

    public Contact(String address, String url, String name, String picture, String email, String telephone, String department)
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
