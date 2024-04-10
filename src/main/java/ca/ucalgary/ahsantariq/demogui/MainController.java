package ca.ucalgary.ahsantariq.demogui;

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
}