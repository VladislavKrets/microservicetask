package com.task.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static synchronized Map<String, String> cfgFileReader() {
        Map<String, String> properties = new HashMap<>();
        try (BufferedReader iniFileReader = new BufferedReader(new FileReader("config.cfg"))) {
            String property;
            String[] propertyArray;
            while ((property = iniFileReader.readLine()) != null) {
                propertyArray = property.split("=");
                if (property.contains("=")) {
                    if (propertyArray.length == 2) properties.put(propertyArray[0], propertyArray[1]);
                    else properties.put(propertyArray[0], "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
