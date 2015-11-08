package com.cs442.team3.con_venient;

import java.util.ArrayList;

public class DataModel {

    ArrayList<Event> events = new ArrayList<Event>();


    public void hardCoded(){

        ArrayList<Event> eventsList = new ArrayList<Event>();
        Event event = new Event("Career Fair", false);
        eventsList.add(event);
        event = new Event("Fair 1", false);
        eventsList.add(event);
        event = new Event("Fair 2", false);
        eventsList.add(event);
        event = new Event("Fair 3", false);
        eventsList.add(event);
        event = new Event("Fair 4", false);
        eventsList.add(event);
        event = new Event("Fair 5", false);
        eventsList.add(event);
        event = new Event("Fair 6", false);
        eventsList.add(event);
        event = new Event("Fair 7", false);
        eventsList.add(event);
        event = new Event("Fair 8", false);
        eventsList.add(event);
        event = new Event("Fair 9", false);
        eventsList.add(event);
        event = new Event("Fair 10", false);
        eventsList.add(event);
        event = new Event("Fair 11", false);
        eventsList.add(event);
        event = new Event("Fair 12", false);
        eventsList.add(event);

        events = eventsList;

    }

    public DataModel(){
        super();
        this.hardCoded();
    }

    public ArrayList<Event> getEvents(){
        return events;
    }

    public void setEvents(ArrayList<Event> events){
        this.events = events;
    }
}
