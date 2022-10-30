package com.sliit.fuelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String[] types = {"Car", "Van", "Bus", "Bike", "ThreeWheel"};
    EditText userName, userEmail, userPassword, userVehicleNumber;
    Button registerBtn;
    TextView redirectToUserLoginBtn;
    String name, email, password, vehicleNumber;
    String vehicleType = "Car";
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        userName = findViewById(R.id.reg_userName);
        userEmail = findViewById(R.id.reg_userEmail);
        userPassword = findViewById(R.id.reg_userPassword);
        userVehicleNumber = findViewById(R.id.reg_userVehicleNumber);
        registerBtn = findViewById(R.id.reg_userRegisterBtn);
        spinner = (Spinner) findViewById(R.id.reg_userVehicleType);

        redirectToUserLoginBtn = findViewById(R.id.redirectToUserLoginBtn);

        redirectToUserLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegistration.this, UserLogin.class));
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserRegistration.this,
                android.R.layout.simple_spinner_item, types);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                vehicleType = "Car";
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                vehicleType = "Van";
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                vehicleType = "Bus";
                // Whatever you want to happen when the third item gets selected
                break;
            case 3:
                vehicleType = "Bike";
                // Whatever you want to happen when the fourth item gets selected
                break;
            case 4:
                vehicleType = "ThreeWheel";
                // Whatever you want to happen when the fifth item gets selected
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}