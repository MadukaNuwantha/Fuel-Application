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

public class StationLogAdapter extends ArrayAdapter<StationLog> {


    public StationLogAdapter(Context context, ArrayList<StationLog> stationLogArrayList) {
        super(context, R.layout.station_log_list_tile, stationLogArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        StationLog stationLog = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.station_log_list_tile, parent, false);
        }

        TextView stationLogDescription = convertView.findViewById(R.id.station_log_list_tile_description);
        TextView stationLogName = convertView.findViewById(R.id.station_log_list_tile_name);
        TextView stationLogValue = convertView.findViewById(R.id.station_log_list_tile_value);

        stationLogDescription.setText(stationLog.Description);
        stationLogName.setText(stationLog.Name);
        stationLogValue.setText(stationLog.Value);

        return convertView;
    }
}
