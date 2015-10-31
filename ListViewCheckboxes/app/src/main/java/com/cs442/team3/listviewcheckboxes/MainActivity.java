package com.cs442.team3.listviewcheckboxes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyAdapter dataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayListView(){
        ArrayList<Event> eventsList = new ArrayList<Event>();

        Event event = new Event("Career Fair", false);
        eventsList.add(event);

        //creates an arrayadapter from the string array
        dataAdapter = new MyAdapter(this, R.layout.event_list_items,eventsList);
        ListView listView = (ListView) findViewById(R.id.ListView1);
        // assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //when clicked it will show a toast.
                Event event = (Event) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),"Clicked "+event.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }




    public class MyAdapter extends ArrayAdapter<Event> {

        private ArrayList<Event> eventList;

        public MyAdapter(Context context, int textViewResourceid, ArrayList<Event> eventList){
            super(context, textViewResourceid,eventList);

            this.eventList = new ArrayList<Event>();
            this.eventList.addAll(eventList);

        }

        private class ViewHolder{
            //TextView name;
            CheckBox name1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolder holder = null;
           // Log.v("ConvertView", String.valueOf(position));

            if(convertView == null){
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.event_list_items,null);

                holder = new ViewHolder();
               // holder.name = (TextView) convertView.findViewById(R.id.event);
                holder.name1 = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name1.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        CheckBox cb = (CheckBox) v;
                        Event event = (Event) cb.getTag();
                        //Toast.makeText(getApplicationContext(),"Clicked "+cb.getText() + " is " +cb.isChecked(), Toast.LENGTH_SHORT).show();
                        event.setSelected(cb.isChecked());
                    }
                });
            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }

            Event event = eventList.get(position);
           // holder.name.setText("");
            holder.name1.setText(event.getName());
            holder.name1.setChecked(event.isSelected());
            holder.name1.setTag(event);


            return convertView;
        }

    }
}
