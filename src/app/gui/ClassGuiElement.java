/*
 * File:         ClassGuiElement.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the UML class diagram displayed in GUI.
 */
package app.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the UML class displayed in the GUI.
 */
public class ClassGuiElement extends VBox {
    // name
    private Label class_name;
    // list of attributes
    private List<Label> attributes;
    // list of methods
    private List<Label> methods;

    DraggableObject draggableObject = new DraggableObject();

    /**
     * ClassDiagram constructor.
     *
     * @param name name of the class diagram
     */
    public ClassGuiElement(String name) {
        attributes = new ArrayList<>();
        methods = new ArrayList<>();

        draggableObject.makeDraggable(this);
        String cssLayout = "-fx-border-color: black;\n" +
                           "-fx-border-width: 1;\n";
        this.setStyle(cssLayout);

        class_name = new Label(name);
        this.getChildren().add(class_name);
        this.getChildren().add(new Separator());

        // addAttr button
        Button addAttr = new Button("Add Attribute");
        addAttr.setLayoutY(100);
        addAttr.setLayoutX(100);
        this.getChildren().add(addAttr);
        addAttr.setOnAction(e -> addAttribute());

        // addMeth button
        Button addMeth = new Button("Add Method");
        addMeth.setLayoutY(100);
        addMeth.setLayoutX(100);
        this.getChildren().add(addMeth);
        addMeth.setOnAction(e -> addMethod());
    }

    /**
     * Method adds an attribute to the attributes array.
     */
    public void addAttribute() {
        attributes.add(new Label("defattr" + (attributes.size())));
        this.getChildren().add(attributes.get(attributes.size() - 1));
    }

    /**
     * Method adds a method to the methods array.
     */
    public void addMethod() {
        methods.add(new Label("defmethod" + (methods.size())));
        this.getChildren().add(methods.get(methods.size() - 1));
    }
}
