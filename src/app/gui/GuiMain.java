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

import app.uml.UMLMessage;
import app.umlGui.UMLSeqDiaGui;
import app.backend.Diagrams;
import app.uml.UMLClass;
import app.uml.UMLMethod;
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
        // createClassDiagScene(rootClass, primaryStage);

        // set the scene for sequence diagram
        // todo button (add Message) (should choose from some types)
        // todo maybe like variable for just active scene ....
        for (int i = 0; i < rootSeq.size(); i++){
            this.createSeqDiagScene(i, rootSeq);
        }

        // show the help
        this.helpMessage();
    }

    private void createClassDiagScene(Group rootClass, Stage primaryStages){
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

        // addClass button
        /*
        Button addClass = createButton("Add Class", 1);
        addClass.setOnAction(e -> rootClass.getChildren().add(new UMLClassGui(BEdiagrams.getClassDiagram().createClass("Your Class"), (UMLClassDiagramGui) rootClass.getChildren().get(0))));
        addClass.setLayoutY(50);
        addClass.setLayoutX(500);
        rootClass.getChildren().add(addClass);
         */

        // set the stage
        stage.setTitle("ija-app: diagrams");
        stage.setScene(scene);
        stage.show();
    }

    private void createSeqDiagScene(int n, List<Group> rootSeq){
        // TODO SMAZAT A VYTVARET VE FE ... V PRIPADE ZMACKNUTI SAVE TLACITKA ULOZIT DO BE
        // uz je vse nactene z JSONu v jednotlivych UMLSeqDiaGui -> staci jen zobrazit

        UMLSeqDiaGui seqDiaGui = (UMLSeqDiaGui) rootSeq.get(n).getChildren().get(0);
        Stage primaryStage = new Stage();
        Scene sceneSeqTest = new Scene(rootSeq.get(n), 1000, 750, Color.WHITE);
        String newActorName;

        // Creating menu
        Menu options = new Menu("Add...");
        options.setStyle("-fx-font-size: 15;");

        // add actor on action
        MenuItem addActor = new MenuItem("Add actor");
        addActor.setOnAction( e -> {
            ComboBox<String> comboBox1 = new ComboBox<>();
            TextField actorName        = new TextField();
            Button confirm             = new Button("Confirm");

            // fill up combobox
            for (UMLClass cls : BEdiagrams.getClassDiagram().getClasses()){
                comboBox1.getItems().add(cls.getName());
            }

            confirm.setOnAction( ev -> {
                System.out.println(actorName.getText());
                System.out.println(comboBox1.getValue());
                rootSeq.get(n).getChildren().remove(comboBox1);
                rootSeq.get(n).getChildren().remove(actorName);
                rootSeq.get(n).getChildren().remove(confirm);

                if(actorName.getText() != ""){
                    if(comboBox1.getValue() != null){
                        seqDiaGui.paintNewActor(actorName.getText(),
                                BEdiagrams.getClassDiagram().findClass(comboBox1.getValue()));
                    }
                }
            });

            comboBox1.setLayoutX(10);
            comboBox1.setLayoutY(30);
            actorName.setLayoutX(10);
            actorName.setLayoutY(55);
            confirm.setLayoutX(10);
            confirm.setLayoutY(80);
            comboBox1.setPromptText("class name");
            // combo box on action

            rootSeq.get(n).getChildren().add(comboBox1);
            rootSeq.get(n).getChildren().add(actorName);
            rootSeq.get(n).getChildren().add(confirm);
        });

        // add message on action
        MenuItem addMessage = new MenuItem("Add message");
        addMessage.setOnAction( e -> {
            ComboBox<String> methodCB        = new ComboBox<>();
            ComboBox<String> typeCB      = new ComboBox<>();
            ComboBox<String> CBactorFrom = new ComboBox<>();
            ComboBox<String> CBactorTo   = new ComboBox<>();
            Button confirm2              = new Button("Confirm");

            // fill up combobox
            for (UMLClass cls : BEdiagrams.getClassDiagram().getClasses()){
                for (UMLMethod met : cls.getMethods()){
                    methodCB.getItems().add(met.getName());
                }
            }

            // Fill up types
            typeCB.getItems().add("synch");
            typeCB.getItems().add("asynch");
            typeCB.getItems().add("response");
            typeCB.getItems().add("creat");
            typeCB.getItems().add("free");

            // fill actors comboboxes
            for (UMLActorGui act : seqDiaGui.getActorsGui()){
                CBactorFrom.getItems().add(act.getDisplayedName());
                CBactorTo.getItems().add(act.getDisplayedName());
            }

            confirm2.setOnAction( ev -> {

                System.out.println(methodCB.getValue());
                System.out.println(CBactorFrom.getValue());
                System.out.println(CBactorTo.getValue());

                /*
                public UMLMessage(UMLClass fromClass, UMLClass toClass,
                        String fromActor, String toActor,
                        String type, UMLMethod method){
                 */

                if (CBactorFrom.getValue() != null){
                   if (CBactorTo.getValue() != null){

                        UMLActorGui actorFromGui = seqDiaGui.findActorGuiByWholeName(String.valueOf(CBactorFrom.getValue()));
                        UMLActorGui actorToGui   = seqDiaGui.findActorGuiByWholeName(String.valueOf(CBactorTo.getValue()));
                        UMLClass classFrom       = BEdiagrams.getClassDiagram().findClass(actorFromGui.getClassName());
                        UMLClass classTo       = BEdiagrams.getClassDiagram().findClass(actorToGui.getClassName());
                        String fromActor         = actorFromGui.getActorName();
                        String to                = actorToGui.getActorName();
                        String type              = String.valueOf(typeCB.getValue());
                        UMLMethod method         = classFrom.findMethod(methodCB.getValue());

                        /*
                        System.out.println("-------type debug" + typeCB.getValue());
                        System.out.println("..act F G " + actorFromGui);
                        System.out.println("..act T G " + actorToGui);
                        System.out.println("..CF " + actorFromGui.getClassName());
                        System.out.println("..CT " + actorToGui.getClassName());
                        System.out.println("..Class F " + classFrom);
                        System.out.println("..Class T " + classTo);
                        System.out.println("..From A " + fromActor);
                        System.out.println(".. To   " + to);
                        System.out.println(".. Type " + type);
                        System.out.println(".. Method " + method);
                        */

                        if(actorFromGui != actorToGui){
                            UMLMessage mess = new UMLMessage(classFrom, classTo, fromActor, to, type, method);
                            seqDiaGui.paintMessage(mess);
                            //UMLMessage
                        }
                    }
                }

                rootSeq.get(n).getChildren().remove(methodCB);
                rootSeq.get(n).getChildren().remove(typeCB);
                rootSeq.get(n).getChildren().remove(confirm2);
                rootSeq.get(n).getChildren().remove(CBactorTo);
                rootSeq.get(n).getChildren().remove(CBactorFrom);

            });


            typeCB.setPromptText("Message type");
            typeCB.setLayoutX(10);
            typeCB.setLayoutY(30);

            methodCB.setPromptText("Method");
            methodCB.setLayoutX(10);
            methodCB.setLayoutY(60);

            CBactorFrom.setPromptText("Actor From");
            CBactorFrom.setLayoutX(10);
            CBactorFrom.setLayoutY(90);

            CBactorTo.setPromptText("Actor To");
            CBactorTo.setLayoutX(10);
            CBactorTo.setLayoutY(120);

            confirm2.setLayoutX(10);
            confirm2.setLayoutY(150);


            // combo box on action

            rootSeq.get(n).getChildren().add(typeCB);
            rootSeq.get(n).getChildren().add(methodCB);
            rootSeq.get(n).getChildren().add(CBactorFrom);
            rootSeq.get(n).getChildren().add(CBactorTo);
            rootSeq.get(n).getChildren().add(confirm2);
        });
        options.getItems().addAll(addActor, addMessage);

        // Creating menu
        Menu options2 = new Menu("Remove...");
        options2.setStyle("-fx-font-size: 15;");

        MenuItem removeActor = new MenuItem("Remove actor");
        removeActor.setOnAction( e -> {
            ComboBox<String> comboBox3 = new ComboBox<>();
            Button confirm             = new Button("Confirm");

            // fill up combobox
            /*
            for ()
            for (UMLClass cls : BEdiagrams.getClassDiagram().getClasses()){
                comboBox1.getItems().add(cls.getName());
            }

            confirm.setOnAction( ev -> {
                System.out.println(actorName.getText());
                System.out.println(comboBox1.getValue());
                rootSeq.get(n).getChildren().remove(comboBox1);
                rootSeq.get(n).getChildren().remove(actorName);
                rootSeq.get(n).getChildren().remove(confirm);

                // store to BE -> Paint it

            });

            comboBox1.setLayoutX(10);
            comboBox1.setLayoutY(30);
            actorName.setLayoutX(10);
            actorName.setLayoutY(55);
            confirm.setLayoutX(10);
            confirm.setLayoutY(80);
            comboBox1.setPromptText("class name");
            // combo box on action

            rootSeq.get(n).getChildren().add(comboBox1);
            rootSeq.get(n).getChildren().add(actorName);
            rootSeq.get(n).getChildren().add(confirm);
             */
        });


        // todo ONaction
        MenuItem removeMessage = new MenuItem("Remove message");
        // todo ONaction
        options2.getItems().addAll(removeActor, removeMessage);


        // add save button (fix releasing the button)
        Button saveButton = this.createButton("Save JSON", 0);
        //saveButton.setOnAction(e -> DiagramSaver.saveJSON(e, rootSeq.get(n)));
        Menu save = this.createMenu(saveButton);

        // add undo button - TODO, fix releasing the button
        Button undoButton = this.createButton("Undo", 0);
        Menu undo = this.createMenu(undoButton);

        // add MenuBar
        MenuBar menuBar = new MenuBar(options, options2 ,save, undo);
        //menuBar.useSystemMenuBarProperty();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        rootSeq.get(n).getChildren().add(menuBar);

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
