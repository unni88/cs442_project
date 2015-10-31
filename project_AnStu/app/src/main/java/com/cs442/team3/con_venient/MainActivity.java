package com.cs442.team3.con_venient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
    private void displayListView() {

        ArrayList<String> events = new ArrayList<String>();
        events.add("Event1");
        events.add("Event2");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, events);
        ListView listview=(ListView) findViewById(R.id.eventList);
        listview.setAdapter(adapter);
        /*
        // The desired columns to be bound
        String[] columns = new String[] {
                CountriesDb.KEY_CODE,
                CountriesDb.KEY_NAME,
                CountriesDb.KEY_CONTINENT
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.code,
                R.id.name,
                R.id.continent,
        };

        // create an adapter from the SimpleCursorAdapter
        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.country_info,
                null,
                columns,
                to,
                0);

        // get reference to the ListView
        ListView listView = (ListView) findViewById(R.id.countryList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        //Ensures a loader is initialized and active.
        getLoaderManager().initLoader(0, null, this);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // display the selected country
                String countryCode =
                        cursor.getString(cursor.getColumnIndexOrThrow(CountriesDb.KEY_CODE));
                Toast.makeText(getApplicationContext(),
                        countryCode, Toast.LENGTH_SHORT).show();

                String rowId =
                        cursor.getString(cursor.getColumnIndexOrThrow(CountriesDb.KEY_ROWID));

                // starts a new Intent to update/delete a Country
                // pass in row Id to create the Content URI for a single row
                Intent countryEdit = new Intent(getBaseContext(), CountryEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "update");
                bundle.putString("rowId", rowId);
                countryEdit.putExtras(bundle);
                startActivity(countryEdit);

            }
        });*/

    }
}
