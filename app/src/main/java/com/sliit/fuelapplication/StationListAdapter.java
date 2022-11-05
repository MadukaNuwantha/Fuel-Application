package com.sliit.fuelapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class StationListAdapter extends ArrayAdapter<Station> {

    public StationListAdapter(Context context, ArrayList<Station> userArrayList) {
        super(context, R.layout.station_list_tile, userArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Station station = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.station_list_tile, parent, false);
        }

        TextView stationName = convertView.findViewById(R.id.station_list_tile_name);
        TextView stationEmail = convertView.findViewById(R.id.station_list_tile_email);
        TextView stationFuelAmt = convertView.findViewById(R.id.station_list_tile_fuel);
        TextView stationQueueLen = convertView.findViewById(R.id.station_list_tile_queue);

        stationName.setText(station.StationName);
        stationEmail.setText(station.StationEmail);
        stationFuelAmt.setText(Double.toString(station.FuelAmount));
        stationQueueLen.setText(Integer.toString(station.QueueLength));

        return convertView;
    }
}
