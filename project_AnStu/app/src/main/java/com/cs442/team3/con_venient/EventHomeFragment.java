package com.cs442.team3.con_venient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventHomeFragment extends Fragment {

    public EventHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_home, container, false);

        TextView textv = (TextView)view.findViewById(R.id.event_home);
        textv.setText(eventHomeDetails(MainActivity.EXTRA_MESSAGE));
        // Inflate the layout for this fragment
        return view;

    }

    private String eventHomeDetails(String name) {
        ArrayList<MyEvent> events = MainActivity.data.getEvents();
        MyEvent ev = new MyEvent("", false, "", "");
        String s = "";
        //Toast.makeText(getActivity(),MainActivity.EXTRA_MESSAGE,Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(),events.get(1).getName(),Toast.LENGTH_SHORT).show();
        for (int i = 0; i < events.size(); i++){
            if (name.equals(events.get(i).getName())) {
                Toast.makeText(getActivity(),"works",Toast.LENGTH_LONG).show();
                ev = events.get(i);
            }
        }
        String s1 = "Event Name: "+ev.getName();
        String s2 = "Location: "+ev.getLocation();
        String s3 = "Date and Time: "+ ev.getDateTime();

        s= s1+"\n\n"+s2+"\n\n"+s3;
        return s;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
