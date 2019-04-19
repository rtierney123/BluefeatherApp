package com.adafruit.bluefruit.le.connect.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import static android.content.Context.LOCATION_SERVICE;

public class CanaryStuff {


    // CSV Stuff
    // I believe this should not be a global variable
//    private FileWriter fileWriter;

    // Location Stuff
    /*
    private LocationManager locationManager;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    private String latStr;
    private String lonStr;
    private String altStr;
    */



    public CanaryStuff(LocationManager locationManager) {
        /*
        this.locationManager = locationManager;


        // Setup Location Stuff
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (SecurityException e) {
            System.out.print("Well darn, we're not allowed to do this.");
            e.printStackTrace();
        }

        latStr = "No Location Yet";
        lonStr = "No Location Yet";
        altStr = "No Location Yet";

        // This should create the csv file for us to send
//        createCSV();
    }

    @Override
    public void onLocationChanged(Location location) {
        latStr = ((Double) location.getLatitude()).toString();
        lonStr = ((Double) location.getLongitude()).toString();
        altStr = ((Double) location.getAltitude()).toString();
        System.out.println("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");
        System.out.println("Latitude" + " status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
        System.out.println("Latitude" + " enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","disable");
    }

    protected Map<String, Double> mapLastKnownLocation() {
        Date currentTime = Calendar.getInstance().getTime();

        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            try {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            } catch (SecurityException e) {
                System.out.print("Oh darn.");
                e.printStackTrace();
            }
        }
//		Log.w("Location", (bestLocation.getLatitude() + "\t" + bestLocation.getLongitude() + "\t" + bestLocation.getAltitude()));

        Map<String, Double> retMap = new HashMap<String, Double>();
        retMap.put("time", ((double) currentTime.getTime()));
        retMap.put("latitude", bestLocation.getLatitude());
        retMap.put("longitude", bestLocation.getLongitude());
        retMap.put("altitude", bestLocation.getAltitude());
        return retMap;
        */
    }


}
