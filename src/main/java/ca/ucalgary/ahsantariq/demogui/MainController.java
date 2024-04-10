package ca.ucalgary.ahsantariq.demogui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    }

    @FXML
    public void about(ActionEvent actionEvent) {
    }
}