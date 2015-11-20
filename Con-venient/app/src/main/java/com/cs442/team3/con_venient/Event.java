package com.cs442.team3.con_venient;

import java.util.List;

/**
 * Created by Unni on 11/15/15.
 */
public class Event {

    private String name;

    private String address;

    private String date; //Format is 10-Sep-2015

    private String time; //Format is 14:00

    private List<Booth> boothList;

    public Event(String name, String address, String time, String date, List<Booth> boothList) {
        this.name = name;
        this.address = address;
        this.time = time;
        this.date = date;
        this.boothList = boothList;
    }

}
