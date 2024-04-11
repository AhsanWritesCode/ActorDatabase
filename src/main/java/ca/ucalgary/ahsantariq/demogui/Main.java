package ca.ucalgary.ahsantariq.demogui;
// Import necessary libraries

import java.io.File;

/**
 * CPSC 233 Final Project
 * Authors: Ahsan Tariq, Ethan Braum, Rahnuha Nurain (Template by Jonathan Hudson)
 * Student Email: ahsan.tariq@ucalgary.ca, ethan.braum@ucalgary.ca, rahnuha.nurain@ucalgary.ca
 * Tutorial: 17
 *
 * @version 2.0 - OO Version
 */

// Main class representing the entry point of the application
public class Main {
    // Main method to start the application
    public static void main(String[] args) {
        // Check if command line arguments are provided
        if (args.length > 1) {
            // Print error message if more than one command line argument is provided
            System.err.println("Expected one command line argument for filename to load from");
        }
        // Check if exactly one command line argument is provided
        if (args.length == 1) {
            // Get the filename from the command line argument
            String filename = args[0];
            // Create a File object with the provided filename
            File file = new File(args[0]);
            // Check if the file exists and is readable
            if (!file.exists() || !file.canRead()) {
                // Print error message if the file cannot be read
                System.err.println("Can not load from the file provided " + filename);
                // Exit the program with a non-zero exit code to indicate an error
                System.exit(1);
            }
            // If the file exists and is readable, call the menuLoop method from the Menu class with the file
            Menu.menuLoop(file);
        } else {
            // If no command line argument is provided, call the menuLoop method from the Menu class with null
            Menu.menuLoop(null);
        }
    }
}

