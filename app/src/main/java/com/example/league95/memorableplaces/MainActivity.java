package com.example.league95.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Reason why we did static is because we need to update
    //These variables from the MapsActivity as well!!
    //Memorable places
    static ArrayList<String> places = new ArrayList<>();
    //We need another array list for the locations
    static ArrayList<LatLng> locations = new ArrayList<>();
    //Adapter
    static ArrayAdapter arrayAdapter;
    //Shared pref?
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create list view
        ListView listView = (ListView) findViewById(R.id.ourView);
        // Load the saved data.
        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();
        //What to do initial is set our storages to 0.
        places.clear();
        latitudes.clear();
        longitudes.clear();
        locations.clear();
        sharedPreferences = this.getSharedPreferences("com.example.league95.memorableplaces", Context.MODE_PRIVATE);
        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes", ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Check if we have any data saved to begin with
        if (places.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0) {
            if (places.size() == latitudes.size() && latitudes.size() == longitudes.size()) {
                for (int i = 0; i < places.size(); i++) {
                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }
            }
            //Else if there aren't any places saved
        } else {
            //Initial
            places.add("Add a new place..");
            //Initial locations empty
            locations.add(new LatLng(51.93 , -0.38));
        }
        //Adapter
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        //set adapter
        listView.setAdapter(arrayAdapter);
        //when we click on an item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", i);
                startActivity(intent);
            }
        });
        //Now we restore our results from the map activity

        arrayAdapter.notifyDataSetChanged();

    }
}
