package app.gui;

import app.uml.UMLAttribute;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ClassGuiElement extends VBox {
    // Vbox
    //@FXML
    //private VBox class_vbox;

    // name
    @FXML
    private Label class_name;

    // seznam atributu
    private List<Label> attributes;
    // seznam metod
    private List<Label> methods;

    /*
    // line
    @FXML
    private Label class_atrib2;
    @FXML
    private Label class_attrib1;
    // line
    // seznam metod
    @FXML
    private Label class_method1;
    @FXML
    private Label class_method2;
    */

    /**
     * ClassDiagram constructor.
     *
     * @param name name of the class diagram
     */
    public ClassGuiElement(String name) {
        class_name.setText(name);

    }

    // adds an attribute to the attributes array
    public void addAttribute(String text) {
        Label attr = new Label(text);
        //this.attributes.add(attr);
        this.getChildren().add(attr);
    }



}
