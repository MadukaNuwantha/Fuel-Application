package com.sliit.fuelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchFuelQueue extends AppCompatActivity {
Button searchFuelStation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_fuel_queue);

        searchFuelStation=findViewById(R.id.searchFuelStation);

        searchFuelStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchFuelQueue.this, FuelQueue.class));
            }
        });
    }
}