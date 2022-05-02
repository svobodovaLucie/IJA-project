package app.umlGui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UMLActorGui extends Label {


    private Label actorNameGui;

    /**
     * Paint Actor Constructor
     *
     * @param name Actor name
     * @param n Actor order
     */
    public UMLActorGui(String name, int n){
        int x_space = 150;
        int borders = 25;

        // todo restyle
        this.actorNameGui = new Label(name);
        this.actorNameGui.setAlignment(Pos.CENTER);
        this.actorNameGui.setContentDisplay(ContentDisplay.CENTER);
        this.actorNameGui.prefHeight(100);
        this.actorNameGui.prefWidth(100);
        this.actorNameGui.setStyle("-fx-background-color: #d7d0d0;");
        this.actorNameGui.setStyle("-fx-border-color: black;");
        this.actorNameGui.setFont(new Font("Arial", 13));

        // set position
        this.actorNameGui.setLayoutX(borders+(x_space*n));
        this.actorNameGui.setLayoutY(borders);

    }

    public Label getTextField(){ return this.actorNameGui;}

}
