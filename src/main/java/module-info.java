module ca.ucalgary.ahsantariq.demogui {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.ucalgary.ahsantariq.demogui to javafx.fxml;
    exports ca.ucalgary.ahsantariq.demogui;
}