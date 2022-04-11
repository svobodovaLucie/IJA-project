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
import app.uml.UMLAttribute;
import app.uml.UMLMethod;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a UML class. It is inherited from UMLClassifier.
 * It contains a list of its UML attributes and a list of its UML
 * methods. The UML class may be abstract.
 */
public class UMLClassGui extends VBox {

    // name
    @FXML
    Label nameLabel;

    // list of attributes
    private List<Label> nodeAttributes;

    // list of methods
    private List<Label> nodeMethods;

    // draggable property
    DraggableObject draggableObject = new DraggableObject();

    /**
     * UMLClass constructor. The UML class is not abstract.
     *
     * @param name name of the UML class
     */
    public UMLClassGui(String name) {
        // make the UMLClassGui object dragable
        draggableObject.makeDraggable(this);
        // set the style
        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-width: 1;\n";
        this.setStyle(cssLayout);

        // add name label
        this.nameLabel = new Label(name);
        this.nameLabel.setStyle("-fx-font-weight: bold");
        this.nameLabel.setMaxWidth(Double.MAX_VALUE);
        this.getChildren().add(nameLabel);

        // add a separator
        this.getChildren().add(new Separator());

        // create lists of attributes and methods
        this.nodeAttributes = new ArrayList<>();
        this.nodeMethods = new ArrayList<>();
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
        if (this.nodeAttributes.contains(attr))
            return true;
        try {
            this.nodeAttributes.add(attr);
            this.getChildren().add(this.nodeAttributes.get(nodeAttributes.size() - 1));
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
            this.nodeMethods.add(meth);
            this.getChildren().add(this.nodeMethods.get(nodeMethods.size() - 1));
        } catch (UnsupportedOperationException uoe) {
            return false;
        }
        return true;
    }
}

