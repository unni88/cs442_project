package com.cs442.team3.con_venient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;


public class MainEventActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView navList;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    ShowcaseView scv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);
        SharedPreferences prefers = MainActivity.prefs;
        if (prefers.getBoolean("firsttime", true)) {
            // do first launch stuff here
            scv = new ShowcaseView.Builder(this, false)
                    .setTarget(Target.NONE)
                    .hideOnTouchOutside()
                    .setContentTitle("Welcome to the Event Page!")
                    .setContentText("This is where you can view basic information for an event. \nUse the three-bar navigation drawer" +
                            " on the top left of your screen to see the different things that this event has to offer")
                    .setStyle(R.style.FullColor)
                    .build();
            scv.setButtonText("OK!");
            prefers.edit().putBoolean("firsttime", false).commit();
        }
        //Intent intent = getIntent();
        String eventname = MainActivity.e_name;
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        navList = (ListView)findViewById(R.id.navlist);
        ArrayList<String> navArray = new ArrayList<String>();
        navArray.add(eventname);
        navArray.add("Booth List");
        navArray.add("Event Map");
        navArray.add("Notes");
        navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,navArray);
        navList.setAdapter(adapter);
        navList.setOnItemClickListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.opendrawer,R.string.closedrawer);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();


        loadSelection(0);
    }


    private void loadSelection(int i){
        navList.setItemChecked(i,true);

        switch (i){
            case 0:
                EventHomeFragment eventHomeFragment = new EventHomeFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,eventHomeFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                EventBoothsFragment boothsFragment = new EventBoothsFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,boothsFragment);
                fragmentTransaction.commit();
                break;

            case 2:
                EventMapFragment mapFragment = new EventMapFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,mapFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                EventNotesFragment noteFragment = new EventNotesFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,noteFragment);
                fragmentTransaction.commit();

                break;
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
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
        System.out.println("*************************************id"+id);
        if (id == R.id.action_settings) {

            showRefershDialog(MainEventActivity.this).show();
        }
        if (id == android.R.id.home){
            if (drawerLayout.isDrawerOpen(navList)){
                drawerLayout.closeDrawer(navList);
            }else{
                drawerLayout.openDrawer(navList);
            }
        }
        if (id ==R.id.action_QRCode){
            try {
                //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                Intent intent = new Intent(ACTION_SCAN);
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException anfe) {
                //on catch, show the download dialog
                showDialog(MainEventActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
            }
        }
        if(id == R.id.action_show_calendar_id){
            final Intent intent = new Intent(this,CalendarMainActivity.class);
            startActivity(intent);
            finish();
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        loadSelection(position);

        drawerLayout.closeDrawer(navList);
    }

    @Override
    public void onBackPressed() {
        finish();

        //FragmentManager fm = this.getSupportFragmentManager();
        //fm.popBackStack();
        /*navList.setItemChecked(0,true);
        EventHomeFragment eventHomeFragment = new EventHomeFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentholder, eventHomeFragment);
        fragmentTransaction.commit();*/
    }
}
