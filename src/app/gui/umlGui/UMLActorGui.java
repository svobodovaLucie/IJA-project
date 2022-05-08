/*
 * File:         UMLActorGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File represent Actor as GUI object.
 */

package app.gui.umlGui;
import app.backend.uml.UMLClass;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Class holds all graphical information about UML actor and its graphical objects.
 */
public class UMLActorGui extends Label implements PropertyChangeListener {

    // UMLClass that is represented in Seq GUI
    private UMLClass umlClass;

    // actor name label
    private Label actorNameGui;

    // actor name
    String actorName;

    // displayed name actorName(class)
    String displayedName;

    // name
    String className;
    // (name)
    String clasNamespecial;

    // actor position
    private int actorX;
    private int actorY;

    // vertical line position
    private int currentLineYPos;

    // list of all vertical lines
    private List<Line> lines;

    // information if actor was freed by free message
    private boolean freed;

    // order of actor in all actor list
    int actorOrder;

    /**
     * Paint Actor Constructor.
     *
     * @param umlClass UMLClass
     * @param n Actor order
     */
    public UMLActorGui(UMLClass umlClass, String name, String ActorClass, int n, int y) {
        this.umlClass = umlClass;
        this.actorName = name;
        this.displayedName = name + ActorClass;
        this.clasNamespecial = ActorClass;
        this.className = ActorClass.substring(2,ActorClass.length()-1);
        this.actorOrder = n;

        int x_space = 200;
        int x_borders = 200;

        this.actorX = (x_borders + (x_space*n));
        this.actorY = y;
        this.lines     = new ArrayList<>();

        this.actorNameGui = new Label(displayedName);
        this.actorNameGui.setAlignment(Pos.CENTER);
        this.actorNameGui.setContentDisplay(ContentDisplay.CENTER);
        this.actorNameGui.prefHeight(80);
        this.actorNameGui.prefWidth(80);
        if(umlClass == null){
            this.actorNameGui.setStyle("-fx-border-color: red; -fx-font-size:15; -fx-padding: 3 5 3 5;");
        }
        else{
            this.actorNameGui.setStyle("-fx-border-color: black; -fx-font-size:15; -fx-padding: 3 5 3 5;");
        }


        // set position
        this.actorNameGui.setLayoutX(x_borders+(x_space*n));
        this.actorNameGui.setLayoutY(y);

        this.freed = false;

        // add this actor as an observer of uml class
        umlClass.addPropertyChangeListener(this);
    }

    /**
     * Method adds the UMLClass to the Actor.
     * @param umlClass
     */
    public void setUmlClass(UMLClass umlClass) {
        this.umlClass = umlClass;
    }

    /**
     * Method sets the actor's color to red (inconsistency).
     */
    public void setRed() {
        this.actorNameGui.setStyle("-fx-border-color: red; -fx-font-size:15" +
                "; -fx-padding: 3 5 3 5; -fx-text-fill: red;");
    }

    /**
     * Creates new lines arraylist.
     */
    public void freeLines(){
        this.lines = new ArrayList<Line>();
    }

    /**
     * Method returns displayed name.
     *
     * @return displayed name
     */
    public String getDisplayedName(){
        return this.displayedName;
    }

    /**
     * Method returns the actorNameGui label.
     *
     * @return Label
     */
    public Label getTextField(){
        return this.actorNameGui;
    }

    /**
     * Draw vertically line + 50 points.
     *
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

    /**
     * Adds line to actors lines.
     *
     * @param line line
     */
    public void addLine(Line line){
        getLines().add(line);
    }

    /**
     * Method returns the actor's label.
     *
     * @return actor name that is displayed
     */
    public Label getActorNameGui(){
        return this.actorNameGui;
    }

    /**
     * Method returns list of actor's vertical lines.
     *
     * @return list with all the actor vertical lines
     */
    public List<Line> getLines(){
        return this.lines;
    }

    /**
     * Method returns Y position of an actor.
     *
     * @return actor y pos
     */
    public int getActorY(){
        return this.actorY;
    }

    /**
     * Method returns X position of an actor.
     *
     * @return actor x pos
     */
    public int getActorX(){
        return this.actorX;
    }

    /**
     * Method sets vertical lines y position.
     *
     * @param i y pos of vertical lines
     */
    public void setCurrentLineYPos(int i) {
        this.currentLineYPos = i;
    }

    /**
     * Method returns name of the uml class.
     *
     * @return class name
     */
    public String getClassName(){
        return this.className;
    }

    /**
     * Method returns name of the actor.
     *
     * @return actor name
     */
    public String getActorName(){
        return this.actorName;
    }

    /**
     * Method returns boolean information
     * - was the actor freed by a message?
     *
     * @return information if actor is freed by message
     */
    public boolean getFreed(){
        return this.freed;
    }

    /**
     * Sets information if actor is freed.
     *
     * @param b bool true means actor is freed
     */
    public void setFreed(boolean b){
        this.freed = b;
    }

    /**
     * Method implements observer - reaction to a change
     * of the name of observable class.
     * It sets the name of the actor to new value
     * (consistent with the name of the observable class).
     *
     * @param e a PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent e) {
        this.actorNameGui.setText(this.actorName + "(" + (String) e.getNewValue() + ")");
    }

    /**
     * Method returns actor's name without ().
     *
     * @return name without ()
     */
    public String getClasNamespecial() {
        return clasNamespecial;
    }

    /**
     * Method returns actor's uml class.
     *
     * @return actor uml class
     */
    public UMLClass getUmlClass() {
        return umlClass;
    }


    /**
     * Method returns the order of an actor.
     *
     * @return order of actor
     */
    public int getActorOrder(){
        return this.actorOrder;
    }
}
