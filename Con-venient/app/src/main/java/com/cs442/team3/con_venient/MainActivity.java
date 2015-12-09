package com.cs442.team3.con_venient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionItemTarget;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import android.support.v7.widget.Toolbar;
//import java.lang.annotation.Target;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static SharedPreferences prefs = null;
    ShowcaseView sv;
    private int count = 0;
    private Target addButton;

    public static DataModel data = new DataModel();

    public static String e_name = "";

    private ArrayAdapter<String> adapter;

    public final static MyEvent EVENT_HOME = new MyEvent("",false,"","");


    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    //public ArrayList<Event> events = new ArrayList<Event>()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.cs442.team3.con_venient", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            // do first launch stuff here
            addButton = new ViewTarget(R.id.addEvent, this);

            sv = new ShowcaseView.Builder(this, false)
                    .setTarget(Target.NONE)
                    .setOnClickListener(this)
                    .setContentTitle("Welcome to Con-venient!")
                    .setContentText("This is a little tutorial. \nRemember to use the three-dot overflow menu" +
                            " on the top right of your screen to scan QR Codes and see your calendar!")
                    .setStyle(R.style.FullColor)
                    .build();
            sv.setButtonText("OK!");
            //prefs.edit().putBoolean("firstrun", false).commit();
        }
        data.importData(this);
        displayListView();
        Button addEvent = (Button)findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // starts a new Intent to add a Country
                Intent pref_intent = new Intent(getBaseContext(), EventPrefs.class);

                startActivity(pref_intent);
            }
        });
        //needs to populate the events array from data base on create
    }

    @Override
    protected void onDestroy() {
        data.exportData(this);
        super.onDestroy();


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
            showRefershDialog(MainActivity.this).show();
        }
        if (id ==R.id.action_QRCode){
            try {
                //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                Intent intent = new Intent(ACTION_SCAN);
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException anfe) {
                //on catch, show the download dialog
                showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
            }
        }
        if(id == R.id.action_show_calendar_id){
            final Intent intent = new Intent(this,CalendarMainActivity.class);
            startActivityForResult(intent, 0);

        }


        return super.onOptionsItemSelected(item);
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                //Toast toast = Toast.makeText(this, "Content:" + contents, Toast.LENGTH_SHORT);
                //toast.show();

                Intent webviewActivity = new Intent(getBaseContext(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("weblink", contents);
                webviewActivity.putExtras(bundle);
                startActivity(webviewActivity);
            }
        }
    }
    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                //Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Uri uri = Uri.parse("market://search?q=pname:" + "tw.com.quickmark&hl=en");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayListView();
    }

    private void displayListView() {

        ArrayList<String> events = new ArrayList<String>();
        ArrayList<MyEvent> eventOb = data.getEvents();

        for (int i=0;i< eventOb.size();i++){
            MyEvent ev = eventOb.get(i);
            if(ev.isSelected())
                events.add(ev.getName());

        }


        TextView info_text = (TextView) findViewById(R.id.infoText);
        if (events.isEmpty()){
            info_text.setVisibility(View.VISIBLE);
            //info_text.setText("Please click Add Event to add a new event to your list.");
        }else info_text.setVisibility(View.GONE);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, events);
        ListView listview=(ListView) findViewById(R.id.eventList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView) view).getText().toString();
                e_name = item;
                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, MainEventActivity.class);
                startActivity(intent);
            }
        });
    }

    private static AlertDialog showRefershDialog(final Activity act){
        AlertDialog.Builder refreshDialog = new AlertDialog.Builder(act);
        refreshDialog.setTitle("Refresh Confirmation");
        refreshDialog.setMessage("Are you sure you wish to refresh the data");
        refreshDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                //   Toast.makeText(,"Details Being Synced with the Web Service",Toast.LENGTH_LONG).show();


            }
        });
        refreshDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return refreshDialog.show();
    }


    public void ExportBtnClicked(View view) {
        data.exportData(this);
    }
    public void ImportBtnClicked(View view) {
        data.importData(this);
        displayListView();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (prefs.getBoolean("firstrun", true)) {
            // do first launch stuff here
            switch (count){
                /*case 1:
                    sv.setTarget(Target.NONE);
                    sv.setContentTitle("Menu Options");
                    sv.setContentText("Click on this menu Icon on any page to access a QR Code scanner and your calendar.");
                    sv.setButtonText("OK!");
                    break;*/
                case 0:
                    sv.setShowcase(addButton, true);
                    sv.setContentTitle("Adding Events");
                    sv.setContentText("This button will take you to a list of events which you can add to your main page.");
                    sv.setButtonText("Got It!");
                    break;
                case 1:
                    sv.hide();
                    prefs.edit().putBoolean("firstrun", false).commit();
                    break;
            }
            count++;
        }

    }
}
