package ca.ucalgary.ahsantariq.demogui.util;

import ca.ucalgary.ahsantariq.demogui.Data;
import ca.ucalgary.ahsantariq.demogui.objects.Actor;
import ca.ucalgary.ahsantariq.demogui.objects.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

// Utility class responsible for saving data to a file
public class FileSaver {
    // Method to save data to a file
    public static boolean save(File file, Data data) {
        try (FileWriter fw = new FileWriter(file)) {
            // Write section header for Actors
            fw.write("Actors\n");
            // Get all actors from the data and sort them
            ArrayList<Actor> actors = data.getAllActors();
            Collections.sort(actors);
            // Write each actor's information to the file
            for (Actor actor : actors) {
                fw.write(String.format("%s,%s,%s,%s,%s,%s\n",
                        actor.getName(),
                        actor.getAge(),
                        actor.getHeight(),
                        actor.getCountry(),
                        actor.getNumberOfAwards(),
                        actor.getImdbLink()));
            }
            // Flush the writer to ensure data is written to the file
            fw.flush();
            // Write section header for Pairs
            fw.write("Pairs\n");
            ArrayList<Pair> pairs = data.getAllPairs();
            Collections.sort(pairs);
            for (Pair pair : pairs) {
                // pairs always contain 2 people
                fw.write(String.format("%s,%s\n",
                        pair.getImdbLink1(),
                        pair.getImdbLink1()));
            }

// Flush the writer again to ensure data is written to the file
            fw.flush();

            // Return true to indicate successful saving
            return true;
        } catch (IOException ioe) {
            // Print stack trace if an IOException occurs
            ioe.printStackTrace();
            // Return false to indicate failure in saving
            return false;
        }
    }
}
