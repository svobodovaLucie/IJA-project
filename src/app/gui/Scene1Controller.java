/*
 * File:         Scene1Controller.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * GUI Scene1 controller.
 */
package app.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Class represents a GUI Scene1 controller.
 */
public class Scene1Controller {

    @FXML
    Label nameLabel;

    /**
     * Method display string given in username.
     *
     * @param username string to be displayed
     */
    public void displayName(String username) {
        nameLabel.setText("Hello: " + username);
    }
}
