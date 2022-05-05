/*
 * File:         Controller.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * GUI main scene controller - TO BE DONE.
 */
package app.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class represents the GUI main scene controller.
 */
public class Controller {
    @FXML
    TextField nameTextField;

    @FXML
    private VBox class_vbox;

    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    DraggableObject draggableObject = new DraggableObject();

    /**
     * Method is run on event and it changes the scene to scene1
     * and displays a name given in the GUI textField.
     *
     * @param event event on that the scene is changed
     * @throws IOException
     */
    public void login(ActionEvent event) throws IOException {
        // get the string from the GUI textField
        String username = nameTextField.getText();

        // set the new scene - scene1
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene1.fxml"));
        root = loader.load();

        Scene1Controller scene1Controller = loader.getController();
        scene1Controller.displayName(username);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method makes an object draggable.
     *
     * @param event
     */
    public void makeDraggable(ActionEvent event) {
        draggableObject.makeDraggable(class_vbox);
    }

    /**
     * Method adds a new class to the diagram.
     *
     * @param event
     * @param stage
     */
    public static void addClass(ActionEvent event, Stage stage) {
        scene = new Scene(root, 500, 500, Color.LIGHTCYAN);
        stage.setScene(scene);
        stage.show();
    }


}
