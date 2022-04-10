/*
 * File:         GuiMain.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od GuiMain class that represents
 * the part of the application and calls the main backend function.
 */
package app.gui;

import app.backend.MainApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Class represents the main part of the GUI.
 */
public class GuiMain extends Application {
    /**
     * Main method for the GUI.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {

        // run the backend
        MainApplication backend = new MainApplication();
        backend.main(args[0]);

        // run GUI
        launch(args);
    }

    /**
     * JavaFX Application Thread.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // set FXML loader
        //FXMLLoader loader = new FXMLLoader();
        //System.out.println("UNO");

        //loader.setLocation(getClass().getResource("/AppGuiMain.fxml"));
        //Parent root = loader.load();

        // try
        Group root = new Group();
        // set the scene
        Scene scene = new Scene(root, 600, 600, Color.WHITE);
        System.out.println("DEUX");
        scene.getStylesheets().add("stylesheet.css");

        //int i = 100;
        //FlowPane pane = new FlowPane();

        // addClass button
        Button addClass = new Button("Add Class");
        addClass.setLayoutY(50);
        addClass.setLayoutX(100);
        root.getChildren().add(addClass);
        addClass.setOnAction(e -> root.getChildren().add(new ClassGuiElement("My Name")));




        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        // set and show the stage
        /*
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
        */
    }
}
