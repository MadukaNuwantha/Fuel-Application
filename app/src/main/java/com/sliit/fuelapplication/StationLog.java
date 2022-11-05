package com.sliit.fuelapplication;

public class StationLog {
    String LogID;
    String StationID;
    String Description;
    String Name;
    String Value;

    public StationLog(String logID, String stationID, String description, String name, String value) {
        LogID = logID;
        StationID = stationID;
        Description = description;
        Name = name;
        Value = value;
    }
}
