package ca.ucalgary.ahsantariq.demogui;

import ca.ucalgary.ahsantariq.demogui.comparators.ActorNameImdbLinkComparator;
import ca.ucalgary.ahsantariq.demogui.objects.Actor;
import ca.ucalgary.ahsantariq.demogui.objects.ActorPair;
import ca.ucalgary.ahsantariq.demogui.objects.Pair;
import ca.ucalgary.ahsantariq.demogui.util.FileLoader;
import ca.ucalgary.ahsantariq.demogui.util.FileSaver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainController {

    // Format string for displaying actor information
    private static final String ACTOR_FORMAT = "%-40s\t%-40s\t%-40s\t%-40s\t%-40s\t%-40s\t%-40s%n";
    // Header for displaying actor information
    private static final String ACTOR_HEADER = String.format(ACTOR_FORMAT, "NAME", "AGE", "HEIGHT", "COUNTRY", "# OF AWARDS", "IMDB LINK", "RETIRED");
    // Format string for displaying actor pairs
    private static final String PAIR_FORMAT = "%-40s %-40s%n";
    // Header for displaying actor pairs
    private static final String PAIR_HEADER = String.format(PAIR_FORMAT, "IMDBLINK1", "IMDBLINK2");
    private static Data data = new Data();
    // Separator string for formatting purposes
    private static String ACTOR_SEPARATOR = "";
    private static String PAIR_SEP = "";
    // Separator string for formatting purposes

    // Static block to initialize the separator string
    static {
        // Loop through each character in the actor header and concatenate '-' to form a separator string
        for (int i = 0; i < ACTOR_HEADER.length(); i++) {
            ACTOR_SEPARATOR += "-";
        }
    }

    // Static block to initialize the separator string
    static {
        // Loop through each character in the pair header and concatenate '-' to form a separator string
        for (int i = 0; i < PAIR_HEADER.length(); i++) {
            PAIR_SEP += "-";
        }
    }

    @FXML
    private Label actors_label;

    @FXML
    private Label unpaired_label;

    @FXML
    private TextField costar_id1;

    @FXML
    private TextField costar_id2;

    @FXML
    private Label costars_label;

    @FXML
    private Button actor_info;

    @FXML
    private TextField info_id1;

    @FXML
    private TextField retire_id1;

    @FXML
    private TextField sep_id1;

    @FXML
    private TextField sep_id2;

    @FXML
    private Label status_label;

    @FXML
    void about(ActionEvent event) {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("Message");
        about.setContentText("Author: Rahnuha Nurain\nAhsan Tariq\nEthan Braum\n\n"
                + "Email: rahnuha.nurain@ucalgary.ca\nahsan.tariq@ucalgary.ca\nethan.braum@ucalgary.ca\n\n"
                + "Version: v1.0\n\n"
                + "This application retrieves and organizes actor information from IMDb links for effortless access and storage.");

        // Set light blue style directly
        DialogPane dialogPane = about.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #ADD8E6; " // Light blue background
                + "-fx-font-family: Arial; " // Arial font family
                + "-fx-font-size: 14px; " // Font size
                + "-fx-text-fill: #000000;"); // Black text color

        about.showAndWait();
    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    void load(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load a file");
        fc.setInitialDirectory(new File("."));
        fc.setInitialFileName("data.csv");
        File file = fc.showOpenDialog(new Stage());
        load(file);
        viewActors();
        viewCoStarPairs();
        viewUnpaired();
    }

    public void viewActors() {
        // Retrieve and print information about all actors from the data
        ArrayList<Actor> actors = data.getAllActors();
        actors.sort(new ActorNameImdbLinkComparator());
        String s = viewActors(actors);
        actors_label.setText(s);
        actors_label.setFont(new Font("Courier New", 10));
    }

    public void viewUnpaired() {
        // Retrieve and print information about all actors from the data
        ArrayList<Actor> actors = data.getActorsNotInPair();
        actors.sort(new ActorNameImdbLinkComparator());
        String s = viewActors(actors);
        unpaired_label.setText(s);
        unpaired_label.setFont(new Font("Courier New", 10));
    }

    public void viewCoStarPairs() {
        StringBuilder sb = new StringBuilder();
        // Display header for co-star pair information
        sb.append(PAIR_HEADER);
        // Display separator line for formatting
        sb.append(PAIR_SEP);
        sb.append("\n");
        // Retrieve and print information about all co-star pairs from the data
        ArrayList<Pair> pairs = data.getAllPairs();
        for (Pair pair : pairs) {
            if (pair instanceof ActorPair actorPair) {
                // Display co-star pair information using formatted string
                sb.append(String.format(PAIR_FORMAT, actorPair.getImdbLink1(), actorPair.getImdbLink2()));
            }
        }
        costars_label.setText(sb.toString());
        costars_label.setFont(new Font("Courier New", 10));
    }

    private String viewActors(ArrayList<Actor> actors) {
        StringBuilder sb = new StringBuilder();
        // Display header for actor information
        sb.append(ACTOR_HEADER);
        // Display separator line for formatting
        sb.append(ACTOR_SEPARATOR);
        sb.append("\n");
        for (Actor actor : actors) {
            // Convert boolean value for retired status to "T" or "F"
            String retired = "F";
            if (actor.isRetired()) {
                retired = "T";
            }
            // Display actor information using formatted string
            sb.append(String.format(ACTOR_FORMAT, actor.getName(), actor.getAge(), actor.getHeight(), actor.getCountry(), actor.getNumberOfAwards(), actor.getImdbLink(), actor.isRetired()));
        }
        return sb.toString();
    }

    public void load(File file) {
        // Reset status label appearance and text
        status_label.setTextFill(Color.BLACK); // Set text color to black
        status_label.setText(""); // Clear any previous text
        // Clear any previous text
        Data data = FileLoader.load(file);
        // Check if data loading was successful
        if (data == null) {
            // If loading failed, update status label with error message
            status_label.setTextFill(Color.RED); // Set text color to red
            status_label.setText(String.format("Failed to load from file %s%n", file)); // Set error message
        } else {
            // If loading was successful
            status_label.setTextFill(Color.GREEN); // Set text color to green
            status_label.setText(String.format("Loaded data from file %s%n", file)); // Set success message

            // Update main controller's data
            MainController.data = data;
            // Update views
            viewActors(); // Update Actors view
            viewCoStarPairs(); // Update co-star pairs view
            viewUnpaired(); // Update unpaired view

        }
    }

    @FXML
    void save(ActionEvent event) {
        // Create a file chooser dialog
        FileChooser fc = new FileChooser();
        fc.setTitle("Save a file"); // Set dialog title
        fc.setInitialDirectory(new File(".")); // Set initial directory to current directory
        fc.setInitialFileName("data.csv"); // Set default file name
        File file = fc.showSaveDialog(new Stage()); // Show the save dialog and wait for user input

        // Attempt to save data to the selected file
        if (FileSaver.save(file, data)) {
            // If saving was successful, update status label with success message
            status_label.setTextFill(Color.GREEN); // Set text color to green
            status_label.setText(String.format("Saved to file %s%n", file)); // Set success message
        } else {
            // If saving failed, update status label with error message
            status_label.setTextFill(Color.RED); // Set text color to red
            status_label.setText(String.format("Failed to save to file %s%n", file)); // Set error message
        }
    }


    @FXML
    void menuActorStatistics(ActionEvent event) {
        // Create an alert dialog for displaying statistics
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pair Summary"); // Set dialog title
        // Set dialog header text to describe the purpose of the statistics
        alert.setHeaderText("Statistics about actors and co-star pairs");

        // Construct a string with statistics information
        String s = "";
        s += String.format("There are %d actors.%n", data.getActorCount()); // Number of actors
        s += String.format("There are %d co-star pairs.%n", data.getPairCount()); // Number of co-star pairs
        s += String.format("There are %d actors not in a co-star pair.%n", data.getLoneActorsCount()); // Number of actors not in a pair

        // Set the content of the alert dialog to display the statistics
        alert.setContentText(s);

        // Show the alert dialog and wait for user interaction
        alert.showAndWait();
    }


    @FXML
    void menuPrintActorInfo(ActionEvent event) {
        // Create an alert dialog for displaying actor information
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Actor Info"); // Set dialog title

        // Retrieve actor information based on input
        Actor actor = data.getActor(info_id1.getText());

        // Set dialog header text to include actor's IMDb link
        alert.setHeaderText("Actor: " + actor.getImdbLink());

        // Construct content text with actor's details
        alert.setContentText(String.format("Name: %s%nImdb Link: %s%nAge: %s%nHeight: %s%nCountry: %s%nAwards Won: %s%n",
                actor.getName(), actor.getImdbLink(), actor.getAge(), actor.getHeight(), actor.getCountry(), actor.getNumberOfAwards()));

        // Show the alert dialog and wait for user interaction
        alert.showAndWait();
    }


    @FXML
    void menuCountryStats(ActionEvent event) {
        // Create an alert dialog for displaying country statistics
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Country Stats"); // Set dialog title

        // Retrieve list of actors in descending order of country count
        ArrayList<Actor> actorsInCountryDescendingList = data.getActorsInCountryDescendingList();

        // Construct content text with country statistics
        String s = "";
        for (Map.Entry<String, Integer> entry : data.getActorsPerCountryDescending().entrySet()) {
            s += (entry.getKey() + ": " + entry.getValue() + " actors\n");
        }

        // Set dialog header text to describe the content
        alert.setHeaderText("Actors per country in ascending order");

        // Set the content of the alert dialog to display the country statistics
        alert.setContentText(s);

        // Show the alert dialog and wait for user interaction
        alert.showAndWait();
    }

    @FXML
    void menuTallestActor() {
        // Get all actors from the data
        ArrayList<Actor> actors = data.getAllActors();
        int maxHeight = Integer.MIN_VALUE;
        String tallestActorName = "";

        // Iterate through all actors to find the tallest actor
        for (Actor actor : actors) {
            int height = actor.getHeight();
            if (height > maxHeight) {
                maxHeight = height;
                tallestActorName = actor.getName();
            }
        }

        // Display the tallest actor
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tallest Actor"); // Set dialog title
        // Set dialog header text to describe the content
        alert.setHeaderText("Tallest actor in the database");
        // Construct content text with information about the tallest actor
        String s = "The tallest actor in the database is " + tallestActorName + " with a height of " + maxHeight + " cm!";
        alert.setContentText(s);
        // Show the alert dialog and wait for user interaction
        alert.showAndWait();
    }


    @FXML
    void retire(ActionEvent event) {
        // Reset status label appearance and text
        status_label.setTextFill(Color.BLACK); // Set text color to black
        status_label.setText(""); // Clear any previous text

        // Get the ID of the actor to retire
        String id1 = retire_id1.getText();

        // Check if the actor is in a co-star pair
        if (!data.checkInPair(id1)) {
            // If the actor is in a pair, update status label with error message
            status_label.setTextFill(Color.RED); // Set text color to red
            status_label.setText(String.format("Actor is in a co-star pair so they can't retire until their pair is separated!")); // Set error message
        } else {
            // If the actor can be retired
            status_label.setTextFill(Color.GREEN); // Set text color to green
            status_label.setText(String.format("Actor has been retired")); // Set success message
            data.retire(id1); // Retire the actor
        }

        // Update views after retiring actor
        viewActors(); // Update actors view
        viewCoStarPairs(); // Update co-star pairs view
        viewUnpaired(); // Update unpaired view
    }


    @FXML
    void splitPair(ActionEvent event) {
        // Reset status label appearance and text
        status_label.setTextFill(Color.BLACK); // Set text color to black
        status_label.setText(""); // Clear any previous text

        // Get the IDs of the actors to split
        String id1 = sep_id1.getText();
        String id2 = sep_id2.getText();

        // Check if actor 1 is in a co-star pair
        if (!data.checkInPair(id1)) {
            // If actor 1 is not in a pair, update status label with error message
            status_label.setTextFill(Color.RED); // Set text color to red
            status_label.setText(String.format("Actor 1 is not in a co-star pair!")); // Set error message
        } else if (!data.checkInPair(id2)) {
            // If actor 2 is not in a pair, update status label with error message
            status_label.setTextFill(Color.RED); // Set text color to red
            status_label.setText(String.format("Actor 2 is not in a co-star pair!")); // Set error message
        } else if (!data.getPair(id1).equals(data.getPair(id2))) {
            // If actors are not in the same pair, update status label with error message
            status_label.setTextFill(Color.RED); // Set text color to red
            status_label.setText(String.format("These actors are not in the same co-star pair!")); // Set error message
        } else {
            // If actors are in the same pair, split the pair and update status label with success message
            status_label.setTextFill(Color.GREEN); // Set text color to green
            status_label.setText(String.format("Actor 1 and 2 are separated!")); // Set success message
            data.splitPair(id1, id2); // Split the pair
        }

        // Update views after splitting pair
        viewActors(); // Update actors view
        viewCoStarPairs(); // Update co-star pairs view
        viewUnpaired(); // Update unpaired view
    }


    @FXML
    void storeNewActor(ActionEvent event) {
        // Load the FXML file for adding a new actor
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("add.fxml"));
        Scene scene = null;
        try {
            // Create a scene with the loaded FXML and set its dimensions
            scene = new Scene(fxmlLoader.load(), 400, 400);
        } catch (IOException e) {
            // Throw a runtime exception if there is an error loading the FXML
            throw new RuntimeException(e);
        }

        // Get the controller for the add.fxml file
        AddController cont = fxmlLoader.getController();
        // Pass data and reference to this controller to the AddController
        cont.setData(data, this);

        // Create a new stage for displaying the scene
        Stage stage = new Stage();
        stage.setTitle("Add New Actor"); // Set stage title
        stage.setScene(scene); // Set the scene to the stage
        stage.showAndWait(); // Display the stage and wait for it to be closed
    }


    @FXML
    void storeNewCoStarPair(ActionEvent event) {
        // Reset status label appearance and text
        status_label.setTextFill(Color.BLACK); // Set text color to black
        status_label.setText(""); // Clear any previous text

        // Get the IDs of the actors for the new co-star pair
        String id1 = costar_id1.getText();
        String id2 = costar_id2.getText();

        // Check if actor 1 is already in a co-star pair
        if (data.checkInPair(id1)) {
            // If actor 1 is already in a pair, update status label with error message
            status_label.setTextFill(Color.RED); // Set text color to red
            status_label.setText(String.format("Actor 1 is already in a co-star pair!")); // Set error message
        } else if (data.checkInPair(id2)) {
            // If actor 2 is already in a pair, update status label with error message
            status_label.setTextFill(Color.RED); // Set text color to red
            status_label.setText(String.format("Actor 2 is already in a co-star pair!")); // Set error message
        } else {
            // If actors are not in pairs, create a new co-star pair and update status label with success message
            status_label.setTextFill(Color.GREEN); // Set text color to green
            status_label.setText(String.format("Actor 1 and 2 are now in a co-star pair!")); // Set success message
            data.storeNewCoStarPair(id1, id2); // Store new co-star pair
        }

        // Update views after storing new co-star pair
        viewActors(); // Update actors view
        viewCoStarPairs(); // Update co-star pairs view
        viewUnpaired(); // Update unpaired view
    }
}
