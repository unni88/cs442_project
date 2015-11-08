package com.cs442.team3.con_venient;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataModel {

    ArrayList<Event> events = new ArrayList<Event>();


    public void hardCoded(){
        /*
        this.name = name;
        this.website = website;
        this.contactInfo = contactInfo;
        this.descriptions = descriptions;*/

        ArrayList<Event> eventsList = new ArrayList<Event>();
        Event event = new Event("Career Fair", false);
            event.booths.add(new Booth("Google","www.google.com", "888-656-4323", "google is hiring ...."));
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
    public void exportData(Context context){
        /*if (!isExternalStorageWritable())
            Toast.makeText(context,"SD Card is unmounted.",Toast.LENGTH_LONG).show();
        else {
            //Toast.makeText(this,"SD Card is mounted.",Toast.LENGTH_LONG).show();
            File file = new File(context.getExternalFilesDir(null), "A5_ToDoList.txt");

            //Toast.makeText(this,"Path: "+this.getExternalFilesDir(null).getPath(),Toast.LENGTH_LONG).show();
            try {
                Iterator<> i = todoItems.iterator();
                String result = "";
                while (i.hasNext()) {
                    ToDoItem temp = i.next();
                    result += temp.storeItem() + "\n";
                }
                FileWriter fw = new FileWriter(file);
                fw.write(result);
                fw.close();
                Toast.makeText(context,"ToDoList exported.",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.w("ExternalStorage", "Error writing " + file, e);
            }
        }*/
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        Boolean state = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        return state;
    }
}
