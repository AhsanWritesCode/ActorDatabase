package ca.ucalgary.ahsantariq.demogui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AddController {

    @FXML
    private TextField passAge;

    @FXML
    private TextField passAwardsWon;

    @FXML
    private TextField passCountry;

    @FXML
    private TextField passHeight;

    @FXML
    private TextField passImdbLink;

    @FXML
    private TextField passName;

    @FXML
    private Label status_label;

    private Data data;

    private MainController mc;

    public void setData(Data data, MainController mc) {
        this.data = data;
        this.mc = mc;
    }

    @FXML
    void add(ActionEvent event) {
        String name = passName.getText();
        if (name.isEmpty()) {
            Alert about = new Alert(Alert.AlertType.ERROR);
            about.setTitle("No Input Provided For Name");
            about.setHeaderText("Message");
            about.setContentText("You didn't enter anything for name!");

            // Set light blue style directly
            DialogPane dialogPane = about.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #FF7F7F; " // Light blue background
                    + "-fx-font-family: Arial; " // Arial font family
                    + "-fx-font-size: 14px; " // Font size
                    + "-fx-text-fill: #000000;"); // Black text color

            about.showAndWait();
        }
        int age = 0;
        try {
            age = Integer.parseInt(passAge.getText());
        } catch (NumberFormatException e) {
            Alert about = new Alert(Alert.AlertType.ERROR);
            about.setTitle("Cannot Parse Age");
            about.setHeaderText("Message");
            about.setContentText("You've entered something other than an integer for age!");

            // Set light blue style directly
            DialogPane dialogPane = about.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #FF7F7F; " // Light blue background
                    + "-fx-font-family: Arial; " // Arial font family
                    + "-fx-font-size: 14px; " // Font size
                    + "-fx-text-fill: #000000;"); // Black text color

            about.showAndWait();
        }
        int height = 0;
        try {
            height = Integer.parseInt(passHeight.getText());
        } catch (NumberFormatException e) {
            Alert about = new Alert(Alert.AlertType.ERROR);
            about.setTitle("Cannot Parse Height");
            about.setHeaderText("Message");
            about.setContentText("You've entered something other than an integer for height!");

            // Set light blue style directly
            DialogPane dialogPane = about.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #FF7F7F; " // Light blue background
                    + "-fx-font-family: Arial; " // Arial font family
                    + "-fx-font-size: 14px; " // Font size
                    + "-fx-text-fill: #000000;"); // Black text color

            about.showAndWait();
        }
        String country = passCountry.getText();
        if (country.isEmpty()) {
            Alert about = new Alert(Alert.AlertType.ERROR);
            about.setTitle("No Input Provided For Country");
            about.setHeaderText("Message");
            about.setContentText("You didn't enter anything for country!");

            // Set light blue style directly
            DialogPane dialogPane = about.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #FF7F7F; " // Light blue background
                    + "-fx-font-family: Arial; " // Arial font family
                    + "-fx-font-size: 14px; " // Font size
                    + "-fx-text-fill: #000000;"); // Black text color

            about.showAndWait();
        }
        int awardsWon = 0;
        try {
            awardsWon = Integer.parseInt(passAwardsWon.getText());
        } catch (NumberFormatException e) {
            Alert about = new Alert(Alert.AlertType.ERROR);
            about.setTitle("Cannot Parse Awards Won");
            about.setHeaderText("Message");
            about.setContentText("You've entered something other than an integer for awards won!");

            // Set light blue style directly
            DialogPane dialogPane = about.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #FF7F7F; " // Light blue background
                    + "-fx-font-family: Arial; " // Arial font family
                    + "-fx-font-size: 14px; " // Font size
                    + "-fx-text-fill: #000000;"); // Black text color

            about.showAndWait();
        }
        String imdbLink = passImdbLink.getText();
        if (imdbLink.isEmpty()) {
            Alert about = new Alert(Alert.AlertType.ERROR);
            about.setTitle("No Input Provided For IMDB Link!");
            about.setHeaderText("Message");
            about.setContentText("You didn't enter anything for IMDB link!");

            // Set light blue style directly
            DialogPane dialogPane = about.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #FF7F7F; " // Light blue background
                    + "-fx-font-family: Arial; " // Arial font family
                    + "-fx-font-size: 14px; " // Font size
                    + "-fx-text-fill: #000000;"); // Black text color

            about.showAndWait();
        }
        else if (data.actorExists(imdbLink)) {
            Alert about = new Alert(Alert.AlertType.ERROR);
            about.setTitle("Actor Already Exists");
            about.setHeaderText("Message");
            about.setContentText("An actor with this IMDB Link is already in the database!");

            // Set light red style directly
            DialogPane dialogPane = about.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #FF7F7F; " // Light blue background
                    + "-fx-font-family: Arial; " // Arial font family
                    + "-fx-font-size: 14px; " // Font size
                    + "-fx-text-fill: #000000;"); // Black text color

            about.showAndWait();
        }
        if (!name.isEmpty() && age != 0 && height != 0 && !country.isEmpty() && awardsWon >= 0 && !imdbLink.isEmpty()) {
            data.storeNewActor(name, age, height, country, awardsWon, imdbLink);
        }
        mc.viewUnpaired();
        mc.viewActors();
        mc.viewCoStarPairs();
    }
}
