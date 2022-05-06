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
import app.uml.UMLRelation;
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
        createClassDiagScene(rootClass);

        // set the scene for sequence diagram
        // todo button (add Message) (should choose from some types)
        // todo maybe like variable for just active scene ....
        for (int i = 0; i < rootSeq.size(); i++){
            this.createSeqDiagScene(i, rootSeq);
        }

        // show the help
        this.helpMessage();
    }

    private void createClassDiagScene(Group rootClass){
        // set the scene
        Stage stage = new Stage();
        Scene scene = new Scene(rootClass, stage.getMaxWidth(), stage.getMaxHeight(), Color.WHITE);

        // options for adding new elements
        Menu addOptions = addMenuAdd(rootClass);
        // options for removing elements
        Menu removeOptions = addMenuRemove(rootClass);

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
        MenuBar menuBar = new MenuBar(addOptions, removeOptions, save, undo);
        menuBar.useSystemMenuBarProperty();
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        rootClass.getChildren().add(menuBar);

        // set the stage
        stage.setTitle("ija-app: diagrams");
        stage.setScene(scene);
        stage.show();
    }

    private Menu addMenuAdd(Group rootClass) {
        Menu addOptions = new Menu("Add...");
        addOptions.setStyle("-fx-font-size: 15;");
        // button for adding new class
        MenuItem addClass = new MenuItem("Add class");
        addClass.setOnAction(e -> {
            try {
                UMLClassDiagramGui umlClassDiagramGui = (UMLClassDiagramGui) rootClass.getChildren().get(0);
                UMLClass newClass = BEdiagrams.getClassDiagram().createClass("Untitled");
                umlClassDiagramGui.getChildren().add(new UMLClassGui(newClass, umlClassDiagramGui));
            } catch (Exception exception) {
                System.out.println("Can't create two classes/interfaces with the same name!");
                customAlertBox();
            }
        });
        // button for adding new interface
        MenuItem addInterface = new MenuItem("Add interface");
        addInterface.setOnAction(e -> {
            try {
                UMLClassDiagramGui umlClassDiagramGui = (UMLClassDiagramGui) rootClass.getChildren().get(0);
                UMLClass newInterface = BEdiagrams.getClassDiagram().createInterface("Untitled Interface");
                umlClassDiagramGui.getChildren().add(new UMLClassGui(newInterface, umlClassDiagramGui));
            } catch (Exception exception) {
                System.out.println("Can't create two interfaces/classes with the same name!");
                customAlertBox();
            }
        });
        // button for adding new relation
        MenuItem addRelation = new MenuItem("Add relation");
        addRelation.setOnAction(e -> {
            System.out.println("Adding new relation (not implemented yet)");
            editRelationMessage(rootClass, true);      // true == add relationship
        });

        addOptions.getItems().addAll(addClass, addInterface, addRelation);

        return addOptions;
    }

    private void customAlertBox() {
        // TODO alert box
        Group helpGroup = new Group();
        Text text = new Text();
        text.setFont(new Font(15));
        helpGroup.setStyle("-fx-label-padding: 100 100 100 100");
        text.setWrappingWidth(350);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText("\nThere is already a class/interface with the name Untitled.\n" +
                     "\"Two classes/interfaces with the same name can't be created.");
        helpGroup.getChildren().add(text);
        Scene helpScene = new Scene(helpGroup, 400, 400);
        Stage helpStage = new Stage();
        helpStage.setScene(helpScene);
        helpStage.setTitle("Warning");
        helpStage.show();
    }

    private Menu addMenuRemove(Group rootClass) {
        Menu removeOptions = new Menu("Remove...");
        removeOptions.setStyle("-fx-font-size: 15;");
        // button for adding new class
        MenuItem removeClass = new MenuItem("Remove class");
        removeClass.setOnAction(e -> {
            removeClassMessage();
        });
        // button for adding new interface
        MenuItem removeInterface = new MenuItem("Remove interface");
        removeInterface.setOnAction(e -> {
            System.out.println("Removing new interface (not implemented yet)");
            removeInterfaceMessage();
        });
        // button for adding new relation
        MenuItem removeRelation = new MenuItem("Remove relation");
        removeRelation.setOnAction(e -> {
            System.out.println("Removing new relation (not implemented yet)");
            editRelationMessage(rootClass, false);     // false == remove relationship
        });

        removeOptions.getItems().addAll(removeClass, removeInterface, removeRelation);

        return removeOptions;
    }

    public void editRelationMessage(Group rootClass, boolean add_remove) {   // add - true, remove - false
        Group helpGroup = new Group();
        Text text = new Text();
        text.setFont(new Font(15));
        helpGroup.setStyle("-fx-label-padding: 100 100 100 100");
        text.setWrappingWidth(350);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText("\n\n         Select relationship to be edited.");
        helpGroup.getChildren().add(text);

        // classFrom
        ComboBox<String> classFromCB = new ComboBox<>();
        // classTo
        ComboBox<String> classToCB = new ComboBox<>();
        // get names of all classes
        for (UMLClass cls : BEdiagrams.getClassDiagram().getClassesInterfaces()) {
            classFromCB.getItems().add(cls.getName());
            classToCB.getItems().add(cls.getName());
        }
        classFromCB.setPromptText("Select From");
        classFromCB.setLayoutX(42);
        classFromCB.setLayoutY(42);

        // relationship type
        ComboBox<String> typeCB = new ComboBox<>();
        // get names of all types (better arrows)
        typeCB.getItems().addAll("association", "inheritance", "aggregation", "composition");
        typeCB.setPromptText("Select Type");
        typeCB.setLayoutX(42);
        typeCB.setLayoutY(82);

        // classTo continue
        classToCB.setPromptText("Select To");
        classToCB.setLayoutX(42);
        classToCB.setLayoutY(122);

        Scene helpScene = new Scene(helpGroup, 400, 400);
        Stage helpStage = new Stage();
        helpStage.setScene(helpScene);
        helpStage.setTitle("Edit relationship");

        // confirming button
        Button confirm = new Button("Confirm");
        confirm.setLayoutX(100);
        confirm.setLayoutY(162);
        if (add_remove) {   // add relationship
            confirm.setOnAction(event -> {
                System.out.println("confirming add");
                UMLClassDiagramGui umlClassDiagramGui = (UMLClassDiagramGui) rootClass.getChildren().get(0);
                UMLRelation newRelation = BEdiagrams.getClassDiagram().createRelation(BEdiagrams.getClassDiagram(), classFromCB.getValue(), classToCB.getValue(), typeCB.getValue());
                UMLRelationGui newRelationGui = new UMLRelationGui(newRelation, umlClassDiagramGui);
                umlClassDiagramGui.getChildren().add(newRelationGui.getRelationArrow());
                helpStage.close();
            });
        } else {
            confirm.setOnAction(event -> {
                System.out.println("confirming remove");
                //UMLClassDiagramGui umlClassDiagramGui = (UMLClassDiagramGui) rootClass.getChildren().get(0);
               // UMLRelation newRelation = BEdiagrams.getClassDiagram().createRelation(BEdiagrams.getClassDiagram(), classFromCB.getValue(), classToCB.getValue(), typeCB.getValue());
                //umlClassDiagramGui.getChildren().add(new UMLRelationGui(newRelation, umlClassDiagramGui));
                //BEdiagrams.getClassDiagram().removeClass(cb.getValue());
                //System.out.println("removed");
                //helpStage.close();
                BEdiagrams.getClassDiagram().removeRelation(classFromCB.getValue(), classToCB.getValue(), typeCB.getValue());
                System.out.println("removed");
                helpStage.close();
            });
        }
        helpGroup.getChildren().addAll(classFromCB, typeCB, classToCB, confirm);

        helpStage.show();
    }

    private void removeClassMessage(){
        Group helpGroup = new Group();
        Text text = new Text();
        text.setFont(new Font(15));
        helpGroup.setStyle("-fx-label-padding: 100 100 100 100");
        text.setWrappingWidth(350);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText("\n\n         Select class to be removed.");
        helpGroup.getChildren().add(text);

        ComboBox<String> cb = new ComboBox<>();
        // get names of all classes
        for (UMLClass cls : BEdiagrams.getClassDiagram().getClasses()) {
            cb.getItems().add(cls.getName());
        }
        cb.setPromptText("Select class");
        cb.setLayoutX(42);
        cb.setLayoutY(42);
        helpGroup.getChildren().add(cb);

        Stage helpStage = new Stage();

        // confirming button
        Button confirm = new Button("Confirm");
        confirm.setLayoutX(70);
        confirm.setLayoutY(70);
        confirm.setOnAction(event -> {
            System.out.println("confirming");
            BEdiagrams.getClassDiagram().removeClass(cb.getValue());
            System.out.println("removed");
            helpStage.close();
        });
        helpGroup.getChildren().add(confirm);

        Scene helpScene = new Scene(helpGroup, 400, 400);
        helpStage.setScene(helpScene);
        helpStage.setTitle("Edit relationship");
        helpStage.show();
    }

    private void removeInterfaceMessage(){
        Group helpGroup = new Group();
        Text text = new Text();
        text.setFont(new Font(15));
        helpGroup.setStyle("-fx-label-padding: 100 100 100 100");
        text.setWrappingWidth(350);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText("\n\n         Select interface to be removed.");
        helpGroup.getChildren().add(text);

        ComboBox<String> cb = new ComboBox<>();
        // get names of all classes
        for (UMLClass intfc : BEdiagrams.getClassDiagram().getInterfaces()) {
            cb.getItems().add(intfc.getName());
        }
        cb.setPromptText("Select interface");
        cb.setLayoutX(42);
        cb.setLayoutY(42);
        helpGroup.getChildren().add(cb);

        Scene helpScene = new Scene(helpGroup, 400, 400);
        Stage helpStage = new Stage();
        helpStage.setScene(helpScene);
        helpStage.setTitle("Remove interface");

        // confirming button
        Button confirm = new Button("Confirm");
        confirm.setLayoutX(70);
        confirm.setLayoutY(70);
        confirm.setOnAction(event -> {
            BEdiagrams.getClassDiagram().removeInterface(cb.getValue());
            System.out.println("removed");
            helpStage.close();
        });
        helpGroup.getChildren().add(confirm);

        helpStage.show();
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
            ComboBox<String> methodCB    = new ComboBox<>();
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
            ComboBox<String> actorsCB = new ComboBox<>();
            Button confirm             = new Button("Confirm");

            // fill up combobox
            for (UMLActorGui agui : seqDiaGui.getActorsGui()){
                actorsCB.getItems().add(agui.getDisplayedName());
            }

            confirm.setOnAction( ev -> {
                System.out.println(confirm.getText());
                System.out.println(actorsCB.getValue());
                UMLActorGui umlActorGui = seqDiaGui.findActorGuiByWholeName(actorsCB.getValue());

                seqDiaGui.removeActor(umlActorGui);

                rootSeq.get(n).getChildren().remove(actorsCB);
                rootSeq.get(n).getChildren().remove(confirm);
                seqDiaGui.paintEVERYTHINGAGAIN();


            });
            actorsCB.setLayoutX(10);
            actorsCB.setLayoutY(30);
            confirm.setLayoutX(10);
            confirm.setLayoutY(60);
            actorsCB.setPromptText("Actor name");
            // combo box on action

            rootSeq.get(n).getChildren().add(actorsCB);
            rootSeq.get(n).getChildren().add(confirm);

        });


        // todo ONaction
        MenuItem removeMessage = new MenuItem("Remove message");
        removeMessage.setOnAction( e -> {
            ComboBox<String> messagesCB = new ComboBox<>();
            Button confirm3              = new Button("Confirm");

            // fill up combobox
            int indexI = 1;
            for (UMLMessageGui agui : seqDiaGui.getMessageGui()){
                System.out.println("~~~~~ " + agui.getMessage());
                messagesCB.getItems().add(indexI++ + " " + agui.getMessage().getType());
            }


            confirm3.setOnAction( ev -> {
                if(messagesCB.getValue() == null){
                    rootSeq.get(n).getChildren().remove(messagesCB);
                    rootSeq.get(n).getChildren().remove(confirm3);

                }
                else {
                    String[] str = messagesCB.getValue().split("\\s+");

                    seqDiaGui.removeMessage(str[1], Integer.parseInt(str[0]));
                /*
                seqDiaGui.removeActor(umlActorGui);

                seqDiaGui.paintEVERYTHINGAGAIN();

                */
                    rootSeq.get(n).getChildren().remove(messagesCB);
                    rootSeq.get(n).getChildren().remove(confirm3);

                }
            });
            messagesCB.setLayoutX(10);
            messagesCB.setLayoutY(30);
            confirm3.setLayoutX(10);
            confirm3.setLayoutY(60);
            messagesCB.setPromptText("Method");
            // combo box on action

            rootSeq.get(n).getChildren().add(messagesCB);
            rootSeq.get(n).getChildren().add(confirm3);

        });
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
