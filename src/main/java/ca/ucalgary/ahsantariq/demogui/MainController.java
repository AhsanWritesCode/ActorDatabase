package ca.ucalgary.ahsantariq.demogui;

import ca.ucalgary.ahsantariq.demogui.comparators.ActorNameImdbLinkComparator;
import ca.ucalgary.ahsantariq.demogui.objects.Actor;
import ca.ucalgary.ahsantariq.demogui.objects.ActorPair;
import ca.ucalgary.ahsantariq.demogui.objects.Pair;
import ca.ucalgary.ahsantariq.demogui.util.FileSaver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class MainController {

    private static Data data = new Data();

    // Format string for displaying actor information
    private static final String ACTOR_FORMAT = "%-20s\t%-3s\t%-6s\t%-30s\t%-11s\t%-36s\t%-7s%n";
    // Header for displaying actor information
    private static final String ACTOR_HEADER = String.format(ACTOR_FORMAT, "NAME", "AGE", "HEIGHT", "COUNTRY", "# OF AWARDS", "IMDB LINK", "RETIRED");
    // Separator string for formatting purposes
    private static String ACTOR_SEPARATOR = "";

    // Static block to initialize the separator string
    static {
        // Loop through each character in the actor header and concatenate '-' to form a separator string
        for (int i = 0; i < ACTOR_HEADER.length(); i++) {
            ACTOR_SEPARATOR += "-";
        }
    }

    // Format string for displaying actor pairs
    private static final String PAIR_FORMAT = "%-32s %-33s%n";
    // Header for displaying actor pairs
    private static final String PAIR_HEADER = String.format(PAIR_FORMAT, "IMDBLINK1", "IMDBLINK2");
    // Separator string for formatting purposes

    private static String PAIR_SEP = "";

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
    private TextField costar_id1;

    @FXML
    private TextField costar_id2;

    @FXML
    private Label costars_label;

    @FXML
    private Button info_id1;

    @FXML
    private TextField retire_id1;

    @FXML
    private TextField sep_id1;

    @FXML
    private TextField sep_id2;

    @FXML private Label status_label;
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
    void getLoneActorsCount(ActionEvent event) {

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
    }

    private void viewActors() {
        // Retrieve and print information about all actors from the data
        ArrayList<Actor> actors = data.getAllActors();
        actors.sort(new ActorNameImdbLinkComparator());
        viewActors(actors);
        viewCoStarPairs();
    }

    private void viewCoStarPairs() {
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

    private void viewActors(ArrayList<Actor> actors) {
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
        actors_label.setText(sb.toString());
    }

    private void load(File file){
        status_label.setTextFill(Color.BLACK);
        status_label.setText("");
        if (data == null){
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Failed to load from file %s%n", file));
        } else{
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Loaded data from file %s%n", file));
            MainController.data = data;
        }
    }

    @FXML
    void save(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save a file");
        fc.setInitialDirectory(new File("."));
        fc.setInitialFileName("data.csv");
        File file = fc.showSaveDialog(new Stage());
        if (FileSaver.save(file, data)){
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Saved to file %s%n", file));
        } else {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Failed to save to file %s%n", file));
        }
    }

    @FXML
    void menuActorStatistics(ActionEvent event) {

    }

    @FXML
    void menuPrintActorInfo(ActionEvent event) {

    }

    @FXML
    void retire(ActionEvent event) {

    }

    @FXML
    void splitPair(ActionEvent event) {
        status_label.setTextFill(Color.BLACK);
        status_label.setText("");
        String id1 = sep_id1.getText();
        String id2 = sep_id2.getText();
        if(!data.checkInPair(id1)){
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Actor 1 is not in a co-star pair!"));

        }
        else if(!data.checkInPair(id2)){
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Actor 2 is not in a co-star pair!"));
        }
        else if (data.getPair(id1).equals(data.getPair(id2))) {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("These actors are not in the same co-star pair!"));
        }
        else {
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Actor 1 and 2 are separated!"));
            data.splitPair(id1,id2);
        }
        viewCoStarPairs();
    }

    @FXML
    void storeNewActor(ActionEvent event) {

    }

    @FXML
    void storeNewCoStarPair(ActionEvent event) {

    }

}
