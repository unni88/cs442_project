package com.cs442.team3.con_venient;

import java.util.ArrayList;

/**
 * Created by wmei on 10/31/15.
 */
public class MyEvent {


    String name = null;
    String location = null;
    String dateTime = null;
    String notes = "";

    boolean selected = false;
    ArrayList<Booth> booths = new ArrayList<Booth>();

    public MyEvent(String name, boolean selected, String location, String dateTime){
        super();
        this.name = name;
        this.selected = selected;
        this.location = location;
        this.dateTime = dateTime;

    }

    public String getNotes(){return notes;}
    public void setNotes(){this.name = notes;}

    public String getName(){return name;}
    public void setName(){this.name = name;}

    public boolean isSelected(){return selected;}
    public void setSelected(boolean selected){this.selected = selected;}

    public ArrayList<Booth> getBooths() {return booths; }

    public void addBooth(Booth booth) {
        booths.add(booth);

    }

    public String getLocation(){return location;}
    public void setLocation(String location){this.location = location;}

    public String getDateTime(){return dateTime;}
    public void setDateTime(String dateTime){this.dateTime = dateTime;}


}
