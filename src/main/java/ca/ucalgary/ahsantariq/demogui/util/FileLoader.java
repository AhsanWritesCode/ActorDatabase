package ca.ucalgary.ahsantariq.demogui.util;

import ca.ucalgary.ahsantariq.demogui.Data;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class FileLoader {
    // Method to load data from a file
    public static Data load(File file) {
        // Create a new Data object to store loaded data
        Data data = new Data();
        try(Scanner scanner = new Scanner(file)){
            // Read the first line to check if it contains "Actors"
            String line = scanner.nextLine();
            if (!line.equals("Actors")){
                // Print error message if header not found
                System.err.println("Could not find Actors header!");
                return null;
            }
            boolean found_pairs = false;
            // Loop through lines until "Pairs" header or end of file is found
            while (scanner.hasNextLine()){
                line = scanner.nextLine();
                if(line.equals("Pairs")){
                    found_pairs = true;
                    break; // Exit loop if "Pairs" header is found
                }
                // Split the line into parts using comma as delimiter
                String[] parts = line.split(",");
                // Check if the parts array has correct length
                if (parts.length != 6){
                    // Print error message if length is incorrect
                    System.err.printf("Actor entry %s was not the right length!%n", Arrays.toString(parts));
                }
                // Extract data from parts array
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                int height = Integer.parseInt(parts[2]);
                String country = parts[3];
                int awards = Integer.parseInt(parts[4]);
                String imdbLink = parts[5];
                // Store actor data in the Data object
                data.storeNewActor(name,age,height,country,awards,imdbLink);
            }
            if (!found_pairs){
                // Print error message if "Pairs" header not found
                System.err.println("Could not find Pairs header!");
                return null;// Return null indicating failure
            }
            // Loop through lines to read co-star pairs
            while (scanner.hasNextLine()){
                line = scanner.nextLine();
                // Split the line into parts using comma as delimiter
                String[] parts = line.split(",");
                // Check if the parts array has correct length
                if (parts.length != 2) {
                    // Print error message if length is incorrect
                    System.err.printf("Pair entry %s was not the right length!%n", Arrays.toString(parts));
                }
                // Extract IMDb links for co-stars
                String imdbLink1 = parts[0];
                String imdbLink2 = parts[1];
                // Store co-star pair in the Data object
                data.storeNewCoStarPair(imdbLink1, imdbLink2);
            }
        } catch (IOException ioe) {
            // Print error message if exception occurs while reading file
            System.err.println("Exception occurred while reading file!");
            return null; // Return null indicating failure
        }
        return data; // Return loaded data
    }
}