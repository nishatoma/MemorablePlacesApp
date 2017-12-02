package com.example.league95.memorableplaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Reason why we did static is because we need to update
    //These variables from the MapsActivity as well!!
    //Memorable places
    static List<String> places = new ArrayList<>();
    //We need another array list for the location
    static List<LatLng> location = new ArrayList<>();
    //Adapter
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create list view
        ListView listView = (ListView) findViewById(R.id.ourView);


        //Initial
        places.add("Add a new place..");
        //Initial location empty
        location.add(new LatLng(0, 0));
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
    }
}
