package app.gui;

import app.uml.UMLAttribute;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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

    DraggableObject draggableObject = new DraggableObject();

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
        attributes = new ArrayList<>();
        methods = new ArrayList<>();

        draggableObject.makeDraggable(this);
        String cssLayout = "-fx-border-color: black;\n" +
                           "-fx-border-width: 1;\n";
        this.setStyle(cssLayout);


        class_name = new Label(name);
        this.getChildren().add(class_name);
        this.getChildren().add(new Separator());

        /*
        attributes.add(new Label("defattr1"));

        this.getChildren().add(attributes.get(1));
        this.getChildren().add(new Separator());
        methods.add(new Label("defmethod1"));
        methods.add(new Label("defmethod1"));
        this.getChildren().add(methods.get(0));
        this.getChildren().add(methods.get(1));
        */

        // addAttr button
        Button addAttr = new Button("Add Attribute");
        addAttr.setLayoutY(100);
        addAttr.setLayoutX(100);
        this.getChildren().add(addAttr);
        addAttr.setOnAction(e -> addAttribute("blah"));

        // addMeth button
        Button addMeth = new Button("Add Method");
        addMeth.setLayoutY(100);
        addMeth.setLayoutX(100);
        this.getChildren().add(addMeth);
        addMeth.setOnAction(e -> addMethod("blah"));

    }

    // adds an attribute to the attributes array
    public void addAttribute(String text) {
        attributes.add(new Label("defattr" + (attributes.size())));
        this.getChildren().add(attributes.get(attributes.size() - 1));
    }

    // adds an attribute to the attributes array
    public void addMethod(String text) {
        methods.add(new Label("defmethod" + (methods.size())));
        this.getChildren().add(methods.get(methods.size() - 1));
    }


}
