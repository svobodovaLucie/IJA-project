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

import app.backend.Diagrams;
import app.umlGui.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.List;

/**
 * Class represents the main part of the GUI.
 */
public class GuiMain extends Application {

    // BE diagrams
    static Diagrams BEdiagrams;


    /**
     * Main method for the GUI.
     *
     * @param args the command line arguments
     */
    public static void main(String args[], Diagrams diagrams) {
        BEdiagrams = diagrams;
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

        // load GUI from BE for Class Diagram
        GuiLoader guiLoader = new GuiLoader();
        Group rootClass = guiLoader.loadClassDiagramGui(BEdiagrams.getClassDiagram());

        // load seq diagram from BE
        // both are roots because they will be in different Scene
        // there will be scene for each seq diagram

        List<Group> rootSeq = guiLoader.loadSeqDiagramGui(BEdiagrams.getSeqDiagrams());

        System.out.println(BEdiagrams.getSeqDiagrams());

        // create class diagram scene
        createClassDiagScene(rootClass);

        // set the scene for sequence diagram
        // todo button (add Message) (should choose from some types)
        // todo maybe like variable for just active scene ....
        for (int i = 0; i < rootSeq.size(); i++){
            this.createSeqDiagScene(i, rootSeq, primaryStage);
        }

        // show the help
        this.helpMessage();
    }

    private void createClassDiagScene(Group rootClass){
        // set the scene
        Stage stage = new Stage();
        Scene scene = new Scene(rootClass, stage.getMaxWidth(), stage.getMaxHeight(), Color.WHITE);

        // options for adding new elements
        Menu options = new Menu("Add...");
        options.setStyle("-fx-font-size: 15;");
        // button for adding new class
        MenuItem addClass = new MenuItem("Add class");
        addClass.setOnAction(e -> {
            try {
                rootClass.getChildren().add(new UMLClassGui(BEdiagrams.getClassDiagram().createClass("Untitled"), (UMLClassDiagramGui) rootClass.getChildren().get(0)));
            } catch (Exception exception) {
                System.out.println("Can't create two classes with the same name!");
                // TODO alert box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Two classes with the same name.");
                alert.setContentText("There is already a class with the name Untitled.\n" +
                                     "Two classes with the same name can't be created.");   // TODO default name
                alert.show();
            }
        });
        // button for adding new interface
        MenuItem addInterface = new MenuItem("Add interface");
        addInterface.setOnAction(e -> {
            System.out.println("Adding new interface (not implemented yet)");
        });
        // button for adding new relation
        MenuItem addRelation = new MenuItem("Add relation");
        addRelation.setOnAction(e -> {
            System.out.println("Adding new relation (not implemented yet)");
        });

        options.getItems().addAll(addClass, addInterface, addRelation);

        // add save button (fix releasing the button)
        Button saveButton = this.createButton("Save JSON", 0);
        saveButton.setOnAction(e -> DiagramSaverNoGui.saveJSON(e, BEdiagrams, "savedDiagram.json"));
        //saveButton.setOnAction(e -> DiagramSaver.saveJSON(e, rootClass));
        Menu save = this.createMenu(saveButton);

        // add undo button - TODO, fix releasing the button
        Button undoButton = this.createButton("Undo", 0);
        undoButton.setOnAction(e -> {
            System.out.println("undoButton.onAction()");
            UMLClassDiagramGui umlClassDiagramGui = (UMLClassDiagramGui) rootClass.getChildren().get(0);
            umlClassDiagramGui.undo();
        });
    //(UMLClassDiagramGui)rootClass.getChildren().get(0).undo()));
        Menu undo = this.createMenu(undoButton);

        // add MenuBar
        MenuBar menuBar = new MenuBar(options, save, undo);
        menuBar.useSystemMenuBarProperty();
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        rootClass.getChildren().add(menuBar);

        // set the stage
        stage.setTitle("ija-app: diagrams");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Create scene for sequece diagram
      * @param n
     */
    private void createSeqDiagScene(int n, List<Group> rootSeq, Stage primaryStage){
        // uz je vse nactene z JSONu v jednotlivych UMLSeqDiaGui -> staci jen zobrazit

        Scene sceneSeqTest = new Scene(rootSeq.get(n), 1000, 750, Color.WHITE);

        // add save button (fix releasing the button)
        Button saveButton = this.createButton("Save JSON", 0);
        //saveButton.setOnAction(e -> DiagramSaver.saveJSON(e, rootSeq.get(n)));
        Menu save = this.createMenu(saveButton);

        // add undo button - TODO, fix releasing the button
        Button undoButton = this.createButton("Undo", 0);
        Menu undo = this.createMenu(undoButton);

        // add MenuBar
        MenuBar menuBar = new MenuBar(save, undo);
        //menuBar.useSystemMenuBarProperty();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        rootSeq.get(n).getChildren().add(menuBar);

        // Add Actor button
        Button addActor = createButton("Add Actor", 1);
        //addClass.setOnAction(e -> rootSeq.get(n).getChildren().add(new UMLClassGui("YourClass")));
        addActor.setLayoutY(50);
        addActor.setLayoutX(10);
        rootSeq.get(n).getChildren().add(addActor);

        // Add Message button
        Button addMessage = createButton("Add Message", 1);
        //addClass.setOnAction(e -> rootSeq.get(n).getChildren().add(new UMLClassGui("YourClass")));
        addMessage.setLayoutY(85);
        addMessage.setLayoutX(10);
        rootSeq.get(n).getChildren().add(addMessage);

        primaryStage.setScene(sceneSeqTest);
        primaryStage.setTitle("Sequence Diagram Editor");
        primaryStage.show();
    }

    /**
     * Just print help message
     */
    private void helpMessage(){
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

    /**
     * Create button with given text
     * @param text Button text that ll be displayed
     * @param style 0 for normal buttons
     *              1 for diagrams buttons
     * @return new button
     */
    private Button createButton(String text, int style){
        Button button = new Button(text);
        if (style == 0){
            button.setStyle("-fx-background-color: transparent;\n" +
                    "-fx-border-color: transparent;\n" +
                    "-fx-font-size: 15;");
        }
        else if (style == 1){
            button.setStyle("-fx-background-color: transparent;\n" +
                    "-fx-border-color: black;\n" +
                    "-fx-font-size: 15;");
        }
        return button;
    }

    /**
     * Create menu with given button
     * @param button button that ll be connected to menu
     * @return new menu
     */
    private Menu createMenu(Button button){
        Menu menu = new Menu();
        menu.setGraphic(button);
        return menu;
    }
}
