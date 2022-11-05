package com.sliit.fuelapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button userBtn, stationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userBtn = findViewById(R.id.selectUserBtn);
        stationBtn = findViewById(R.id.selectStationBtn);

        userBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, UserLogin.class));
        });

        stationBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StationLogin.class));
        });
    }
}