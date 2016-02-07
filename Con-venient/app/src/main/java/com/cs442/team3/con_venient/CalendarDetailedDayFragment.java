package com.cs442.team3.con_venient;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CalendarDetailedDayFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


    private static View rootView = null;

    private static List<String> dayEvents = null;

    private static ArrayAdapter dayEventsArrayAdapter;


    private Cursor mCursor = null;
    private static final String[] COLS = new String[] {CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION };


    public static String dayDetailedRequest = null;

    public CalendarDetailedDayFragment() {
    }


    public void ondaySelected(final String dayMonthRequest){



        dayDetailedRequest = dayMonthRequest;

        final String dayStr = dayMonthRequest.split("-")[0];
        final String monthStr = dayMonthRequest.split("-")[1];
        final String yearStr = dayMonthRequest.split("-")[2];
/*
        final Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dayStr));
        startCal.set(Calendar.MONTH,Integer.parseInt(monthStr));
        startCal.set(Calendar.YEAR,Integer.parseInt(yearStr));
        startCal.set(Calendar.HOUR_OF_DAY,0);
        startCal.set(Calendar.MINUTE,0);
        startCal.set(Calendar.SECOND,0);

        final Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dayStr));
        endCal.set(Calendar.MONTH,Integer.parseInt(monthStr));
        endCal.set(Calendar.YEAR,Integer.parseInt(yearStr));
        endCal.set(Calendar.HOUR_OF_DAY,23);
        endCal.set(Calendar.MINUTE,59);
        endCal.set(Calendar.SECOND, 59);*/
        dayEvents.clear();
        dayEvents.addAll(getDayDetails(dayStr,monthStr,yearStr));
        dayEventsArrayAdapter.notifyDataSetChanged();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_day_detailed_calendar, container, false);
        dayEvents= new ArrayList<String>();

        if(null == dayDetailedRequest){
            dayEvents.add("--Please Click on a day to load the Events--");
        }else{
            dayEvents.add("--No Events Found--");
        }


        dayEventsArrayAdapter= new ArrayAdapter<String>(rootView.getContext(),R.layout.fragment_day_detailed_calendar,R.id.day_events_list_text,dayEvents);
        final ListView lv = (ListView) rootView.findViewById(R.id.day_events_list);
        lv.setAdapter(dayEventsArrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String yourData = dayEvents.get(position);

                if(null != yourData && (!yourData.equalsIgnoreCase("--Please Click on a day to load the Events--")) && (!yourData.equalsIgnoreCase("--No Events Found--"))  ) {
                    Toast.makeText(getContext(), yourData, Toast.LENGTH_SHORT).show();
                    showConfirmCalSync(getActivity(),yourData);
                }
            }
        });


        return rootView;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteractionForDetailedDay(Uri uri);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    public ArrayList<String> getCalendarSyncItems(final Long startTime,final Long endTime){
        final ArrayList<String> allEvents = new ArrayList<String>();
        Format df = android.text.format.DateFormat.getDateFormat(getActivity().getApplicationContext());
        Format tf = android.text.format.DateFormat.getTimeFormat(getActivity().getApplicationContext());
        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime + " ))";



        if ( ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.WRITE_CALENDAR},
                    1);
        }

        mCursor  = this.getActivity().getContentResolver().query(CalendarContract.Events.CONTENT_URI, COLS, selection, null, null);


        boolean presentFlag  = mCursor.moveToFirst();

        if((presentFlag)) {
            while (!mCursor.isLast()) {
                //final int calendarId = mCursor.getInt(0);
                final String eventTitle = mCursor.getString(1);
                final String eventDescription = mCursor.getString(2);
                final Long dtstart = mCursor.getLong(3);
                final String dateTimeInFormat = getDayAndMonth(dtstart);
                final Long dtEnd = mCursor.getLong(4);  //ALL DAY COMES IN BETWEEN
                final String eventLocation = mCursor.getString(6);


                allEvents.add(eventTitle + "" + ((null != eventDescription && !eventDescription.isEmpty()) ? ":" + eventDescription : "") + " on " + dateTimeInFormat + " at " + eventLocation);


                mCursor.moveToNext();
            }

            final String eventTitle = mCursor.getString(1);
            final String eventDescription = mCursor.getString(2);
            final Long dtstart = mCursor.getLong(3);
            final String dateTimeInFormat = getDayAndMonth(dtstart);
            final String eventLocation = mCursor.getString(6);
            allEvents.add(eventTitle + "" + ((null != eventDescription && !eventDescription.isEmpty()) ? ":" + eventDescription : "") + " on " + dateTimeInFormat + " at " + eventLocation);

        }

        return allEvents;
    }





    // Add an event to the calendar of the user.
    public static void addEvent(final Activity act,final int year,final int month,final int day,final int hour,final int minute,final String eventName) {
        GregorianCalendar calDate = new GregorianCalendar(year, month,day,hour,minute);
        try {
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, calDate.getTimeInMillis());
            values.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis() + 60 * 60 * 1000);
            values.put(CalendarContract.Events.TITLE, eventName);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());

            if ( ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.WRITE_CALENDAR},
                        1);
            }

            act.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private ArrayList<String> getDayDetails(final String day,final String Month,final String year){
        final String dayMonthYearRequestQuery = (Integer.parseInt(Month)+1)+"/"+day+"/"+year;
        final ArrayList<String> responseList = new ArrayList<String>();
        if(!DataFromWebService.isHosted) {
            final ArrayList<MyEvent> myEvents = MainActivity.data.getEvents();
            if(null != myEvents && !myEvents.isEmpty()){
                for(MyEvent myEvent:myEvents){
                    final String dateTime  = myEvent.getDateTime();
                    final String date  = dateTime.split(" ")[0];
                    final String time  = dateTime.split(" ")[1];


                    if(null != dateTime && !dateTime.isEmpty() && checkIfDateisSame(date,dayMonthYearRequestQuery)){
                       String responseString = "Event :"+myEvent.getName()+" on "+date+" "+time;
                        if(null != myEvent.getLocation()){
                            responseString = responseString + " at "+myEvent.getLocation();
                        }
                        if(null != myEvent.getNotes()) {
                            responseString = responseString +"   "+myEvent.getNotes();
                        }
                        responseList.add(responseString);
                    }else{
                        System.out.println("################");
                        System.out.println(dayMonthYearRequestQuery +" is not equal to "+date);
                        System.out.println("################");

                    }
                }
            }
        }else{

            final ArrayList<MyEvent> myEvents = MainActivity.data.getEvents();
            if(null != myEvents && !myEvents.isEmpty()){
                for(MyEvent myEvent:myEvents){
                    final String dateTime  = myEvent.getDateTime();
                    final String date  = dateTime.split(" ")[0];
                    final String time  = dateTime.split(" ")[1];


                    if(null != dateTime && !dateTime.isEmpty() && checkIfDateisSame(date,dayMonthYearRequestQuery)){
                        String responseString = "Event :"+myEvent.getName()+" on "+date+" "+time;
                        if(null != myEvent.getLocation()){
                            responseString = responseString + " at "+myEvent.getLocation();
                        }
                        if(null != myEvent.getNotes()) {
                            responseString = responseString +"   "+myEvent.getNotes();
                        }
                        responseList.add(responseString);
                    }else{
                        System.out.println("################");
                        System.out.println(dayMonthYearRequestQuery +" is not equal to "+date);
                        System.out.println("################");

                    }
                }
            }

        }
        return responseList;
    }



    private boolean checkIfDateisSame(final String date1,final String date2){
        boolean dateSame = true;
        if(  Integer.parseInt(date1.split("/")[0]) != Integer.parseInt(date2.split("/")[0]) ){
            dateSame = false;
        }
        if(  Integer.parseInt(date1.split("/")[1]) != Integer.parseInt(date2.split("/")[1]) ){
            dateSame = false;
        }
        if(  Integer.parseInt(date1.split("/")[2]) != Integer.parseInt(date2.split("/")[2]) ){
            dateSame = false;
        }
        return dateSame;
    }


    private String getDayAndMonth(Long timeInMillis){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeInMillis);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        final String strDate  = hour +":"+minute+":"+second;
        return strDate;
    }



    private static AlertDialog showConfirmCalSync(final Activity act,final String eventDets){
        final String eventName = (eventDets.split(" on ")[0]).split("Event :")[1];
        final String date = (eventDets.split(" on ")[1]).split(" ")[0];
        final String time = (eventDets.split(" on ")[1]).split(" ")[1];

        final int month = (Integer.parseInt(date.split("/")[0])-1);
        final int day = Integer.parseInt(date.split("/")[1]);
        final int year = Integer.parseInt(date.split("/")[2]);

        final int hour = Integer.parseInt(time.split(":")[0]);
        final int minute = Integer.parseInt(time.split(":")[1]);



        AlertDialog.Builder refreshDialog = new AlertDialog.Builder(act);
        refreshDialog.setTitle("Confirmation");
        refreshDialog.setMessage("Are you sure you wish to Add this Event to your personal Calendar");
        refreshDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                addEvent(act,year,month,day,hour,minute,eventName);
            }
        });
        refreshDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return refreshDialog.show();
    }

}
