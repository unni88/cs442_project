package com.cs442.team3.con_venient;

/**
 * Created by wmei on 10/31/15.
 */
public class Booth {

    int id = 0;
    String name = null;
    String website = null;
    String contactInfo = null;
    String descriptions = null;

    public Booth( String name, String website, String contactInfo, String descriptions){
        super();
        //this.id = id;
        this.name = name;
        this.website = website;
        this.contactInfo = contactInfo;
        this.descriptions = descriptions;

    }

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getWebsite(){return website;}
    public void setWebsite(String website){this.website = website;}

    public String getContactInfo(){return contactInfo;}
    public void setContactInfo(String contactInfo){this.contactInfo=contactInfo;}

    public String getDescriptions(){return descriptions;}
    public void setDescriptions(String descriptions){this.descriptions = descriptions;}

    public int getId(){return id;}
    public void setId(int id){this.id = id;}




}
