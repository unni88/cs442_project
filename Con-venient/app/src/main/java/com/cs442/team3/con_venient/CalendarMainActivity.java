package com.cs442.team3.con_venient;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CalendarMainActivity extends FragmentActivity implements CalendarViewFragment.OnFragmentInteractionListener,CalendarDetailedDayFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_main);
    }


    @Override
    public void onDayClick(String dayMonthDate){
        CalendarDetailedDayFragment calendardayDetailedFragment = (CalendarDetailedDayFragment)
                getSupportFragmentManager().findFragmentById(R.id.calendar_day_detail_fragment);
        if (calendardayDetailedFragment != null) {
            // If article frag is available, we're in two-pane layout...
            // Call a method in the ArticleFragment to update its content
            calendardayDetailedFragment.ondaySelected(dayMonthDate);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected article
            CalendarDetailedDayFragment newFragment = new CalendarDetailedDayFragment();
            Bundle args = new Bundle();
            args.putString(CalendarDetailedDayFragment.dayDetailedRequest, dayMonthDate);
            newFragment.setArguments(args);
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.calendar_day_detail_fragment, newFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onFragmentInteractionForDetailedDay(Uri uri){

    }
}
