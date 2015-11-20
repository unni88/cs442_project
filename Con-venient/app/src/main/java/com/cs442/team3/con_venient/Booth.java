package com.cs442.team3.con_venient;

/**
 * Created by Unni on 11/19/15.
 */
public class Booth {
    private String boothName;

    private int boothid;

    private String website;

    private String phone;

    private String description;


    public Booth(String boothName, int boothid, String website, String phone, String description) {
        this.boothName = boothName;
        this.boothid = boothid;
        this.website = website;
        this.phone = phone;
        this.description = description;
    }
}
