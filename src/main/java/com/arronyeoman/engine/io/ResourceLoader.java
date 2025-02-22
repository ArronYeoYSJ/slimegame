package com.arronyeoman.engine.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader {

    public static String loadResourceAsString(String filepath) {
        StringBuilder output = new StringBuilder();

        // Try loading as a resource stream (Works inside a JAR)
        try (InputStream inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream(filepath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IOException("ERROR: Resource not found - " + filepath);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            System.err.println("Error loading resource: " + filepath);
        }

        return output.toString();
    }
}