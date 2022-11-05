package com.sliit.fuelapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        String Name = sharedPreferences.getString("name", "");
        String Email = sharedPreferences.getString("email", "");
        String VehicleNumber = sharedPreferences.getString("vehicleNumber", "");
        String VehicleType = sharedPreferences.getString("vehicleType", "");

        userName.setText(Name);
        userEmail.setText(Email);
        userVehicleNumber.setText(VehicleNumber);
        userVehicleType.setText(VehicleType);


        joinFuelQueueBtn.setOnClickListener(v -> startActivity(new Intent(UserProfile.this, SearchFuelQueue.class)));
    }
}