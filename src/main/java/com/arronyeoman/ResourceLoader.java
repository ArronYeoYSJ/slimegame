package com.arronyeoman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ResourceLoader {

    public static String loadResourceAsString(String filepath){

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))){
            String line ="";
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading resource at: " + filepath);
        }
        
        return output.toString();
        
        }
}



