package com.adafruit.bluefruit.le.connect.app.canary;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import de.siegmar.fastcsv.writer.CsvWriter;
import de.siegmar.fastcsv.writer.CsvAppender;

public class CSVManager {

    // Uses FastCSV library from: https://github.com/osiegmar/FastCSV
    protected CsvWriter csvWriter = new CsvWriter();

    File file;

    public void sendCSV(String sentInfo) {
        File fileDir = new File( Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"SensorData");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"SensorData"+File.separator+"data.csv");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

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
                        csvAppender.appendLine("TIME", "SPO2", "ALTITUDE", "LATITUDE", "LONGITUDE");
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        System.out.println("Is filewriter null?");
        if (fileWriter != null) {
            System.out.println("filewriter is not null");
            try (CsvAppender csvAppender = csvWriter.append(fileWriter)) {
                csvAppender.appendLine(parsedStrings);

                for (String string : parsedStrings) {
                    System.out.println(string);
                }

            } catch (java.io.IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}
