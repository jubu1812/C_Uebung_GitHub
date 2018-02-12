package com.example.julia.c_uebung_github;

/**
 * Created by mschneiderbauer on 12.02.2018.
 */

public class Standort {
    double longitude, latitude;
    String dateAndTime;

    public Standort(double latitude, double longitude, String dateAndTime) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dateAndTime = dateAndTime;
    }

    public Standort() {
    }

    @Override
    public String toString() {
        return latitude +
                ", " + longitude +
                ", " + dateAndTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
