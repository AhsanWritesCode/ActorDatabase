package ca.ucalgary.ahsantariq.demogui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;

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

    }

    @FXML
    void exit(ActionEvent event) {

    }

    @FXML
    void getLoneActorsCount(ActionEvent event) {

    }

    @FXML
    void load(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load a file");


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
    void menuActorStatistics(ActionEvent event) {

    }

    @FXML
    void menuPrintActorInfo(ActionEvent event) {

    }

    @FXML
    void retire(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void splitPair(ActionEvent event) {

    }

    @FXML
    void storeNewActor(ActionEvent event) {

    }

    @FXML
    void storeNewCoStarPair(ActionEvent event) {

    }

}
