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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String[] types = {"Car", "Van", "Bus", "Bike", "ThreeWheel"};
    EditText userName, userEmail, userPassword, userVehicleNumber;
    Button registerBtn;
    TextView redirectToUserLoginBtn;
    String name, email, password, vehicleNumber;
    String vehicleType = "Car";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        userName = findViewById(R.id.reg_userName);
        userEmail = findViewById(R.id.reg_userEmail);
        userPassword = findViewById(R.id.reg_userPassword);
        userVehicleNumber = findViewById(R.id.reg_userVehicleNumber);
        registerBtn = findViewById(R.id.reg_userRegisterBtn);
        redirectToUserLoginBtn = findViewById(R.id.redirectToUserLoginBtn);
        Spinner spinner = findViewById(R.id.reg_userVehicleType);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UserRegistration.this,
                android.R.layout.simple_spinner_item, types);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        redirectToUserLoginBtn.setOnClickListener(v -> startActivity(new Intent(UserRegistration.this, UserLogin.class)));

        registerBtn.setOnClickListener(v -> {
            name = userName.getText().toString();
            email = userEmail.getText().toString();
            password = userPassword.getText().toString();
            vehicleNumber = userVehicleNumber.getText().toString();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !vehicleNumber.isEmpty() && !vehicleType.isEmpty()) {
                userRegistration();
            } else {
                Toast.makeText(UserRegistration.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

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


    private void userRegistration() {
        String URL = "https://fuelapplication.herokuapp.com/api/user";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            jsonBody.put("vehicleNumber", vehicleNumber);
            jsonBody.put("vehicleType", vehicleType);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, response -> {
                try {
                    String ResponseStatus = response.getString("status");

                    if (ResponseStatus.equals("success")) {
                        startActivity(new Intent(UserRegistration.this, UserLogin.class));
                    } else {
                        Toast.makeText(UserRegistration.this, "Couldn't complete registration!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
            });
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException ignored) {
        }
    }
}