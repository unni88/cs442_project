package com.cs442.team3.con_venient;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class DataModel {

    ArrayList<MyEvent> events = new ArrayList<MyEvent>();


    public void hardCoded(){
        /*
        this.name = name;
        this.website = website;
        this.contactInfo = contactInfo;
        this.descriptions = descriptions;*/

        ArrayList<MyEvent> eventsList = new ArrayList<MyEvent>();
        MyEvent event = new MyEvent("Career Fair", false,"Hermann Hall, 3241 S Federal St, Chicago, IL 60616","Wednesday, October 7, 1-5 pm ");
            event.booths.add(new Booth("Google","www.google.com", "888-656-4323", "google is hiring ...."));
        eventsList.add(event);
        event = new MyEvent("Fair 1", false,"location1","dateTime1");
        eventsList.add(event);
        event = new MyEvent("Fair 2", false,"location2","dateTime2");
        eventsList.add(event);
        event = new MyEvent("Fair 3", false,"location3","dateTime3");
        eventsList.add(event);
        event = new MyEvent("Fair 4", false,"location4","dateTime4");
        eventsList.add(event);
        event = new MyEvent("Fair 5", false,"location5","dateTime5");
        eventsList.add(event);
        event = new MyEvent("Fair 6", false,"location6","dateTime6");
        eventsList.add(event);
        event = new MyEvent("Fair 7", false,"location7","dateTime7");
        eventsList.add(event);
        event = new MyEvent("Fair 8", false,"location8","dateTime8");
        eventsList.add(event);
        event = new MyEvent("Fair 9", false,"location9","dateTime9");
        eventsList.add(event);

        events = eventsList;

    }

    public DataModel(){
        super();
        this.hardCoded();
    }

    public ArrayList<MyEvent> getEvents(){
        return events;
    }

    public void setEvents(ArrayList<MyEvent> events){
        this.events = events;
    }
    public void exportData(Context context){
        if (!isExternalStorageWritable())
            Toast.makeText(context,"SD Card is unmounted.",Toast.LENGTH_LONG).show();
        else {
            //Toast.makeText(this,"SD Card is mounted.",Toast.LENGTH_LONG).show();
            File file = new File(context.getExternalFilesDir(null), "con_venient.txt");

            //Toast.makeText(this,"Path: "+this.getExternalFilesDir(null).getPath(),Toast.LENGTH_LONG).show();
            try {
                Iterator<MyEvent> i = events.iterator();
                String result = "";
                while (i.hasNext()) {
                    MyEvent temp = i.next();
                    if(temp.isSelected())
                    result += temp.getName() + "\n";
                }
                FileWriter fw = new FileWriter(file);
                fw.write(result);
                fw.close();
                Toast.makeText(context,"ToDoList exported.",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.w("ExternalStorage", "Error writing " + file, e);
            }
        }
    }

    public void importData(Context context){
        if (!isExternalStorageWritable())
            Toast.makeText(context,"SD Card is unmounted.",Toast.LENGTH_LONG).show();
        else{
            //Toast.makeText(this,"SD Card is mounted.",Toast.LENGTH_LONG).show();
            File file = new File(context.getExternalFilesDir(null),"A5_ToDoList.txt");
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String eventname;
                while((eventname = br.readLine())!=null) {
                    Iterator<MyEvent> i = events.iterator();
                    while (i.hasNext()) {
                        MyEvent temp = i.next();
                        if (temp.getName().equalsIgnoreCase(eventname)) {
                            temp.selected = true;
                            continue;
                        }
                    }
                }
                fr.close();
            } catch (IOException e) {
                Log.w("ExternalStorage", "Error reading " + file, e);
            }
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        Boolean state = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        return state;
    }
}
