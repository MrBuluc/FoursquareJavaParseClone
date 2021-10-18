package com.hakkicanbuluc.foursquarejavaparseclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class LocationsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> placeNames, placeObjIDs;
    ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_place) {
            Intent intent = new Intent(getApplicationContext(),
                    CreatePlaceActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        listView = findViewById(R.id.listview_locations_activity);
        placeNames = new ArrayList<>();
        placeObjIDs = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, placeNames);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("id", placeObjIDs.get(i));
            startActivity(intent);
        });

        download();
    }

    public void download() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Places");
        query.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() > 0) {
                    placeNames.clear();
                    for (ParseObject object : objects) {
                        placeNames.add(object.getString("name"));
                        placeObjIDs.add(object.getObjectId());
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}