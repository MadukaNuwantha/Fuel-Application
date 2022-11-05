package com.sliit.fuelapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class StationProfile extends AppCompatActivity {
    TextView stationName, stationEmail, stationFuelAmt, stationQueueLen;
    EditText stationRefillAmt;
    Button stationRefillFuelBtn, stationViewLogBtn;
    String refillAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_profile);

        getStationDetails();

        stationName = findViewById(R.id.profile_stationName);
        stationEmail = findViewById(R.id.profile_stationEmail);
        stationFuelAmt = findViewById(R.id.profile_stationFuelAmt);
        stationQueueLen = findViewById(R.id.profile_stationQueueLen);
        stationRefillAmt = findViewById(R.id.profile_stationFuelRefillAmount);
        stationRefillFuelBtn = findViewById(R.id.profile_stationFuelRefillBtn);
        stationViewLogBtn = findViewById(R.id.profile_viewStationLogBtn);

        stationRefillFuelBtn.setOnClickListener(v -> {
            refillAmt = stationRefillAmt.getText().toString();
            if (!refillAmt.isEmpty()) {
                stationRefill();
            } else {
                Toast.makeText(StationProfile.this, "Enter a valid amount!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getStationDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("StationData", MODE_PRIVATE);

        String StationID = sharedPreferences.getString("stationId", "");

        String URL = "https://fuelapplication.herokuapp.com/api/station/" + StationID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                String ResponseStatus = response.getString("status");


                if (ResponseStatus.equals("success")) {

                    JSONObject jsonObjectResult = response.getJSONObject("result");
                    String QueueLength = jsonObjectResult.getString("length");
                    JSONObject jsonObjectData = jsonObjectResult.getJSONObject("data");

                    String Name = jsonObjectData.getString("name");
                    String Email = jsonObjectData.getString("email");
                    String RemainingFuel = jsonObjectData.getString("fuelAmount");

                    stationName.setText(Name);
                    stationEmail.setText(Email);
                    stationFuelAmt.setText(RemainingFuel);
                    stationQueueLen.setText(QueueLength);

                } else {
                    Toast.makeText(StationProfile.this, "Login credentials are incorrect", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void stationRefill() {
        SharedPreferences sharedPreferences = getSharedPreferences("StationData", MODE_PRIVATE);

        String StationID = sharedPreferences.getString("stationId", "");

        String URL = "https://fuelapplication.herokuapp.com/api/station/refill/" + StationID;
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("fuelAmount", Double.parseDouble(refillAmt));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, response -> {
                try {
                    String ResponseStatus = response.getString("status");

                    if (ResponseStatus.equals("success")) {
                        getStationDetails();
                        stationRefillAmt.getText().clear();
                    } else {
                        Toast.makeText(StationProfile.this, "Couldn't refill fuel", Toast.LENGTH_SHORT).show();
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