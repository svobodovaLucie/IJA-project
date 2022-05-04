/*
 * File:         UMLClassGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLClass class that represents
 * the UML class.
 */
package app.umlGui;

import app.backend.CommandBuilder;
import app.gui.DraggableObject;
import app.uml.UMLAttribute;
import app.uml.UMLClass;
import app.uml.UMLClassifier;
import app.uml.UMLMethod;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class represents a UML class. It is inherited from UMLClassifier.
 * It contains a list of its UML attributes and a list of its UML
 * methods. The UML class may be abstract.
 */
public class UMLClassGui extends VBox {

    // owner
    private final UMLClassDiagramGui owner;

    // class that is represented in GUI
    private UMLClass umlClass;

    // name
    TextField nameLabel;

    // grid panes for attributes and methods
    GridPane attributesGridPane;
    GridPane methodsGridPane;

    // draggable property
    DraggableObject draggableObject = new DraggableObject();

    // list of attributes
    private List<UMLAttributeGui> nodeAttributes;
    private List<Button> attributeButtons;

    // list of methods
    private List<UMLMethodGui> nodeMethods;
    private List<Button> methodButtons;

    private PropertyChangeSupport support;

    /**
     * UMLClass constructor. The UML class is not abstract.
     *
     * @param umlClass BE UMLClass to be represented
     */
    public UMLClassGui(UMLClass umlClass, UMLClassDiagramGui umlClassDiagramGui) {
        // add BE class
        this.umlClass = umlClass;

        // observable
        support = new PropertyChangeSupport(this);

        // set margin
        HBox.setMargin(this, new Insets(15, 15, 15, 15));

        // make the UMLClassGui object dragable
        draggableObject.makeDraggable(this);

        // add owner
        this.owner = umlClassDiagramGui;

        // set transparent border for easier dragging
        BorderStroke borderStroke = new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, null,
                new BorderWidths(5));
        this.setBorder(new Border(borderStroke));
        Insets insets = new Insets(5, 5, 5, 5);

        // add name label
        this.nameLabel = new TextField(this.umlClass.getName());
        this.nameLabel.setPadding(new Insets(5, 5, 5, 5));
        this.nameLabel.setStyle("-fx-font-weight: bold;\n" +
		              "-fx-background-color: transparent;\n" +
					  "-fx-border-style: solid;\n" +
					  "-fx-background-radius: 0;\n" +
                      "-fx-border-width: 2 2 1 2;\n" +
					  "-fx-border-color: black;");
        this.nameLabel.setAlignment(Pos.CENTER);
        this.getChildren().add(nameLabel);

