package app.umlGui;

import app.uml.UMLClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class UMLActorGui extends Label {

    private Label actorNameGui;
    private int actorX;
    private int actorY;

    private List<LineTo> lines;

    /**
     * Paint Actor Constructor
     *
     * @param n Actor order
     */
    public UMLActorGui(String name, int n, int y){

        int x_space = 150;
        int x_borders = 200;

        this.actorX = (x_borders + (x_space*n));
        this.actorY = y;
        this.lines     = new ArrayList<>();

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
        this.actorNameGui.setLayoutX(x_borders+(x_space*n));
        this.actorNameGui.setLayoutY(y);

    }


    public Label getTextField(){
        return this.actorNameGui;
    }
    public int getActorX(){
        return this.actorX;
    }
    public int getActorY(){
        return this.actorY;
    }

    /**
     * Draw verticall line
     * @param n Nth actor
     * @param yFrom Starting y position
     * @param yTo End y position
     * @param thick line thickness
     */
    public void paintLine(int n, int yFrom, int yTo, int thick){
        // todo
        return;
    }

}
