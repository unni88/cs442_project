package com.cs442.team3.con_venient;

import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class DataModel {

    ArrayList<MyEvent> events = new ArrayList<MyEvent>();

    int help =0;

    public void hardCoded(){

        ArrayList<MyEvent> eventsList = new ArrayList<MyEvent>();
        MyEvent event = new MyEvent("Career Fair", false,"Hermann Hall, 3241 S Federal St, Chicago, IL 60616","11/21/2015  11:14:12"); /*MM/DD/YYYY HH:mm:ss*/
            event.setMap("http://cmc.iit.edu:1122/hermann_hall.jpg");
            event.booths.add(new Booth("Google",0,"www.google.com", "888-674-1303", "google is hiring ...."));
            event.setNotes("asdasdasdasd");
            event.booths.add(new Booth("Test1",0, "www.test1.com", "phone1", "descript 1"));
            event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
            event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));

        eventsList.add(event);
        event = new MyEvent("Fair 1", false,"location1","11/21/2015  13:01:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 2", false,"location2","09/17/2015  11:14:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 3", false,"location3","09/17/2015  11:16:12");
        event.booths.add(new Booth("Test1",0, "www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 4", false,"location4","11/24/2015  11:14:12");
        event.booths.add(new Booth("Test1",0, "www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2",1, "www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 5", false,"location5","12/05/2015  11:14:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 6", false,"location6","12/06/2015  11:14:12");
        event.booths.add(new Booth("Test1",0, "www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 7", false,"location7","12/07/2015  14:14:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 8", false,"location8","12/08/2015  15:14:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 9", false,"location9","12/09/2015  15:14:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 10", false,"location10","12/07/2015  17:14:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 11", false,"location11","12/07/2015  18:14:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 12", false,"location12","12/07/2015  18:24:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 13", false,"location13","12/07/2015  18:34:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 14", false,"location14","12/07/2015  19:14:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        event = new MyEvent("Fair 15", false,"location15","12/07/2015  19:34:12");
        event.booths.add(new Booth("Test1", 0,"www.test1.com", "phone1", "descript 1"));
        event.booths.add(new Booth("Test2", 1,"www.test2.com", "phone2", "descript 2"));
        event.booths.add(new Booth("Test3", 2,"www.test3.com", "phone3", "descript 3"));
        eventsList.add(event);

        events = eventsList;



    }

    public void getDataFromService(){
        events =  DataFromWebService.getDataFromWebService();
    }

    public DataModel(){
        super();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            getDataFromService();

        }

        //this.hardCoded();
    }

    public int getHelp(){
        return this.help;
    }
    public void setHelp(int help){
     this.help = help;

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
            //Toast.makeText(context,"SD Card is mounted.",Toast.LENGTH_LONG).show();
            File file = new File(context.getExternalFilesDir(null), "con_venient.txt");

            //Toast.makeText(context,"Path: "+context.getExternalFilesDir(null).getPath(),Toast.LENGTH_LONG).show();
            try {
                Iterator<MyEvent> i = events.iterator();
                String result = "";
                while (i.hasNext()) {
                    MyEvent temp = i.next();
                    if(temp.isSelected())
                        result += temp.storeEvent() + "\n";
                }
                FileWriter fw = new FileWriter(file);
                fw.write(result);
                fw.close();
                //Toast.makeText(context,"exported.",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.w("ExternalStorage", "Error writing " + file, e);
            }
        }
    }

    public void importData(Context context){
        if (!isExternalStorageWritable())
            Toast.makeText(context,"SD Card is unmounted.",Toast.LENGTH_LONG).show();
        else{
            //Toast.makeText(context,"SD Card is mounted.",Toast.LENGTH_LONG).show();
            File file = new File(context.getExternalFilesDir(null),"con_venient.txt");
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while((line = br.readLine())!=null) {
                    StringTokenizer st = new StringTokenizer(line, "\t");
                    String eventname = st.nextToken();
                    String notes = st.nextToken();
                    Iterator<MyEvent> i = events.iterator();
                    while (i.hasNext()) {
                        MyEvent temp = i.next();
                        if (temp.getName().equalsIgnoreCase(eventname)) {
                            temp.selected = true;
                            if(!notes.equals("NULL"))
                                temp.setNotes(notes);
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