        // create lists of attributes
        this.nodeAttributes = new ArrayList<>();
        this.attributeButtons = new ArrayList<>();
        // create GridPane for attributes
        this.attributesGridPane = new GridPane();
        this.attributesGridPane.setPadding(new Insets(5, 5, 0, 5));
        //this.attributesGridPane.setPadding(insets);
        this.attributesGridPane.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-width: 1 2 0 2;\n" +
                "-fx-border-color: black;");
        this.getChildren().add(this.attributesGridPane);

        // button for adding new attributes
        GridPane attributeAddGridPane = new GridPane();
        attributeAddGridPane.setPadding(new Insets(0, 0, 5, 0));
        attributeAddGridPane.setHgap(173.71);
        attributeAddGridPane.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-width: 0 2 1 2;\n" +
                "-fx-border-color: black;");
        this.getChildren().add(attributeAddGridPane);
        Text addAttributeLabel = new Text();
        GridPane.setConstraints(addAttributeLabel, 0, 0);
        attributeAddGridPane.getChildren().add(addAttributeLabel);
        // create a button for adding new attributes
        Button addAttributeButton = new Button("+");
        addAttributeButton.setOnAction(e -> {
            System.out.println("Adding new attribute");
            // add attribute to BE
            UMLClassifier umlClassifier = new UMLClassifier("");
            UMLAttribute umlAttribute = new UMLAttribute("", umlClassifier, "private");
            // add attribute to backend
            this.umlClass.addAttribute(umlAttribute);
            // add that attribute to GUI
            UMLAttributeGui umlAttributeGui = new UMLAttributeGui(umlAttribute);
            this.addAttributeGui(umlAttributeGui);
        });
        addAttributeButton.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-color: transparent;\n" +
                "-fx-font-weight: bold;");
        GridPane.setConstraints(addAttributeButton, 1, 0);
        attributeAddGridPane.getChildren().add(addAttributeButton);

        // create list of methods
        this.nodeMethods = new ArrayList<>();
        this.methodButtons = new ArrayList<>();
        // create GridPane for methods
        this.methodsGridPane = new GridPane();
        this.methodsGridPane.setPadding(new Insets(5, 5, 0, 5));
        this.methodsGridPane.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-width: 1 2 0 2;\n" +
                "-fx-border-color: black;");
        this.getChildren().add(this.methodsGridPane);

        // button for adding new methods
        GridPane methodAddGridPane = new GridPane();
        methodAddGridPane.setPadding(new Insets(0, 0, 5, 0));
        methodAddGridPane.setHgap(173.71);
        methodAddGridPane.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-width: 0 2 2 2;\n" +
                "-fx-border-color: black;");
        this.getChildren().add(methodAddGridPane);
        Text addMethodLabel = new Text();
        GridPane.setConstraints(addMethodLabel, 0, 0);
        methodAddGridPane.getChildren().add(addMethodLabel);
        // create a button for adding new methods
        Button addMethodButton = new Button("+");
        addMethodButton.setOnAction(e -> {
            System.out.println("Adding new method");
            // add method to backend
            UMLClassifier umlClassifier = new UMLClassifier("");
            UMLMethod umlMethod = new UMLMethod("", umlClassifier, "");
            this.umlClass.addMethod(umlMethod);
            // add to GUI
            UMLMethodGui umlMethodGui = new UMLMethodGui(umlMethod);
            this.addMethodGui(umlMethodGui);
        });
        addMethodButton.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-color: transparent;\n" +
                "-fx-font-weight: bold;");
        GridPane.setConstraints(addMethodButton, 1, 0);
        methodAddGridPane.getChildren().add(addMethodButton);

        // event listener
        this.nameLabel.textProperty().addListener(((observableValue, s, t1) ->
                    this.umlClass.setName(t1)
        ));

        this.setOnDragDetected(ev -> {
            System.out.println("onDragDetected");
            owner.executeCommand(new CommandBuilder.Command() {
                //List <Double> oldPosition;
                @Override
                public void execute() {
                    System.out.println("execute()");
                    //oldPosition = draggableObject.getPosition();
                    support.firePropertyChange(umlClass.getName(), 0, 1);
                }
                @Override
                public void undo() {
                    System.out.println("undo()");
                    draggableObject.setOldPosition();
                    support.firePropertyChange(umlClass.getName(), 1, 0);
                }
            });
        });

    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    /**
     * Method inserts a UML argument to the UML class. The argument is
     * inserted at the end of the arguments list. If there is an argument
     * with the same name as the one to be added, new argument is not added.
     *
     * @param attr UML argument to be inserted to the list
     *
     * @return true if the argument was successfully added, false if not
     */
    public boolean addAttributeGui(UMLAttributeGui attr) {
        // add attr to attribute grid pane
        if (this.nodeAttributes.contains(attr)) {
            // can't have two attributes with the same name
            return true;
        }
        try {
            int lastRowNumber = nodeAttributes.size();
            this.nodeAttributes.add(attr);

            // add the attribute to the grid pane
            GridPane.setConstraints(this.nodeAttributes.get(lastRowNumber), 0, lastRowNumber);
            this.attributesGridPane.getChildren().add(this.nodeAttributes.get(lastRowNumber));

            // add a button that can remove the attribute
            this.attributeButtons.add(new Button("-"));
            this.attributeButtons.get(lastRowNumber).setStyle("-fx-background-color: transparent;\n" +
                    "-fx-border-color: transparent;\n" +
                    "-fx-font-weight: bold;");
            this.attributeButtons.get(lastRowNumber).setOnAction(actionEvent ->  {
                // row number 0 may be null
                if (lastRowNumber == 0) {
                    this.attributesGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) == 0);
                } else {
                    this.attributesGridPane.getChildren().removeIf(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                }
            });
            GridPane.setConstraints(this.attributeButtons.get(lastRowNumber), 1, lastRowNumber);
            this.attributesGridPane.getChildren().add(this.attributeButtons.get(lastRowNumber));

        } catch (UnsupportedOperationException uoe) {
            return false;
        }
        return true;
    }

    /**
     * Method insert a UML method to the UML class. The amethod is inserted
     * at the end of the methods list. If there is already a method with the
     * same name as the one to be added, the method is not added.
     *
     * @param meth UML method to be inserted to the list
     *
     * @return true if the method was successfully added, false if not
     */
    public boolean addMethodGui(UMLMethodGui meth) {
        if (this.nodeMethods.contains(meth))
            return true;
        try {
            int lastRowNumber = nodeMethods.size();
            this.nodeMethods.add(meth);

            // add the method to the grid pane
            GridPane.setConstraints(this.nodeMethods.get(lastRowNumber), 0, lastRowNumber);
            this.methodsGridPane.getChildren().add(this.nodeMethods.get(lastRowNumber));

            // add a button that can remove the method
            this.methodButtons.add(new Button("-"));
            this.methodButtons.get(lastRowNumber).setStyle("-fx-background-color: transparent;\n" +
                    "-fx-border-color: transparent;\n" +
                    "-fx-font-weight: bold;");
            this.methodButtons.get(lastRowNumber).setOnAction(actionEvent ->  {
                // row number 0 may be null
                if (lastRowNumber == 0) {
                    this.methodsGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) == 0);
                } else {
                    this.methodsGridPane.getChildren().removeIf(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                }
            });
            GridPane.setConstraints(this.methodButtons.get(lastRowNumber), 1, lastRowNumber);
            this.methodsGridPane.getChildren().add(this.methodButtons.get(lastRowNumber));

        } catch (UnsupportedOperationException uoe) {
            return false;
        }
        return true;
    }

    public List<UMLAttributeGui> getAttributes() {
        return this.nodeAttributes;
    }

    public List<UMLMethodGui> getMethods() {
        return this.nodeMethods;
    }

    public String getName() {
        return this.nameLabel.getText();
    }

    public UMLClass getUmlClass() {
        return this.umlClass;
    }
}

