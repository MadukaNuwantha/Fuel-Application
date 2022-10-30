package com.sliit.fuelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {
    Button joinFuelQueueBtn;
    TextView userName, userEmail, userVehicleNumber, userVehicleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = findViewById(R.id.profile_userName);
        userEmail = findViewById(R.id.profile_userEmail);
        userVehicleNumber = findViewById(R.id.profile_userVehicleNumber);
        userVehicleType = findViewById(R.id.profile_userVehicleType);
        joinFuelQueueBtn = findViewById(R.id.profile_joinFuelQueueBtn);

        joinFuelQueueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, SearchFuelQueue.class));
            }
        });
    }
}