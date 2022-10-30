package com.sliit.fuelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SearchFuelQueue extends AppCompatActivity {
    Button searchFuelStation;
    EditText stationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_fuel_queue);

        stationName = findViewById(R.id.search_stationName);
        searchFuelStation = findViewById(R.id.search_searchStationBtn);

        searchFuelStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchFuelQueue.this, FuelQueue.class));
            }
        });
    }
}