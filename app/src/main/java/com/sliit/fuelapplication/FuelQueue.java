package com.sliit.fuelapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FuelQueue extends AppCompatActivity {
    TextView stationName, queueLength, fuelAvailability;
    EditText refillAmt;
    Button exitWithRefill, exitWithoutRefill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_queue);

        stationName = findViewById(R.id.queue_stationName);
        queueLength = findViewById(R.id.queue_queueLength);
        fuelAvailability = findViewById(R.id.queue_fuelAvailability);
        refillAmt = findViewById(R.id.queue_refillAmount);
        exitWithRefill = findViewById(R.id.queue_exitWithRefillBtn);
        exitWithoutRefill = findViewById(R.id.queue_exitWithoutRefillBtn);
    }
}