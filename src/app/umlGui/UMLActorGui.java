package app.umlGui;

import app.uml.UMLClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;

public class UMLActorGui extends Label implements PropertyChangeListener {
    // UMLClass that is represented in Seq GUI
    private UMLClass umlClass;

    private Label actorNameGui;

    /**
     * Paint Actor Constructor
     *
     * @param umlClass UMLClass
     * @param n Actor order
     */
    public UMLActorGui(UMLClass umlClass, int n){
        this.umlClass = umlClass;

        int x_space = 150;
        int x_borders = 200;
        int y_borders = 75;

        // todo restyle
        this.actorNameGui = new Label(umlClass.getName());
        this.actorNameGui.setAlignment(Pos.CENTER);
        this.actorNameGui.setContentDisplay(ContentDisplay.CENTER);
        this.actorNameGui.prefHeight(100);
        this.actorNameGui.prefWidth(100);
        this.actorNameGui.setStyle("-fx-background-color: #d7d0d0;");
        this.actorNameGui.setStyle("-fx-border-color: black;");
        this.actorNameGui.setFont(new Font("Arial", 13));

        // set position
        this.actorNameGui.setLayoutX(x_borders+(x_space*n));
        this.actorNameGui.setLayoutY(y_borders);

    }

    public void propertyChange(PropertyChangeEvent e) {
        this.actorNameGui.setText((String) e.getNewValue());
    }

    public Label getTextField(){ return this.actorNameGui;}

}
