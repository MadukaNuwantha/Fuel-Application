package com.sliit.fuelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StationLogin extends AppCompatActivity {
    Button stationLoginBtn;
    TextView redirectToStationRegBtn;
    EditText stationEmail, stationPassword;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_login);

        stationEmail = findViewById(R.id.log_stationName);
        stationPassword = findViewById(R.id.log_stationPassword);
        stationLoginBtn = findViewById(R.id.log_stationLoginBtn);
        redirectToStationRegBtn = findViewById(R.id.redirectToStationRegBtn);


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