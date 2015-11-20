package com.cs442.team3.con_venient;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {"@link EventNotesFragment.OnFragmentInteractionListener"} interface
 * to handle interaction events.
 * Use the {"@link EventNotesFragment#newInstance"} factory method to
 * create an instance of this fragment.
 */
public class EventNotesFragment extends Fragment {
    public EventNotesFragment() {
        // Required empty public constructor
    }

    EditText edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_notes, container, false);
        Button button = (Button) view.findViewById(R.id.notes_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotes(v);
            }
        });
        edit = (EditText)view.findViewById(R.id.notes);
        String en = MainActivity.e_name;
        edit.setText(getNote(en));
        //edit.setText("testing text");

        // Inflate the layout for this fragment
        return view;

    }

    public String getNote(String name){
    ArrayList<MyEvent> events = MainActivity.data.getEvents();
    MyEvent ev = new MyEvent("", false, "", "");
    String note= " ";
    //Toast.makeText(getActivity(),MainActivity.EXTRA_MESSAGE,Toast.LENGTH_LONG).show();
    //Toast.makeText(getActivity(),events.get(1).getName(),Toast.LENGTH_SHORT).show();
    for (int i = 0; i < events.size(); i++) {
        if (name.equals(events.get(i).getName())) {
            // Toast.makeText(getActivity(),"works",Toast.LENGTH_LONG).show();
            ev = events.get(i);
        }
    }
        note = ev.getNotes();
        return note;
    }

    private String eventHomeDetails(String name) {
        ArrayList<MyEvent> events = MainActivity.data.getEvents();
        MyEvent ev = new MyEvent("", false, "", "");
        String s = "";
        //Toast.makeText(getActivity(),MainActivity.EXTRA_MESSAGE,Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(),events.get(1).getName(),Toast.LENGTH_SHORT).show();
        for (int i = 0; i < events.size(); i++){
            if (name.equals(events.get(i).getName())) {
                // Toast.makeText(getActivity(),"works",Toast.LENGTH_LONG).show();
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

    public void saveNotes(View view){
        ArrayList<MyEvent>  events = MainActivity.data.getEvents();
        String name = MainActivity.e_name;
        MyEvent ev= new MyEvent("",false,"","");
        int index =0;
        for (int i = 0; i < events.size(); i++){
            if (name.equals(events.get(i).getName())) {
                // Toast.makeText(getActivity(),"works",Toast.LENGTH_LONG).show();
                ev = events.get(i);
                index =i;
            }

        }
            ev.setNotes(edit.getText().toString());
            events.set(index, ev);
            MainActivity.data.setEvents(events);

    }
}
