package com.cs442.team3.con_venient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarViewFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    private View rootView = null;

    private static final String tag = "MyCalendarActivity";

    private TextView currentMonth;
    private Button selectedDayMonthYearButton;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    @SuppressLint("NewApi")
    private int month, year;
    @SuppressWarnings("unused")
    @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";


    private Cursor mCursor = null;
    private static final String[] COLS = new String[] {CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION };




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarViewFragment newInstance(String param1, String param2) {
        CalendarViewFragment fragment = new CalendarViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.my_calendar_view, container, false);

            _calendar = Calendar.getInstance(Locale.getDefault());
            month = _calendar.get(Calendar.MONTH) + 1;
            year = _calendar.get(Calendar.YEAR);
            //	Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
            //			+ year);

            selectedDayMonthYearButton = (Button)  rootView
                    .findViewById(R.id.selectedDayMonthYear);
            selectedDayMonthYearButton.setText("Selected: ");

            prevMonth = (ImageView) rootView.findViewById(R.id.prevMonth);
            prevMonth.setOnClickListener(this);

            currentMonth = (TextView) rootView.findViewById(R.id.currentMonth);
            currentMonth.setText(DateFormat.format(dateTemplate,
                    _calendar.getTime()));

            nextMonth = (ImageView) rootView.findViewById(R.id.nextMonth);
            nextMonth.setOnClickListener(this);

            calendarView = (GridView) rootView.findViewById(R.id.calendar);

            // Initialised
            adapter = new GridCellAdapter(rootView.getContext(),
                    R.id.calendar_day_gridcell, month, year);
            adapter.notifyDataSetChanged();
            calendarView.setAdapter(adapter);






        // Inflate the layout for this fragment
        return rootView;
    }

    public void onDaySelected(String dayMonthParameter) {
        if (mListener != null) {
            mListener.onDayClick(dayMonthParameter);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onDayClick(String dayMonthDate);
    }



    /**
     *
     * @param month
     * @param year
     */
    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(getActivity().getApplicationContext(),
                R.id.calendar_day_gridcell, month, year);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == prevMonth) {
            if (month <= 1) {
                month = 12;
                year--;
            } else {
                month--;
            }
            //	Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: "
            //			+ month + " Year: " + year);
            setGridCellAdapterToDate(month, year);
        }
        if (v == nextMonth) {
            if (month > 11) {
                month = 1;
                year++;
            } else {
                month++;
            }
            //	Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
            //			+ month + " Year: " + year);
            setGridCellAdapterToDate(month, year);
        }

    }

    @Override
    public void onDestroy() {
        //	Log.d(tag, "Destroying View ...");
        super.onDestroy();
    }

    // Inner Class
    public class GridCellAdapter extends BaseAdapter implements View.OnClickListener {
        private static final String tag = "GridCellAdapter";
        private final Context _context;

        private final List<String> list;
        private static final int DAY_OFFSET = 1;
        private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
                "Wed", "Thu", "Fri", "Sat" };
        private final String[] months = { "January", "February", "March",
                "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
                31, 30, 31 };
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;
        private Button gridcell;
        private TextView num_events_per_day;
        private final HashMap<String, Integer> eventsPerMonthMap;
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "dd-MMM-yyyy");

        // Days in Current Month
        public GridCellAdapter(Context context, int textViewResourceId,
                               int month, int year) {
            super();
            this._context = context;
            this.list = new ArrayList<String>();
            //	Log.d(tag, "==> Passed in Date FOR Month: " + month + " "
            //			+ "Year: " + year);
            Calendar calendar = Calendar.getInstance();
            setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
            setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		/*	Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
			Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
			Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());*/
            // Print Month
            printMonth(month, year);

            // Find Number of Events
            eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
        }

        private String getMonthAsString(int i) {
            return (i+"") ;
        }

        private String getWeekDayAsString(int i) {
            return weekdays[i];
        }

        private int getNumberOfDaysOfMonth(int i) {
            return daysOfMonth[i];
        }

        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * Prints Month
         *
         * @param mm
         * @param yy
         */
        private void printMonth(int mm, int yy) {
            //	Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
            int trailingSpaces = 0;
            int daysInPrevMonth = 0;
            int prevMonth = 0;
            int prevYear = 0;
            int nextMonth = 0;
            int nextYear = 0;

            int currentMonth = mm - 1;
            String currentMonthName = getMonthAsString(currentMonth);
            daysInMonth = getNumberOfDaysOfMonth(currentMonth);

            //	Log.d(tag, "Current Month: " + " " + currentMonthName + " having "
            //			+ daysInMonth + " days.");
            GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

            Calendar lastDayCal = Calendar.getInstance();
            lastDayCal.set(Calendar.DATE, lastDayCal.getActualMaximum(cal.DATE));

            Format df = DateFormat.getDateFormat(rootView.getContext());
            Format tf = DateFormat.getTimeFormat(rootView.getContext());


            final Map<String,String> daysWithEventsMap  = getDaysWhenEventsArePresent((currentMonth+1));//getCalendarSyncItems(cal.getTimeInMillis(), lastDayCal.getTimeInMillis());

            //	Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

            if (currentMonth == 11) {
                prevMonth = currentMonth - 1;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 0;
                prevYear = yy;
                nextYear = yy + 1;
                //	Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
                //			+ prevMonth + " NextMonth: " + nextMonth
                //			+ " NextYear: " + nextYear);
            } else if (currentMonth == 0) {
                prevMonth = 11;
                prevYear = yy - 1;
                nextYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 1;
                //	Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:"
                //			+ prevMonth + " NextMonth: " + nextMonth
                //			+ " NextYear: " + nextYear);
            } else {
                prevMonth = currentMonth - 1;
                nextMonth = currentMonth + 1;
                nextYear = yy;
                prevYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                //	Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"
                //			+ prevMonth + " NextMonth: " + nextMonth
                //			+ " NextYear: " + nextYear);
            }

            int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;

            //	Log.d(tag, "Week Day:" + currentWeekDay + " is "
            //			+ getWeekDayAsString(currentWeekDay));
            //	Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
            //	Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

            if (cal.isLeapYear(cal.get(Calendar.YEAR)))
                if (mm == 2)
                    ++daysInMonth;
                else if (mm == 3)
                    ++daysInPrevMonth;

            // Trailing Month days
            for (int i = 0; i < trailingSpaces; i++) {
		/*		Log.d(tag,
						"PREV MONTH:= "
								+ prevMonth
								+ " => "
								+ getMonthAsString(prevMonth)
								+ " "
								+ String.valueOf((daysInPrevMonth
										- trailingSpaces + DAY_OFFSET)
										+ i));*/
                list.add(String
                        .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                                + i)
                        + "-GREY"
                        + "-"
                        + getMonthAsString(prevMonth)
                        + "-"
                        + prevYear);
            }

            // Current Month Days
            for (int i = 1; i <= daysInMonth; i++) {
	/*			Log.d(currentMonthName, String.valueOf(i) + " "
						+ getMonthAsString(currentMonth) + " " + yy);*/
                //TODO change Day here .....


                if(null != daysWithEventsMap  && (!daysWithEventsMap.isEmpty())&& null != daysWithEventsMap.get(i+"")){
                    list.add(String.valueOf(i) + "-BLUE" + "-"
                            + getMonthAsString(currentMonth) + "-" + yy);
                }else if (i == getCurrentDayOfMonth()) {
              /*No Need to Highlight current Day     list.add(String.valueOf(i) + "-BLUE" + "-"
                            + getMonthAsString(currentMonth) + "-" + yy);  */
                } else {
                    list.add(String.valueOf(i) + "-WHITE" + "-"
                            + getMonthAsString(currentMonth) + "-" + yy);
                }
            }

            // Leading Month days
            for (int i = 0; i < list.size() % 7; i++) {
                //		Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
                list.add(String.valueOf(i + 1) + "-GREY" + "-"
                        + getMonthAsString(nextMonth) + "-" + nextYear);
            }
        }

        /**
         * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
         * ALL entries from a SQLite database for that month. Iterate over the
         * List of All entries, and get the dateCreated, which is converted into
         * day.
         *
         * @param year
         * @param month
         * @return
         */
        private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
                                                                    int month) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();

            return map;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.screen_gridcell, parent, false);
            }

            // Get a reference to the Day gridcell
            gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
            gridcell.setOnClickListener(this);

            // ACCOUNT FOR SPACING

            //	Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
            String[] day_color = list.get(position).split("-");
            String theday = day_color[0];
            String themonth = day_color[2];
            String theyear = day_color[3];
            if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
                if (eventsPerMonthMap.containsKey(theday)) {
                    num_events_per_day = (TextView) row
                            .findViewById(R.id.num_events_per_day);
                    Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                    num_events_per_day.setText(numEvents.toString());
                }
            }

            // Set the Day GridCell
            gridcell.setText(theday);
            gridcell.setTag(theday + "-" + themonth + "-" + theyear);
		/*	Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
					+ theyear);*/

            if (day_color[1].equals("GREY")) {
                gridcell.setTextColor(getResources()
                        .getColor(R.color.lightgray));
            }
            if (day_color[1].equals("WHITE")) {
                gridcell.setTextColor(getResources().getColor(
                        R.color.lightgray02));
            }
            if (day_color[1].equals("BLUE")) {
                gridcell.setTextColor(getResources().getColor(R.color.orrange));
            }
            return row;
        }

        @Override
        public void onClick(View view) {
            String date_month_year = (String) view.getTag();
            final int day  = (Integer.parseInt(date_month_year.split("-")[0]));
            final int month  = (Integer.parseInt(date_month_year.split("-")[1]));
            final int year = Integer.parseInt(date_month_year.split("-")[2]);
            final String textToSend  = day+"-"+month+"-"+year;
            selectedDayMonthYearButton.setText((month+1)+"/"+day+"/"+year);
            onDaySelected(textToSend);

        }

        public int getCurrentDayOfMonth() {
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth) {
            this.currentDayOfMonth = currentDayOfMonth;
        }

        public void setCurrentWeekDay(int currentWeekDay) {
            this.currentWeekDay = currentWeekDay;
        }

        public int getCurrentWeekDay() {
            return currentWeekDay;
        }
    }


    public Map<String,String> getCalendarSyncItems(final Long startTime,final Long endTime){
        Map<String,String> allEvents = new HashMap<String,String>();  //It Just has the Day as Integer in Map key & value..
        Format df = DateFormat.getDateFormat(getActivity().getApplicationContext());
        Format tf = DateFormat.getTimeFormat(getActivity().getApplicationContext());
        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime + " ))";
        try {

            mCursor = this.getActivity().getContentResolver().query(CalendarContract.Events.CONTENT_URI, COLS, selection, null, null);
            mCursor.moveToFirst();
            while (!mCursor.isLast()) {
                final int calendarId = mCursor.getInt(0);
                final String eventTitle = mCursor.getString(1);
                final String eventDescription = mCursor.getString(2);
                final Long dtstart = mCursor.getLong(3);
                final String dateTimeInFormat = getDayAndMonth(dtstart);
                allEvents.put(dateTimeInFormat.split("_")[0], dateTimeInFormat.split("_")[0]);
                final Long dtEnd = mCursor.getLong(4);  //ALL DAY COMES IN BETWEEN
                final String eventLocation = mCursor.getString(6);
                mCursor.moveToNext();
            }
            final String eventTitle = mCursor.getString(1);
            final String eventDescription = mCursor.getString(2);
            final Long dtstart = mCursor.getLong(3);
            final String dateTimeInFormat = getDayAndMonth(dtstart);
            allEvents.put(dateTimeInFormat.split("_")[0], dateTimeInFormat.split("_")[0]);
        }catch(final Exception e){
            e.printStackTrace();
        }
        return  allEvents;
    }



    public Map<String,String> getDaysWhenEventsArePresent(final int monthParam) {
        final Map<String,String> daysinMonth = new HashMap<String,String>();
        if(!DataFromWebService.isHosted){
            final ArrayList<MyEvent> myEvents= MainActivity.data.getEvents();
            if(null != myEvents && !myEvents.isEmpty()){
                for(MyEvent myEvent : myEvents){
                    try {
                        String date = getDayTimeElement(myEvent.getDateTime(), 2);
                        if (null != date) {
                            final int month = Integer.parseInt(date.split("/")[0]);
                            final int day = Integer.parseInt(date.split("/")[1]);
                            if (month == monthParam) {
                                daysinMonth.put(day + "", day + "");
                            }
                        }
                    }catch (final Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }else{
            {
                final ArrayList<MyEvent> myEvents= MainActivity.data.getEvents();
                if(null != myEvents && !myEvents.isEmpty()){
                    for(MyEvent myEvent : myEvents){
                        try {
                            String date = getDayTimeElement(myEvent.getDateTime(), 2);
                            if (null != date) {
                                final int month = Integer.parseInt(date.split("/")[0]);
                                final int day = Integer.parseInt(date.split("/")[1]);
                                if (month == monthParam) {
                                    daysinMonth.put(day + "", day + "");
                                }
                            }
                        }catch (final Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return daysinMonth;
    }



    private String getDayTimeElement(final String dateTimeString,final int type){
        String returnString  = null;
        final String date = dateTimeString.split(" ")[0];
        final String time  = dateTimeString.split(" ")[1];


        switch(type){
            case 1:                         //corresponds to just the Day in the Month
                returnString = date.split("/")[1];
            case 2 :
                returnString = date;
            default:
                break;
        }


        return returnString;
    }



/*
        System.out.println("########################DISPLAYING RESULTS #################");

        if( (null != allEvents) && (!allEvents.isEmpty())  ){
            for(Map.Entry<String,String>allEvent:allEvents.entrySet()){
                System.out.println(allEvent.getKey() + ":" + allEvent.getValue());
            }
        }


        System.out.println("######################################");  */



    private String getDayAndMonth(Long timeInMillis){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeInMillis);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        final String strDate  = mDay +"_"+mMonth+"_"+mYear;
        return strDate;
    }


}
