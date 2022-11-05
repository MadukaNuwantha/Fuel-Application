package com.sliit.fuelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sliit.fuelapplication.databinding.ActivitySearchFuelQueueBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFuelQueue extends AppCompatActivity {

    ActivitySearchFuelQueueBinding binding;
    EditText stationName;
    ArrayList<Station> stationList = new ArrayList<>();
    ListView stationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchFuelQueueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getFuelStations();

        binding.searchStationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchFuelQueue.this, FuelQueue.class);
                i.putExtra("stationID",stationList.get(position).StationID);
                startActivity(i);
            }
        });
    }

    private void getFuelStations() {
        String URL = "https://fuelapplication.herokuapp.com/api/station/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                String ResponseStatus = response.getString("status");
                if (ResponseStatus.equals("success")) {

                    JSONArray jsonArray = response.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String ID = jsonObject.getString("_id");
                        String Name = jsonObject.getString("name");
                        String Email = jsonObject.getString("email");
                        double RemainingFuel = Double.parseDouble(jsonObject.getString("fuelAmount"));
                        int QueueLength = Integer.parseInt(jsonObject.getString("queue_length"));

                        stationList.add(new Station(ID, Name, Email, RemainingFuel, QueueLength));
                    }
                    StationListAdapter stationListAdapter = new StationListAdapter(SearchFuelQueue.this, stationList);
                    binding.searchStationListView.setAdapter(stationListAdapter);
                    binding.searchStationListView.setClickable(true);
                } else {
                    Toast.makeText(SearchFuelQueue.this, "Couldn't retrieve fuel stations", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }

}