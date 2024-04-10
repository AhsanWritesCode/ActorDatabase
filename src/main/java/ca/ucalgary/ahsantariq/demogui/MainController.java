package ca.ucalgary.ahsantariq.demogui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button createCoStarButton, splitCoStarPair;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private void storeNewCoStarPair(){

    }
    @FXML
    private void splitPair(){

    }
    @FXML
    public void storeNewActor(ActionEvent actionEvent) {
    }
    @FXML
    public void load(ActionEvent actionEvent) {

    }

    @FXML
    public void save(ActionEvent actionEvent) {
    }


    @FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();

    }

    @FXML
    public void about(ActionEvent actionEvent) {
        // Create an alert to display information about the application
        Alert about = new Alert(Alert.AlertType.INFORMATION, "Author: Rahnuha Nurain, \n Ahsan Tariq, \n Ethan Braum  \n Email: rahnuha.nurain@ucalgary.ca,\n ahsan.tariq@ucalgary.ca,\n ethan.braum@ucalgary.ca " +
                "nVersion: v1.0 \nThis " +
                " retrieves and organizes actor information from IMDb links for effortless access and storage\"\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n", ButtonType.OK);
        // Set the title of the alert
        about.setTitle("About");
        // Set the header text of the alert
        about.setHeaderText("Message");
        // Show the alert and wait for user interaction
        about.showAndWait();
    }

    @FXML
    public void retire(ActionEvent actionEvent) {
    }

    @FXML
    public void menuPrintActorInfo(ActionEvent actionEvent) {
    }

    @FXML
    public void menuActorStatistics(ActionEvent actionEvent) {
    }

    @FXML
    public void getLoneActorsCount(ActionEvent actionEvent) {
    }
}