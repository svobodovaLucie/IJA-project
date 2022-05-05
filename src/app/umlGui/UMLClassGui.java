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
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.event.MouseEvent;
import java.beans.Customizer;
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

    // position
    private double posX;
    private double posY;
    public SimpleDoubleProperty x = new SimpleDoubleProperty(150);
    public SimpleDoubleProperty y = new SimpleDoubleProperty(150);

    // draggable property
    public DraggableObject draggableObject = new DraggableObject();

    // list of attributes
    private List<UMLAttributeGui> nodeAttributes;
    private List<Button> attributeButtons;

    // list of methods
    private List<UMLMethodGui> nodeMethods;
    private List<Button> methodButtons;

    private PropertyChangeSupport support;


    /*
    public void setDraggable() {

        System.out.println("Setting draggable");

        this.draggableObject.setOnMousePressed(e -> {
            System.out.println("Mouse pressed");
            this.draggableObject.setCursor(Cursor.MOVE);
            posX = e.getX();
            posY = e.getY();
        });

     */
        /*
        this.draggableObject.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            double distanceX = e.getX()
        });
         */
        /*
        this.draggableObject.setOnMouseDragged(e -> {
            System.out.println("Mouse dragged");
            double distX = e.getX() - posX;
            double distY = e.getY() - posY;

            x.setValue(this.x.getValue() + distX);
            y.setValue(this.y.getValue() + distY);

            this.draggableObject.relocate(x.doubleValue(), y.doubleValue());
        });

         */

        //this.draggableObject.setOnMouseEntered(e -> this.draggableObject.setCursor(Customiz));
        /*
        this.draggableObject.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            this.draggableObject.setCursor(Cursor.MOVE);
            posX = event.getX();
        });
         */
    //}

    /**
     * UMLClass constructor. The UML class is not abstract.
     *
     * @param umlClass BE UMLClass to be represented
     */
    public UMLClassGui(UMLClass umlClass, UMLClassDiagramGui umlClassDiagramGui) {
        // add BE class
        this.umlClass = umlClass;

        // set initial positions TODO load from JSON
        this.posX = 100;
        this.posY = 100;

        // make the UMLClassGui object dragable
        draggableObject.makeDraggable(this);
        //setDraggable();
        //this.draggableObject.setParentEntity(this);

        // observable
        support = new PropertyChangeSupport(this);

        // set margin
        HBox.setMargin(this, new Insets(15, 15, 15, 15));

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
        //addButtonForAddingAttributes();
        addButtonForAddingNewElements(0); // 0 means attribute

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
        //addButtonForAddingMethods();
        addButtonForAddingNewElements(1); // 1 means method

        // event listener
        this.nameLabel.textProperty().addListener(((observableValue, s, t1) ->
                    this.umlClass.setName(t1)
        ));

        this.setOnDragDetected(ev -> {
            owner.executeCommand(new CommandBuilder.Command() {
                //List <Double> oldPosition;
                @Override
                public void execute() {
                    //oldPosition = draggableObject.getPosition();
                    support.firePropertyChange(umlClass.getName(), 0, 1);
                }
                @Override
                public void undo() {
                    //draggableObject.setOldPosition();
                    support.firePropertyChange(umlClass.getName(), 1, 0);
                }
            });
        });

        //this.setAlignment(Pos.CENTER);
        //this.positionInArea(this, 100, 200);
    }

    public double getXpos() {
        return this.posX;
    }

    public double getYpos() {
        return this.posY;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    /**
     *
     * @param attribute_method 0 for adding an attribute
     *                         1 for adding a method
     */
    private void addButtonForAddingNewElements(int attribute_method) {
        // button for adding new attributes/methods
        GridPane addGridPane = new GridPane();
        addGridPane.setPadding(new Insets(0, 0, 5, 0));
        addGridPane.setHgap(173.71);
        // TODO different border style
        String style = new String("-fx-background-color: transparent;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-color: black;\n");
        if (attribute_method == 0) {  // attributes
            style = style + "-fx-border-width: 0 2 1 2;";
        } else {    // methods
            style = style + "-fx-border-width: 0 2 2 2;";
        }
        addGridPane.setStyle(style);
        this.getChildren().add(addGridPane);
        Text addLabel = new Text();
        GridPane.setConstraints(addLabel, 0, 0);
        addGridPane.getChildren().add(addLabel);
        // create a button for adding new methods
        Button addButton = new Button("+");
        // TODO different action
        addButton.setOnAction(e -> {
            if (attribute_method == 0) {
                insertNewAttribute();
            } else {
                insertNewMethod();
            }
        });
        addButton.setStyle("-fx-background-color: transparent;\n" +
                "-fx-border-color: transparent;\n" +
                "-fx-font-weight: bold;");
        GridPane.setConstraints(addButton, 1, 0);
        addGridPane.getChildren().add(addButton);
    }

    private void insertNewAttribute() {
        // add attribute to BE
        UMLClassifier umlClassifier = new UMLClassifier("");
        UMLAttribute umlAttribute = new UMLAttribute("", umlClassifier, "private");
        // add attribute to backend
        this.umlClass.addAttribute(umlAttribute);
        // add that attribute to GUI
        UMLAttributeGui umlAttributeGui = new UMLAttributeGui(umlAttribute);
        this.addAttributeGui(umlAttributeGui);
    }

    private void insertNewMethod() {
        // add method to backend
        UMLClassifier umlClassifier = new UMLClassifier("");
        UMLMethod umlMethod = new UMLMethod("", umlClassifier, "");
        this.umlClass.addMethod(umlMethod);
        // add to GUI
        UMLMethodGui umlMethodGui = new UMLMethodGui(umlMethod);
        this.addMethodGui(umlMethodGui);
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
                FilteredList<Node> fl;
                UMLAttributeGui umlAttributeGui = null;
                // row number 0 may be null
                if (lastRowNumber == 0) {
                    //this.attributesGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) == 0);
                    fl = this.attributesGridPane.getChildren().filtered(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    for (Node node : fl) {
                        try {
                            // FIXME mela by byt vzdy jen jedna nalezena
                            umlAttributeGui = (UMLAttributeGui) node;
                        } catch (Exception exception) {
                            // button -> simply continue
                            continue;
                        }
                        UMLAttribute umlAttribute = umlAttributeGui.getAttribute();
                        this.getUmlClass().removeAttribute(umlAttribute);

                        // TODO remove i ze seznamu atributu UMLClassGui
                    }
                    this.attributesGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) == 0);
                    this.getAttributes().remove(umlAttributeGui);
                } else {
                    //this.attributesGridPane.getChildren().removeIf(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    fl = this.attributesGridPane.getChildren().filtered(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    for (Node node : fl) {
                        try {
                            // FIXME mela by byt vzdy jen jedna nalezena
                            umlAttributeGui = (UMLAttributeGui) node;
                        } catch (Exception exception) {
                            // button -> simply continue
                            continue;
                        }
                        UMLAttribute umlAttribute = umlAttributeGui.getAttribute();
                        this.getUmlClass().removeAttribute(umlAttribute);
                        // TODO remove i ze seznamu metod UMLClassGui
                    }
                    this.attributesGridPane.getChildren().removeIf(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    this.getAttributes().remove(umlAttributeGui);
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
                FilteredList<Node> fl;
                UMLMethodGui umlMethodGui = null;
                if (lastRowNumber == 0) {
                    fl = this.methodsGridPane.getChildren().filtered(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    for (Node node : fl) {
                        try {
                            // FIXME mela by byt vzdy jen jedna nalezena
                            umlMethodGui = (UMLMethodGui) node;
                        } catch (Exception exception) {
                            // button -> simply continue
                            continue;
                        }
                        UMLMethod umlMethod = umlMethodGui.getMethod();
                        this.getUmlClass().removeMethod(umlMethod);
                        // TODO remove i ze seznamu metod UMLClassGui
                    }
                    this.methodsGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) == 0);
                    this.getMethods().remove(umlMethodGui);
                } else {
                    fl = this.methodsGridPane.getChildren().filtered(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    for (Node node : fl) {
                        try {
                            // FIXME mela by byt vzdy jen jedna nalezena
                            umlMethodGui = (UMLMethodGui) node;
                        } catch (Exception exception) {
                            // button -> simply continue
                            continue;
                        }
                        UMLMethod umlMethod = umlMethodGui.getMethod();
                        this.getUmlClass().removeMethod(umlMethod);
                        // TODO remove i ze seznamu metod UMLClassGui
                    }
                    this.methodsGridPane.getChildren().removeIf(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    this.getMethods().remove(umlMethodGui);
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

