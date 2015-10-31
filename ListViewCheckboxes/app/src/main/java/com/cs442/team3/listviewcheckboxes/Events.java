package com.cs442.team3.listviewcheckboxes;

/**
 * Created by wmei on 10/30/15.
 */
public class Events {

    String name = null;
    boolean selected = false;

    public Events(String name, boolean selected){
        super();
        this.name = name;
        this.selected = selected;

    }

    public String getName(){return name;}

    public void setName(){this.name = name;}

    public boolean isSelected(){return selected;}

    public void setSelected(boolean selected){this.selected = selected;}
}
