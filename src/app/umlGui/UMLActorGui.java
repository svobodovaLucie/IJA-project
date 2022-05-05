package app.umlGui;

import app.uml.UMLClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class UMLActorGui extends Label {

    private Label actorNameGui;
    String actorName; //todo maybe rework to backend end but it would be very hard
    String displayedName;

    private int actorX;
    private int actorY;

    private int currentLineYPos;

    private List<Line> lines;

    private boolean freed;

    /**
     * Paint Actor Constructor
     *
     * @param n Actor order
     */
    public UMLActorGui(String name, String ActorClass, int n, int y){

        this.actorName = name;
        this.displayedName = name + ActorClass;

        int x_space = 150;
        int x_borders = 200;

        this.actorX = (x_borders + (x_space*n));
        this.actorY = y;
        this.lines     = new ArrayList<>();

        // todo restyle
        this.actorNameGui = new Label(displayedName);
        this.actorNameGui.setAlignment(Pos.CENTER);
        this.actorNameGui.setContentDisplay(ContentDisplay.CENTER);
        this.actorNameGui.prefHeight(80);
        this.actorNameGui.prefWidth(80);
        this.actorNameGui.setStyle("-fx-border-color: black; -fx-font-size:15;");

        // set position
        this.actorNameGui.setLayoutX(x_borders+(x_space*n));
        this.actorNameGui.setLayoutY(y);

        this.freed = false;

    }

    public String getDisplayedName(){
        return this.displayedName;
    }

    public Label getTextField(){
        return this.actorNameGui;
    }

    /**
     * Draw verticall line + 50 points
     * @param thick line thickness
     */
    public Line paintLine(int thick){
        int lineCount = getLines().size();
        int startPos = this.getActorY();

        // find out how many lines are already drawn
        // and calculate end Y position
        startPos   = startPos + ((lineCount+1) * 50);
        int toPos  = startPos + 50;

        // set current Y end position of vertical line
        this.setCurrentLineYPos(toPos);

        // create new line
        Line line  = new Line();
        line.setStartX(getActorX() + 60);
        line.setStartY(startPos);
        line.setEndX(getActorX() + 60);
        line.setEndY(toPos);
        line.setStrokeWidth(thick);

        // store line to Lines list
        addLine(line);
        return line;
    }

    public void addLine(Line line){
        getLines().add(line);
    }

    public List<Line> getLines(){
        return this.lines;
    }

    public int getActorY(){
        return this.actorY;
    }
    public int getActorX(){
        return this.actorX;
    }

    public void setCurrentLineYPos(int i) {
        this.currentLineYPos = i;
    }

    public int getCurrentLineYPos() {
        return currentLineYPos;
    }

    public String getActorName(){
        return this.actorName;
    }
    public boolean getFreed(){
        return this.freed;
    }
    public void setFreed(boolean b){
        this.freed = b;
    }

}
