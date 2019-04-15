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


import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;

import static android.content.Context.LOCATION_SERVICE;

public class CanaryStuff implements LocationListener {


    // CSV Stuff
    // I believe this should not be a global variable
//    private FileWriter fileWriter;

    // Location Stuff
    private LocationManager locationManager;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    private String latStr;
    private String lonStr;
    private String altStr;



    // Uses FastCSV library from: https://github.com/osiegmar/FastCSV
    protected CsvWriter csvWriter = new CsvWriter();

    File file;

    public CanaryStuff(LocationManager locationManager) {
        this.locationManager = locationManager;

        //TODO: Update the file path to a best-practice directory path
        file = new File("sensorData.csv");

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
    }

    // Uses FastCSV
//    protected void sendCSV(String inputFromBoard) {
//        Map<String, Double> map = mapLastKnownLocation();
//
//        FileWriter fileWriter = null;
//        File filedir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"MyDir");
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"SensorData"+File.separator+"data.csv");
//
//        // Generate array from raw received text. NOTE: MUST PRINTLN EACH LINE FROM ARDUINO TO ENSURE \n CHAR
//        String[] parsedStrings = inputFromBoard.split("\n");
//
//        try {
//            fileWriter = new FileWriter(file, true);
//        } catch (java.io.IOException e) {
//            System.out.println("Error" + e);
//        }
//
//        try {
//            System.out.println("We're trying to write the fields to the file.");
//            CsvAppender csvAppender = csvWriter.append(fileWriter);
//
//
//            System.out.println(map.get("time").toString());
////            csvAppender.appendField(map.get("time").toString());
//
//            System.out.println(map.get("altitude").toString());
////            csvAppender.appendField(map.get("altitude").toString());
//
//            String[] dataline = {map.get("time").toString(), map.get("altitude").toString()};
//            csvAppender.appendLine(dataline);
//
////            csvAppender.endLine();
////            fileWriter.flush();
//
//
////                FILE HEADER
////                csvAppender.appendLine("TIME", "ALTITUDE", "SPO2", "TEMPERATURE");
//
//            // Add each line of the generated array to the csv file
//            for (String line : parsedStrings) {
//                String[] fields = line.split(",");
//                for (String field: fields) {
//                    csvAppender.appendField(field);
//                }
//                csvAppender.endLine();
//                fileWriter.flush();
//            }
//        } catch (java.io.IOException e) {
//            System.out.println(e);
//            e.printStackTrace();
//        }
//
//    }

//    public void createCSV() {
//        File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"SensorData");
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"SensorData"+File.separator+"data.csv");
//        FileWriter fileWriter = null;
//        try {
//            fileDir.mkdir();
//        } catch (Exception e) {
////            System.out.println("\ncreateCSV(): Whoah Nellie, fileDir.mkdir Failed");
//            e.printStackTrace();
//        }
//
//        if(!file.exists()){
//            try {
//                file.createNewFile();
//                fileWriter = new FileWriter(file, true);
//                if (fileWriter != null) {
//                    try (CsvAppender csvAppender = csvWriter.append(fileWriter)) {
//                        // WRITE FILE HEADER
//                        csvAppender.appendLine("TIME", "ALTITUDE", "SPO2", "TEMPERATURE");
//                    } catch (java.io.IOException e) {
//                        System.out.println(e);
//                        e.printStackTrace();
//                    }
//                }
//            } catch (IOException e) {
////                System.out.println("\ncreateCSV(): Whoah Nellie, file.createNewFile Failed");
//                e.printStackTrace();
//            }
//
//        }
//
//    }

    public void sendCSV(String sentInfo) {
        File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"SensorData");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"SensorData"+File.separator+"data.csv");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        Map<String, Double> map = mapLastKnownLocation();

        // Split and clean strings from Adafruit board
        String[] parsedStrings = sentInfo.split(",");
        for (String string : parsedStrings) {
            string.trim();
        }

        try {
            fileDir.mkdir();
        } catch (Exception e) {
//            System.out.println("\ncreateCSV(): Whoah Nellie, fileDir.mkdir Failed");
            e.printStackTrace();
        }
        System.out.println("Does file exist?");
        if(!file.isFile()){
            System.out.println("File doesn't exist yet.");
            try {
                file.createNewFile();
                if (fileWriter != null) {
                    System.out.println("Adding header to file.");
                    try (CsvAppender csvAppender = csvWriter.append(fileWriter)) {
                        // WRITE FILE HEADER
                        csvAppender.appendLine("TIME", "ALTITUDE", "LATITUDE", "LONGITUDE", "SPO2", "TEMPERATURE");
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // Write received data to file
        String[] dataline = {
                map.get("time").toString(),
                map.get("altitude").toString(),
                map.get("latitude").toString(),
                map.get("longitude").toString(),
                parsedStrings[0],
                parsedStrings[1]
        };

        System.out.println("Is filewriter null?");
        if (fileWriter != null) {
            System.out.println("filewriter is not null");
            try (CsvAppender csvAppender = csvWriter.append(fileWriter)) {
                csvAppender.appendLine(dataline);

                for (String string : dataline) {
                    System.out.println(string);
                }

            } catch (java.io.IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}
