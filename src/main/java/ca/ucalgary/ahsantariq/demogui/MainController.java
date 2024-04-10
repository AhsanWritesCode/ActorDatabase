package ca.ucalgary.ahsantariq.demogui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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