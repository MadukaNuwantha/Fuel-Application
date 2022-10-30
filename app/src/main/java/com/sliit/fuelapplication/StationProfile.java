package com.sliit.fuelapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StationProfile extends AppCompatActivity {
    TextView stationName, stationEmail, stationFuelAmt, stationQueueLen;
    EditText stationRefillAmt;
    Button stationRefillFuelBtn, stationViewLogBtn;
    String refillAmt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_profile);

        stationName = findViewById(R.id.profile_stationName);
        stationEmail = findViewById(R.id.profile_stationEmail);
        stationFuelAmt = findViewById(R.id.profile_stationFuelAmt);
        stationQueueLen = findViewById(R.id.profile_stationQueueLen);
        stationRefillAmt = findViewById(R.id.profile_stationFuelRefillAmount);
        stationRefillFuelBtn = findViewById(R.id.profile_stationFuelRefillBtn);
        stationViewLogBtn = findViewById(R.id.profile_viewStationLogBtn);

    }
}