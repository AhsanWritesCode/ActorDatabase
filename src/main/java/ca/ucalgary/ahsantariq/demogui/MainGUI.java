package ca.ucalgary.ahsantariq.demogui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainGUI extends Application {
    private static File file = null;

    public static void main(String[] args) {
        // Check if command line arguments are provided
        if (args.length > 2) {
            // Print error message if more than one command line argument is provided
            System.err.println("Expected one command line argument for filename to load from");
        }
        // Check if exactly one command line argument is provided
        if (args.length == 1) {
            // Get the filename from the command line argument
            String filename = args[0];
            // Create a File object with the provided filename
            file = new File(args[0]);
            // Check if the file exists and is readable
            if (!file.exists() || !file.canRead()) {
                // Print error message if the file cannot be read
                System.err.println("Can not load from the file provided " + filename);
                // Exit the program with a non-zero exit code to indicate an error
                System.exit(1);
            }
            // If the file exists and is readable, call the menuLoop method from the Menu class with the file
            launch();
        }
        else {
            launch();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        MainController cont = fxmlLoader.getController();
        stage.setTitle("Actor Database");
        stage.setScene(scene);
        stage.show();
        if (file != null) {
            cont.load(file);
        }
    }
}