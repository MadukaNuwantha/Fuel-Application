package com.sliit.fuelapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sliit.fuelapplication.databinding.ActivityStationLogViewBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StationLogView extends AppCompatActivity {


    ActivityStationLogViewBinding binding;
    ArrayList<StationLog> stationLogList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStationLogViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getStationLog();
    }


    private void getStationLog() {
        SharedPreferences sharedPreferences = getSharedPreferences("StationData", MODE_PRIVATE);
        String StationID = sharedPreferences.getString("stationId", "");

        String URL = "https://fuelapplication.herokuapp.com/api/log/" + StationID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                String ResponseStatus = response.getString("status");
                if (ResponseStatus.equals("success")) {

                    JSONArray jsonArray = response.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String LogID = jsonObject.getString("_id");
                        String FuelStationID = jsonObject.getString("station_id");
                        String LogDescription = jsonObject.getString("description");
                        String LogName = jsonObject.getString("name");
                        String LogValue = jsonObject.getString("value");


                        stationLogList.add(new StationLog(LogID, FuelStationID, LogDescription, LogName, LogValue));
                    }
                    StationLogAdapter stationLogAdapter = new StationLogAdapter(StationLogView.this, stationLogList);
                    binding.stationLogListView.setAdapter(stationLogAdapter);
                    binding.stationLogListView.setClickable(true);
                } else {
                    Toast.makeText(StationLogView.this, "Couldn't retrieve fuel stations", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }
}