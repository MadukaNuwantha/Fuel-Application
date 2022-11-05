package com.sliit.fuelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class StationRegistration extends AppCompatActivity {
    TextView redirectToStationLoginBtn;
    EditText stationName, stationEmail, stationPassword;
    Button registerStationBtn;
    String name, email, password;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_registration);

        stationName = findViewById(R.id.reg_stationName);
        stationEmail = findViewById(R.id.reg_stationEmail);
        stationPassword = findViewById(R.id.reg_stationPassword);
        registerStationBtn = findViewById(R.id.reg_stationRegisterBtn);
        redirectToStationLoginBtn = findViewById(R.id.redirectToStationLoginBtn);

        redirectToStationLoginBtn.setOnClickListener(view -> startActivity(new Intent(StationRegistration.this, StationLogin.class)));

        registerStationBtn.setOnClickListener(v -> {
            name = stationName.getText().toString();
            email = stationEmail.getText().toString();
            password = stationPassword.getText().toString();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                stationRegistration();
            } else {
                Toast.makeText(StationRegistration.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void stationRegistration() {
        String URL = "https://fuelapplication.herokuapp.com/api/station/";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, response -> {
                try {
                    String ResponseStatus = response.getString("status");

                    if (ResponseStatus.equals("success")) {
                        startActivity(new Intent(StationRegistration.this, StationLogin.class));
                    } else {
                        Toast.makeText(StationRegistration.this, "Couldn't complete registration!", Toast.LENGTH_SHORT).show();
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