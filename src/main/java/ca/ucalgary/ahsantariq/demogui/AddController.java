package ca.ucalgary.ahsantariq.demogui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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

    private Data data;

    private MainController mc;

    public void setData(Data data, MainController mc) {
        this.data = data;
        this.mc = mc;
    }

    @FXML
    void add(ActionEvent event) {
        String name = passName.getText();
        int age = Integer.parseInt(passAge.getText());
        int height = Integer.parseInt(passHeight.getText());
        String country = passCountry.getText();
        int awardsWon = Integer.parseInt(passAwardsWon.getText());
        String imdbLink = passImdbLink.getText();
        data.storeNewActor(name, age, height, country, awardsWon, imdbLink);
        mc.viewUnpaired();
        mc.viewActors();
        mc.viewCoStarPairs();
    }
}
