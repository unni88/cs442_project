package com.cs442.team3.con_venient;

import java.util.ArrayList;

public class DataModel {

    ArrayList<Event> events = new ArrayList<Event>();

    public DataModel(ArrayList<Event> events){
        super();
        this.events = events;
    }

    public ArrayList<Event> getEvents(){
        return events;
    }

    public void setEvents(ArrayList<Event> events){
        this.events = events;
    }

}
