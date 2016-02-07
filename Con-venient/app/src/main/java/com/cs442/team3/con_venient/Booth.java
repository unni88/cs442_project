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

    private String requirements;

    private String typeofposition;

    private String positionhiring;

    private String majors;

    public String getRequirements() {
        return requirements;
    }

    public String getTypeofposition() {
        return typeofposition;
    }

    public String getPositionhiring() {
        return positionhiring;
    }

    public String getMajors() {
        return majors;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public void setTypeofposition(String typeofposition) {
        this.typeofposition = typeofposition;
    }

    public void setPositionhiring(String positionhiring) {
        this.positionhiring = positionhiring;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }

    public Booth(String boothName, int boothid, String website, String phone, String description) {
        this.boothName = boothName;
        this.boothid = boothid;
        this.website = website;
        this.phone = phone;
        this.description = description;
    }

    public String getBoothName() {
        return boothName;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName;
    }

    public int getBoothid() {
        return boothid;
    }

    public void setBoothid(int boothid) {
        this.boothid = boothid;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
