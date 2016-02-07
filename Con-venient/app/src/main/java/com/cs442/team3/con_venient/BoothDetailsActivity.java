package com.cs442.team3.con_venient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BoothDetailsActivity extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booth_details);
        Intent intent = getIntent();
        String boothname = intent.getStringExtra(EventBoothsFragment.EXTRA_MESSAGE);
        TextView title = (TextView) findViewById(R.id.booth_detail_title);
        title.setText(boothname);

        ArrayList<MyEvent> events = MainActivity.data.getEvents();
        MyEvent ev = new MyEvent("", false, "", "");
        String name = MainActivity.e_name;

        for (int i = 0; i < events.size(); i++) {
            if (name.equals(events.get(i).getName())) {

                ev = events.get(i);
                //Toast.makeText(this, "Works", Toast.LENGTH_SHORT).show();
            }


        }
        ArrayList<Booth> bo = ev.getBooths();
        Booth booth =new Booth("",0,"","","");

        for(int i = 0;i<bo.size();i++){
            if(boothname.equals(bo.get(i).getBoothName()))
                booth = bo.get(i);
        }

        TextView web = (TextView) findViewById(R.id.booth_detail_web);
        TextView cont = (TextView) findViewById(R.id.booth_detail_cont);
        TextView desc = (TextView) findViewById(R.id.booth_detail_des);

        TextView boothrequirements = (TextView) findViewById(R.id.booth_requirements);
        TextView typeofposition = (TextView) findViewById(R.id.booth_typeofposition);
        TextView positionhiring = (TextView) findViewById(R.id.booth_positionhiring);
        TextView majors = (TextView) findViewById(R.id.booth_majors);



        web.setText(booth.getWebsite());
        cont.setText(booth.getPhone());
        desc.setText(booth.getDescription());
        boothrequirements.setText(booth.getRequirements());
        typeofposition.setText(booth.getTypeofposition());
        positionhiring.setText(booth.getPositionhiring());
        majors.setText(booth.getMajors());


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
            showRefershDialog(BoothDetailsActivity.this).show();
        }
        if (id ==R.id.action_QRCode){
            try {
                //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                Intent intent = new Intent(ACTION_SCAN);
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException anfe) {
                //on catch, show the download dialog
                showDialog(BoothDetailsActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
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
    public void onBackPressed() {
        finish();
    }

}
