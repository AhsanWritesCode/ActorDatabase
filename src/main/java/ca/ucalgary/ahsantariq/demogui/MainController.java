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
    private static final String PAIR_HEADER = String.format(PAIR_FORMAT, "IMDBLINK1", "\t\t\t  IMDBLINK2");
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
    }

    public void viewUnpaired() {
        // Retrieve and print information about all actors from the data
        ArrayList<Actor> actors = data.getActorsNotInPair();
        actors.sort(new ActorNameImdbLinkComparator());
        String s = viewActors(actors);
        unpaired_label.setText(s);
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
        status_label.setTextFill(Color.BLACK);
        status_label.setText("");
        Data data = FileLoader.load(file);
        if (data == null) {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Failed to load from file %s%n", file));
        } else {
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Loaded data from file %s%n", file));
            MainController.data = data;
            viewActors();
            viewCoStarPairs();
            viewUnpaired();

        }
    }

    @FXML
    void save(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save a file");
        fc.setInitialDirectory(new File("."));
        fc.setInitialFileName("data.csv");
        File file = fc.showSaveDialog(new Stage());
        if (FileSaver.save(file, data)) {
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Saved to file %s%n", file));
        } else {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Failed to save to file %s%n", file));
        }
    }

    @FXML
    void menuActorStatistics(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pair Summary");
        // should this be the text field instead of the button?
        alert.setHeaderText("Statistics about actors and co-star pairs");
        String s = "";
        s += String.format("There are %d actors.%n", data.getActorCount());
        s += String.format("There are %d co-star pairs.%n", data.getPairCount());
        s += String.format("There are %d actors not in a co-star pair.%n", data.getLoneActorsCount());
        alert.setContentText(s);
        alert.showAndWait();
    }

    @FXML
    void menuPrintActorInfo(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Actor Info");
        // should this be the text field instead of the button?
        Actor actor = data.getActor(info_id1.getText());
        alert.setHeaderText("Actor: " + actor.getImdbLink());
        alert.setContentText(String.format("Name: %s%nImdb Link: %s%nAge: %s%nHeight: %s%nCountry: %s%nAwards Won: %s%n", actor.getName(), actor.getImdbLink(), actor.getAge(), actor.getHeight(), actor.getCountry(), actor.getNumberOfAwards()));
        alert.showAndWait();
    }

    @FXML
    void menuCountryStats(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Country Stats");
        ArrayList<Actor> ActorsInCountryDescendingList = data.getActorsInCountryDescendingList();
        String s = "";
        for (Map.Entry<String, Integer> entry : data.getActorsPerCountryDescending().entrySet()) {
            s += (entry.getKey() + ": " + entry.getValue() + " actors\n");
        }
        alert.setHeaderText("Actors per country in ascending order");
        alert.setContentText(s);
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
        alert.setTitle("Tallest Actor");
        System.out.printf("The tallest actor in the database is %s with a height of %d cm%n", tallestActorName, maxHeight);
        String s = "The tallest actor in the database is " + tallestActorName +  " with a height of " + maxHeight + " cm!";
        alert.setHeaderText("Tallest actor in the data base");
        alert.setContentText(s);
        alert.showAndWait();
    }

    @FXML
    void retire(ActionEvent event) {
        status_label.setTextFill(Color.BLACK);
        status_label.setText("");
        String id1 = retire_id1.getText();
        if (!data.checkInPair(id1)) {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Actor is in a co-star pair so they can't retire until their pair is seperated!"));

        } else {
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Actor has been retired"));
            data.retire(id1);
        }
        viewActors();
        viewCoStarPairs();
        viewUnpaired();
    }

    @FXML
    void splitPair(ActionEvent event) {
        status_label.setTextFill(Color.BLACK);
        status_label.setText("");
        String id1 = sep_id1.getText();
        String id2 = sep_id2.getText();
        if (!data.checkInPair(id1)) {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Actor 1 is not in a co-star pair!"));

        } else if (!data.checkInPair(id2)) {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Actor 2 is not in a co-star pair!"));
        } else if (data.getPair(id1).equals(data.getPair(id2))) {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("These actors are not in the same co-star pair!"));
        } else {
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Actor 1 and 2 are separated!"));
            data.splitPair(id1, id2);
        }
        viewActors();
        viewCoStarPairs();
        viewUnpaired();
    }

    @FXML
    void storeNewActor(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("add.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 400, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddController cont = fxmlLoader.getController();
        cont.setData(data, this);
        Stage stage = new Stage();
        stage.setTitle("Add New Actor");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void storeNewCoStarPair(ActionEvent event) {
        status_label.setTextFill(Color.BLACK);
        status_label.setText("");
        String id1 = costar_id1.getText();
        String id2 = costar_id2.getText();
        if (data.checkInPair(id1)) {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Actor 1 is already in a co-star pair!"));

        } else if (data.checkInPair(id2)) {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Actor 2 is already in a co-star pair!"));
        } else {
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Actor 1 and 2 are now in a co-star pair!"));
            data.storeNewCoStarPair(id1, id2);
        }
        viewActors();
        viewCoStarPairs();
        viewUnpaired();
    }

}
