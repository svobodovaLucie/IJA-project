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
import app.umlGui.DiagramLoader;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import jdk.jshell.Diag;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Class represents the main part of the GUI.
 */
public class GuiMain extends Application {
    /**
     * Main method for the GUI.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // run the backend
        //MainApplication backend = new MainApplication();
        //backend.main(args[0]);

        // run GUI
        launch(args);
    }

    /**
     * JavaFX Application Thread.
     *
     * @param primaryStage stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // set FXML loader
        //FXMLLoader loader = new FXMLLoader();
        //System.out.println("UNO");

        //loader.setLocation(getClass().getResource("/AppGuiMain.fxml"));
        //Parent root = loader.load();

        // load the JSON file
        Parameters params = getParameters();
        List<String> args = params.getRaw();
        DiagramLoader jsonLoader = new DiagramLoader();
        Group root = jsonLoader.loadClassDiagramGui(args.get(0));

        // set the scene
        Scene scene = new Scene(root, 600, 600, Color.WHITE);
        scene.getStylesheets().add("stylesheet.css");

        // add save button (fix releasing the button)
        Menu save = new Menu();
        Button saveButton = new Button("Save JSON");
        saveButton.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-color: transparent;\n" +
                "-fx-font-weight: regular;\n" +
                "-fx-font-size: 15;");
        saveButton.setOnAction(Controller::saveJSON);
        save.setGraphic(saveButton);

        // add undo button - TODO, fix releasing the button
        Menu undo = new Menu();
        Button undoButton = new Button("Undo");
        undoButton.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-color: transparent;\n" +
                "-fx-font-weight: regular;\n" +
                "-fx-font-size: 15;");
        //undoButton.setOnAction(Controller::saveJSON);
        undo.setGraphic(undoButton);

        // add MenuBar
        MenuBar menuBar = new MenuBar(save, undo);
        menuBar.useSystemMenuBarProperty();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.getChildren().add(menuBar);

        // addClass button
        Button addClass = new Button("Add Class");
        addClass.setLayoutY(50);
        addClass.setLayoutX(100);
        root.getChildren().add(addClass);
        addClass.setOnAction(e -> root.getChildren().add(new ClassGuiElement("My Name")));

        // set the stage
        primaryStage.setTitle("ija-app: diagrams");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
