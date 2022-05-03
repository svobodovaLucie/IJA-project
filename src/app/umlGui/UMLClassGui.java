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

import app.gui.DraggableObject;
import app.uml.UMLClass;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class represents a UML class. It is inherited from UMLClassifier.
 * It contains a list of its UML attributes and a list of its UML
 * methods. The UML class may be abstract.
 */
public class UMLClassGui extends VBox {

    // class that is represented in GUI
    private UMLClass umlClass;

    // name
    TextField nameLabel;

    // grid pane
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


    /**
     * UMLClass constructor. The UML class is not abstract.
     *
     * @param umlClass BE UMLClass to be represented
     */
    public UMLClassGui(UMLClass umlClass) {
        // add BE class
        this.umlClass = umlClass;

        // set margin
        HBox.setMargin(this, new Insets(15, 15, 15, 15));

        // make the UMLClassGui object dragable
        draggableObject.makeDraggable(this);

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
        this.attributesGridPane.setPadding(insets);
        this.attributesGridPane.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-width: 1 2 1 2;\n" +
                "-fx-border-color: black;");
        this.getChildren().add(this.attributesGridPane);

        // create list of methods
        this.nodeMethods = new ArrayList<>();
        this.methodButtons = new ArrayList<>();
        // create GridPane for methods
        this.methodsGridPane = new GridPane();
        this.methodsGridPane.setPadding(insets);
        this.methodsGridPane.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-width: 1 2 2 2;\n" +
                "-fx-border-color: black;");
        this.getChildren().add(this.methodsGridPane);

        // event listener
        this.nameLabel.textProperty().addListener(((observableValue, s, t1) ->
                    this.umlClass.setName(t1)
        ));

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
        if (this.nodeAttributes.contains(attr))
            // can't have two attributes with the same name
            return true;
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
}

