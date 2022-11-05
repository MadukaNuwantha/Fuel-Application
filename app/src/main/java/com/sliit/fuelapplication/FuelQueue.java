package com.sliit.fuelapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class FuelQueue extends AppCompatActivity {
    TextView stationName, stationEmail, stationQueueLen, stationFuelAmt;
    EditText refillAmt;
    Button exitWithRefill, exitWithoutRefill, joinFuelQueue;
    String stationID, userID, fuelAmount, stationRemainingFuelAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_queue);

        getStationDetails();

        stationName = findViewById(R.id.queue_stationName);
        stationEmail = findViewById(R.id.queue_stationEmail);
        stationQueueLen = findViewById(R.id.queue_queueLength);
        stationFuelAmt = findViewById(R.id.queue_fuelAvailability);
        joinFuelQueue = findViewById(R.id.queue_joinFuelQueueBtn);
        refillAmt = findViewById(R.id.queue_refillAmount);
        exitWithRefill = findViewById(R.id.queue_exitWithRefillBtn);
        exitWithoutRefill = findViewById(R.id.queue_exitWithoutRefillBtn);

        refillAmt.setVisibility(View.INVISIBLE);
        joinFuelQueue.setVisibility(View.GONE);
        exitWithRefill.setVisibility(View.GONE);
        exitWithoutRefill.setVisibility(View.GONE);

        getCurrentFuelQueue();

        exitWithoutRefill.setOnClickListener(v -> {
            exitWithoutRefill();

        });

        joinFuelQueue.setOnClickListener(v -> {
            joinFuelQueue();
        });

        exitWithRefill.setOnClickListener(v -> {
            fuelAmount = refillAmt.getText().toString();
            Intent intent = this.getIntent();
            stationRemainingFuelAmt = intent.getStringExtra("fuelAmount");
            stationID = intent.getStringExtra("stationID");
            if (!fuelAmount.isEmpty() || Double.parseDouble(fuelAmount) < 0 || Double.parseDouble(fuelAmount) > Double.parseDouble(stationRemainingFuelAmt)) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                userID = sharedPreferences.getString("userId", "");
                exitWithRefill();
            } else {
                Toast.makeText(FuelQueue.this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCurrentFuelQueue() {

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        userID = sharedPreferences.getString("userId", "");

        String URL = "https://fuelapplication.herokuapp.com/api/queue/current/" + userID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                String ResponseStatus = response.getString("status");

                Intent intent = this.getIntent();
                stationID = intent.getStringExtra("stationID");

                if (ResponseStatus.equals("fail")) {
                    joinFuelQueue.setVisibility(View.VISIBLE);
                    refillAmt.setVisibility(View.INVISIBLE);
                    exitWithRefill.setVisibility(View.INVISIBLE);
                    exitWithoutRefill.setVisibility(View.INVISIBLE);
                } else if (ResponseStatus.equals("success")) {
                    JSONObject jsonObject = response.getJSONObject("data");
                    String requestStationID = jsonObject.getString("station_id");
                    if (requestStationID.equals(stationID)) {
                        joinFuelQueue.setVisibility(View.GONE);
                        refillAmt.setVisibility(View.VISIBLE);
                        exitWithRefill.setVisibility(View.VISIBLE);
                        exitWithoutRefill.setVisibility(View.VISIBLE);
                    } else {
                        joinFuelQueue.setVisibility(View.GONE);
                        refillAmt.setVisibility(View.INVISIBLE);
                        exitWithRefill.setVisibility(View.GONE);
                        exitWithoutRefill.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(FuelQueue.this, "Server Error!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getStationDetails() {

        Intent intent = this.getIntent();
        String StationID = intent.getStringExtra("stationID");

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
                    Toast.makeText(FuelQueue.this, "Login credentials are incorrect", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void joinFuelQueue() {
        String URL = "https://fuelapplication.herokuapp.com/api/queue/";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("station_id", stationID);
            jsonBody.put("user_id", userID);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, response -> {
                try {
                    String ResponseStatus = response.getString("status");
                    if (ResponseStatus.equals("success")) {
                        getStationDetails();
                        getCurrentFuelQueue();
                    } else {
                        Toast.makeText(FuelQueue.this, "Couldn't update database", Toast.LENGTH_SHORT).show();
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

    private void exitWithRefill() {
        String URL = "https://fuelapplication.herokuapp.com/api/queue/leaveWithFuel/";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("station_id", stationID);
            jsonBody.put("user_id", userID);
            jsonBody.put("fuelAmount", Double.parseDouble(fuelAmount));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, response -> {
                try {
                    String ResponseStatus = response.getString("status");
                    if (ResponseStatus.equals("success")) {
                        startActivity(new Intent(FuelQueue.this, UserProfile.class));
                    } else {
                        Toast.makeText(FuelQueue.this, "Couldn't update database", Toast.LENGTH_SHORT).show();
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

    private void exitWithoutRefill() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        userID = sharedPreferences.getString("userId", "");

        String URL = "https://fuelapplication.herokuapp.com/api/queue/leaveWithoutFuel/" + userID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, response -> {
            try {
                String ResponseStatus = response.getString("status");
                if (ResponseStatus.equals("success")) {
                    startActivity(new Intent(FuelQueue.this, UserProfile.class));
                } else {
                    Toast.makeText(FuelQueue.this, "Couldn't update database", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }
}