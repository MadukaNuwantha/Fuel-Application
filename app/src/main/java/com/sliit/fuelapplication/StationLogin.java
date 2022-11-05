package com.sliit.fuelapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


        redirectToStationRegBtn.setOnClickListener(v -> startActivity(new Intent(StationLogin.this, StationRegistration.class)));

        stationLoginBtn.setOnClickListener(v -> {
            email = stationEmail.getText().toString();
            password = stationPassword.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                stationLogin();
            } else {
                Toast.makeText(StationLogin.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void stationLogin() {
        String URL = "https://fuelapplication.herokuapp.com/api/station/login";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, response -> {
                try {
                    String ResponseStatus = response.getString("status");


                    if (ResponseStatus.equals("success")) {
                        JSONArray jsonArray = response.getJSONArray("result");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        SharedPreferences sharedPreferences = getSharedPreferences("StationData", MODE_PRIVATE);
                        SharedPreferences.Editor Station = sharedPreferences.edit();

                        String StationID = jsonObject.getString("_id");
                        String Name = jsonObject.getString("name");
                        String Email = jsonObject.getString("email");
                        String FuelAmount = jsonObject.getString("fuelAmount");

                        Station.putString("stationId", StationID);
                        Station.putString("name", Name);
                        Station.putString("email", Email);
                        Station.putString("fuelAmount", FuelAmount);
                        Station.apply();

                        startActivity(new Intent(StationLogin.this, StationProfile.class));
                    } else {
                        Toast.makeText(StationLogin.this, "Login credentials are incorrect", Toast.LENGTH_SHORT).show();
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