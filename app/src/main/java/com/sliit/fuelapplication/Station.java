package com.sliit.fuelapplication;

public class Station {

    String StationID;
    String StationName;
    String StationEmail;
    double FuelAmount;
    int QueueLength;

    public Station(String stationID, String stationName, String stationEmail, double fuelAmount, int queueLength) {
        StationID = stationID;
        StationName = stationName;
        StationEmail = stationEmail;
        FuelAmount = fuelAmount;
        QueueLength = queueLength;
    }
}
