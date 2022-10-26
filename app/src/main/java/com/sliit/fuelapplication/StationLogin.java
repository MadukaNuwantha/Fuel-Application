package com.sliit.fuelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StationLogin extends AppCompatActivity {
    Button stationLoginBtn;
    TextView redirectToStationRegBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_login);


        redirectToStationRegBtn = findViewById(R.id.redirectToStationRegBtn);
        stationLoginBtn=findViewById(R.id.stationLoginBtn);

        redirectToStationRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StationLogin.this, StationRegistration.class));
            }
        });

        stationLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StationLogin.this, StationProfile.class));
            }
        });
    }
}