/*
 * File:         GuiMain.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od GuiMain class that represents
 * the main part of the application.
 */
package app.gui;

import app.umlGui.DiagramLoader;
import app.umlGui.DiagramSaver;
import app.umlGui.UMLClassGui;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Scene;
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
        System.out.println("tu");
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

        // load the JSON file
        Parameters params = getParameters();
        System.out.println(params);
        List<String> args = params.getRaw();
        DiagramLoader jsonLoader = new DiagramLoader();


        Group root_seq   = jsonLoader.loadSeqDiagramsGui(args.get(0));
        Group root_class = jsonLoader.loadClassDiagramGui(args.get(0));   // TODO fix if we don't want to load a diagram from a file

        // set the scene
        Scene scene = new Scene(root_class, 600, 600, Color.LIGHTGRAY);
        scene.getStylesheets().add("stylesheet.css");

        // add save button (fix releasing the button)
        Menu save = new Menu();
        Button saveButton = new Button("Save JSON");
        saveButton.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-color: transparent;\n" +
                "-fx-font-size: 15;");
        saveButton.setOnAction(e -> DiagramSaver.saveJSON(e, root_class));
        save.setGraphic(saveButton);

        // add undo button - TODO, fix releasing the button
        Menu undo = new Menu();
        Button undoButton = new Button("Undo");
        undoButton.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-color: transparent;\n" +
                "-fx-font-size: 15;");
        undo.setGraphic(undoButton);

        // add MenuBar
        MenuBar menuBar = new MenuBar(save, undo);
        menuBar.useSystemMenuBarProperty();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root_class.getChildren().add(menuBar);

        // addClass button
        Button addClass = new Button("Add Class");
        addClass.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-color: black;\n" +
                "-fx-font-size: 15;");
        addClass.setLayoutY(50);
        addClass.setLayoutX(500);
        root_class.getChildren().add(addClass);
        addClass.setOnAction(e -> root_class.getChildren().add(new UMLClassGui("YourClass")));

        // set the stage
        primaryStage.setTitle("ija-app: diagrams");
        primaryStage.setScene(scene);
        primaryStage.show();

        // show the help
        Group helpGroup = new Group();
        Text text = new Text();
        text.setFont(new Font(15));
        helpGroup.setStyle("-fx-label-padding: 100 100 100 100");
        text.setWrappingWidth(350);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText("\n\n    Your JSON file was loaded.\n\n" +
                "    Attributes and methods can be edited\n" +
                "    (adding them is not implemented yet).\n\n" +
                "    New class can be added with \n" +
                "    \"Add Class\" button.\n\n" +
                "    Your diagrams can be saved with \n" +
                "    \"Save JSON\" option on the Menu Bar.\n\n" +
                "    Your file will be saved to:\n" +
                "    \"dest/savedDiagram.json\".");
        helpGroup.getChildren().add(text);
        Scene helpScene = new Scene(helpGroup, 400, 400);
        Stage helpStage = new Stage();
        helpStage.setScene(helpScene);
        helpStage.setTitle("ija-app: help");
        helpStage.show();
    }
}
