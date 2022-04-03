package app.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Scene1Controller {

    @FXML
    Label nameLabel;

    public void displayName(String username) {
        nameLabel.setText("Hello: " + username);
    }
}
