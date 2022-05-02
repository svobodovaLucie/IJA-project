/*
 * File:         GuiMain.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od GuiMain class that represents
 * the main part of the application.
 *
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
import app.umlGui.UMLSeqDiaGui;

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

        // both are roots because they will be in different Scene
        // there will be scene for each seq diagram
        List<Group> rootSeq  = jsonLoader.loadSeqDiagramsGui(args.get(0));
        Group rootClass = jsonLoader.loadClassDiagramGui(args.get(0));   // TODO fix if we don't want to load a diagram from a file

        // set the scene for sequence diagram
        // todo button (add Message) (should choose from some types)

        // todo maybe like variable for just active scene ....
        for (int i = 0; i < rootSeq.size(); i++){
            this.createSeqDiagScene(i, rootSeq, primaryStage);
        }

        // set the scene
        Scene scene = new Scene(rootClass, 600, 600, Color.WHITE);
        scene.getStylesheets().add("stylesheet.css");

        // add save button (fix releasing the button)
        Button saveButton = this.createButton("Save JSON", 0);
        saveButton.setOnAction(e -> DiagramSaver.saveJSON(e, rootClass));
        Menu save = this.createMenu(saveButton);

        // add undo button - TODO, fix releasing the button
        Button undoButton = this.createButton("Undo", 0);
        Menu undo = this.createMenu(undoButton);

        // add MenuBar
        MenuBar menuBar = new MenuBar(save, undo);
        menuBar.useSystemMenuBarProperty();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        rootClass.getChildren().add(menuBar);

        // addClass button
        Button addClass = createButton("Add Class", 1);
        addClass.setOnAction(e -> rootClass.getChildren().add(new UMLClassGui("YourClass")));
        addClass.setLayoutY(50);
        addClass.setLayoutX(500);
        rootClass.getChildren().add(addClass);

        // set the stage
        primaryStage.setTitle("ija-app: diagrams");
        primaryStage.setScene(scene);
        primaryStage.show();

        // show the help
        this.helpMessage();

    }

    /**
     * Create scene for sequece diagram
      * @param n
     */
    private void createSeqDiagScene(int n, List<Group> rootSeq, Stage primaryStage){

        UMLSeqDiaGui temp = (UMLSeqDiaGui)rootSeq.get(n).getChildren().get(0);
        temp.addAllActorsGUI();
        System.out.println(temp.getName());
        Scene sceneSeqTest = new Scene(rootSeq.get(n), 1000, 750, Color.WHITE);
        sceneSeqTest.getStylesheets().add("stylesheet.css");
        Stage sceneSeqStage = new Stage();
        sceneSeqStage.setScene(sceneSeqTest);
        sceneSeqStage.setTitle("Sequence Diagram Editor");
        sceneSeqStage.show();

        // add save button (fix releasing the button)
        Button saveButton = this.createButton("Save JSON", 0);
        saveButton.setOnAction(e -> DiagramSaver.saveJSON(e, rootSeq.get(n)));
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
