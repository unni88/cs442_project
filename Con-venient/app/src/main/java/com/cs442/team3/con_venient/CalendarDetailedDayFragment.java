package com.cs442.team3.con_venient;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
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


                    if(null != dateTime && !dateTime.isEmpty() && date.equalsIgnoreCase(dayMonthYearRequestQuery)){
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
            //TODO get Date and time from Web Service
        }
        return responseList;
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

}
