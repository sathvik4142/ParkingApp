package com.parkingapp.parkingapp;

public class ParkingSpot {
    public String address;
    public String lowerHour;
    public String upperHour;
    public double rate;
    public int cars;
    public double longitude;
    public double latitude;
    public boolean inUse;
    public String currLicensePlate;
    public String id;

    public ParkingSpot(String address, String startTime, String endTime, double rate, int cars, double longi, double lati)
    {
        this.address = address;
        lowerHour = startTime;
        upperHour = endTime;
        this.rate = rate;
        this.cars = cars;
        longitude = longi;
        latitude = lati;
        inUse = false;
        currLicensePlate = "";
        id = "";

    }

    public ParkingSpot(){}
}
