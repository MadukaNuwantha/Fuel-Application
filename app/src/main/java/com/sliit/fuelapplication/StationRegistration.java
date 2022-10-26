package com.sliit.fuelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StationRegistration extends AppCompatActivity {
    TextView redirectToStationLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_registration);

        redirectToStationLoginBtn = findViewById(R.id.redirectToStationLoginBtn);

        redirectToStationLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StationRegistration.this, StationLogin.class));
            }
        });
    }
}