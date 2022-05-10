/*
 * File:         UMLClassGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLClassGui class that represents
 * the UML class in the GUI.
 */
package app.gui.umlGui;

import app.backend.CommandBuilder;
import app.gui.helpers.DraggableObject;
import app.backend.uml.UMLAttribute;
import app.backend.uml.UMLClass;
import app.backend.uml.UMLClassifier;
import app.backend.uml.UMLMethod;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class represents a GUI class. It is inherited from VBox,
 * and it contains the list of attributes and methods.
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
    public DraggableObject draggableObject = new DraggableObject();

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
    public UMLClassGui(UMLClass umlClass, UMLClassDiagramGui umlClassDiagramGui) {
        // add BE class
        this.umlClass = umlClass;

        // make the UMLClassGui object dragable
        draggableObject.makeDraggable(this);

        // set margin
        HBox.setMargin(this, new Insets(15, 15, 15, 15));

        // add owner
        this.owner = umlClassDiagramGui;

        // set transparent border for easier dragging
        BorderStroke borderStroke = new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, null,
                new BorderWidths(5));
        this.setBorder(new Border(borderStroke));
        Insets insets = new Insets(5, 5, 5, 5);

        String style = "-fx-font-weight: bold;\n" +
                "-fx-background-color: white;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-background-radius: 0;\n" +
                "-fx-border-color: black;";

        // add name label
        this.nameLabel = new TextField(this.umlClass.getName());
        this.nameLabel.setPadding(insets);
        if (this.umlClass.isInterface()) {
            TextField interfaceLabel = new TextField("<<interface>>");
            interfaceLabel.setEditable(false);
            interfaceLabel.setStyle(style +
                    "-fx-border-width: 2 2 0 2;");
            interfaceLabel.setAlignment(Pos.CENTER);
            interfaceLabel.setPadding(new Insets(5, 5, 0, 5));
            this.getChildren().add(interfaceLabel);

            this.nameLabel.setStyle(style + "-fx-border-width: 0 2 1 2;");
        } else {
            this.nameLabel.setStyle(style + "-fx-border-width: 2 2 1 2;");
        }
        this.nameLabel.setAlignment(Pos.CENTER);
        this.getChildren().add(nameLabel);

        // check if there is only one class with this name
        checkNames();

        // create lists of attributes
        this.nodeAttributes = new ArrayList<>();
        this.attributeButtons = new ArrayList<>();
        // create GridPane for attributes
        this.attributesGridPane = new GridPane();
        this.attributesGridPane.setPadding(new Insets(5, 5, 0, 5));
        //this.attributesGridPane.setPadding(insets);
        this.attributesGridPane.setStyle("-fx-background-color: white;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-width: 1 2 0 2;\n" +
                "-fx-border-color: black;");
        this.getChildren().add(this.attributesGridPane);

        // button for adding new attributes
        addButtonForAddingNewElements(0); // 0 means attribute

        // create list of methods
        this.nodeMethods = new ArrayList<>();
        this.methodButtons = new ArrayList<>();
        // create GridPane for methods
        this.methodsGridPane = new GridPane();
        this.methodsGridPane.setPadding(new Insets(5, 5, 0, 5));
        this.methodsGridPane.setStyle("-fx-background-color: white;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-width: 1 2 0 2;\n" +
                "-fx-border-color: black;");
        this.getChildren().add(this.methodsGridPane);

        // button for adding new methods
        addButtonForAddingNewElements(1); // 1 means method

        // event listener
        this.nameLabel.textProperty().addListener(((observableValue, s, t1) -> {
            owner.executeCommand(new CommandBuilder.Command() {
                String oldName;
                @Override
                public void execute() {
                    umlClass.setName(t1);
                    oldName = s;
                    checkNames();
                }
                @Override
                public void undo() {
                    umlClass.setName(oldName);
                    nameLabel.setText(oldName);
                }
            });
        }));

        // save current position -> UNDO can be used later
        this.setOnDragDetected(ev -> {
            owner.executeCommand(new CommandBuilder.Command() {
                double oldPosX;
                double oldPosY;
                @Override
                public void execute() {
                    oldPosX = draggableObject.getPosX();
                    oldPosY = draggableObject.getPosY();
                }
                @Override
                public void undo() {
                    draggableObject.setPos(oldPosX, oldPosY);
                }
            });
        });
    }

    /**
     * Method returns the list of attributes (nodes).
     *
     * @return list of UMLAttributeGui
     */
    public List<UMLAttributeGui> getAttributes() {
        return this.nodeAttributes;
    }

    /**
     * Method returns the list of methods (nodes).
     *
     * @return list of UMLMethodGui
     */
    public List<UMLMethodGui> getMethods() {
        return this.nodeMethods;
    }

    /**
     * Method returns name of the class.
     *
     * @return name of the class
     */
    public String getName() {
        return this.nameLabel.getText();
    }

    /**
     * Method returns the UML class that is represented in the GUI.
     *
     * @return UMLClass
     */
    public UMLClass getUmlClass() {
        return this.umlClass;
    }

    /**
     * Method returns X position of the GUI class.
     *
     * @return x position
     */
    public double getXpos() {
        return this.draggableObject.posX;
    }

    /**
     * Method returns Y position of the GUI class.
     *
     * @return y position
     */
    public double getYpos() {
        return this.draggableObject.posY;
    }

    /**
     * Method checks if there are multiple classes with the same name.
     * If true, sets the name label red.
     */
    private void checkNames() {
        String style = "-fx-font-weight: bold;\n" +
                "-fx-background-color: white;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-background-radius: 0;\n" +
                "-fx-border-color: black;\n";
        if (this.umlClass.isInterface()) {
            style = style + "-fx-border-width: 0 2 1 2;\n";
        } else {
            style = style + "-fx-border-width: 2 2 1 2;\n";
        }

        for (UMLClass cls : this.owner.getClassDiagram().getClasses()) {
            if (cls == this.umlClass) {
                continue;
            }
            if (Objects.equals(cls.getName(), this.getName())) {
                this.nameLabel.setStyle(style + "-fx-text-fill: red");
                return;
            } else {
                this.nameLabel.setStyle(style + "-fx-text-fill: black");
            }
        }
        // interfaces
        for (UMLClass itf : this.owner.getClassDiagram().getInterfaces()) {
            if (itf == this.umlClass) {
                continue;
            }
            if (Objects.equals(itf.getName(), this.getName())) {
                this.nameLabel.setStyle(style + "-fx-text-fill: red");
                return;
            } else {
                this.nameLabel.setStyle(style + "-fx-text-fill: black");
            }
        }
    }
    /**
     * Method adds new attribute to the class.
     */
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

    /**
     * Method adds new method to the class.
     */
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
     * Method that adds a button bor adding new attributes/methods to VBox.
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
        String style = new String("-fx-background-color: white;\n" +
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
        addButton.setStyle("-fx-background-color: white;\n" +
                "-fx-border-color: transparent;\n" +
                "-fx-font-weight: bold;");
        GridPane.setConstraints(addButton, 1, 0);
        addGridPane.getChildren().add(addButton);
    }

    /**
     * Method inserts an attribute to the UML class. The argument is
     * inserted at the end of the arguments list. If there is an argument
     * with the same name as the one to be added, new argument is not added.
     *
     * @param attr attribute to be inserted to the list
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
            this.attributeButtons.get(lastRowNumber).setStyle("-fx-background-color: white;\n" +
                    "-fx-border-color: transparent;\n" +
                    "-fx-font-weight: bold;");
            this.attributeButtons.get(lastRowNumber).setOnAction(actionEvent ->  {
                FilteredList<Node> fl;
                UMLAttributeGui umlAttributeGui = null;
                // row number 0 may be null
                if (lastRowNumber == 0) {
                    fl = this.attributesGridPane.getChildren().filtered(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    for (Node node : fl) {
                        try {
                            umlAttributeGui = (UMLAttributeGui) node;
                        } catch (Exception exception) {
                            // button -> continue
                            continue;
                        }
                        // rmeove the attribute from backend
                        UMLAttribute umlAttribute = umlAttributeGui.getAttribute();
                        this.getUmlClass().removeAttribute(umlAttribute);
                    }
                    // remove the attribute from GUI
                    this.attributesGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) == 0);
                    this.getAttributes().remove(umlAttributeGui);
                } else {
                    fl = this.attributesGridPane.getChildren().filtered(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    for (Node node : fl) {
                        try {
                            umlAttributeGui = (UMLAttributeGui) node;
                        } catch (Exception exception) {
                            // button -> continue
                            continue;
                        }
                        // remove the attribute from backend
                        UMLAttribute umlAttribute = umlAttributeGui.getAttribute();
                        this.getUmlClass().removeAttribute(umlAttribute);
                    }
                    // remove the attribute from GUI
                    this.attributesGridPane.getChildren().removeIf(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    this.getAttributes().remove(umlAttributeGui);
                }
            });
            // adds the button to the VBox
            GridPane.setConstraints(this.attributeButtons.get(lastRowNumber), 1, lastRowNumber);
            this.attributesGridPane.getChildren().add(this.attributeButtons.get(lastRowNumber));
        } catch (UnsupportedOperationException uoe) {
            return false;
        }
        return true;
    }

    /**
     * Method inserts a method to the class. The method is inserted
     * at the end of the methods list. If there is already a method with the
     * same name as the one to be added, the method is not added.
     *
     * @param meth method to be inserted to the list
     * @return true if the method was successfully added, false if not
     */
    public boolean addMethodGui(UMLMethodGui meth) {
        // don't add new method if it already exists
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
            this.methodButtons.get(lastRowNumber).setStyle("-fx-background-color: white;\n" +
                    "-fx-border-color: transparent;\n" +
                    "-fx-font-weight: bold;");
            this.methodButtons.get(lastRowNumber).setOnAction(actionEvent ->  {
                FilteredList<Node> fl;
                UMLMethodGui umlMethodGui = null;
                if (lastRowNumber == 0) {
                    fl = this.methodsGridPane.getChildren().filtered(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    for (Node node : fl) {
                        try {
                            umlMethodGui = (UMLMethodGui) node;
                        } catch (Exception exception) {
                            // button -> continue
                            continue;
                        }
                        // rmeove the method from backend
                        UMLMethod umlMethod = umlMethodGui.getMethod();
                        this.getUmlClass().removeMethod(umlMethod);
                    }
                    // rmeove the method from GUI
                    this.methodsGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) == 0);
                    this.getMethods().remove(umlMethodGui);
                } else {
                    fl = this.methodsGridPane.getChildren().filtered(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    for (Node node : fl) {
                        try {
                            umlMethodGui = (UMLMethodGui) node;
                        } catch (Exception exception) {
                            // button -> continue
                            continue;
                        }
                        // remove the method from backend
                        UMLMethod umlMethod = umlMethodGui.getMethod();
                        this.getUmlClass().removeMethod(umlMethod);
                    }
                    // remove the method from GUI
                    this.methodsGridPane.getChildren().removeIf(node -> Objects.equals(GridPane.getRowIndex(node), lastRowNumber));
                    this.getMethods().remove(umlMethodGui);
                }
            });
            // add the button to the VBox
            GridPane.setConstraints(this.methodButtons.get(lastRowNumber), 1, lastRowNumber);
            this.methodsGridPane.getChildren().add(this.methodButtons.get(lastRowNumber));
        } catch (UnsupportedOperationException uoe) {
            return false;
        }
        return true;
    }
}

