/*
 * File:         UMLClassGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of sequence diagram gui
 */
package app.umlGui;

import app.gui.DraggableObject;
import app.uml.SeqDiagram;
import app.uml.UMLClass;
import app.uml.UMLMessage;
import app.uml.UMLMethod;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Polygon;
import org.json.simple.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Class that represent Sequence diagram in GUI. It is connected with it Backed
 * representation via SeqDiagram class.
 */
public class UMLSeqDiaGui extends AnchorPane {

    // SeqDiagram to be represented
    private SeqDiagram seqDiagram;

    // counters
    private int messageCounter;
    private int actorsCounter;

    // Gui data
    private List<UMLActorGui> actorsGui;
    private List<UMLMessageGui> messageGui;

    // current vertical lines postion
    private int yPos;


    /**
     * UMLSeqDiaGui - UML Sequence diagram gui - Constructor
     * When constructor is called everything is painted
     * @param seqDiagram BE diagram that ll be represented graphically
     */
    public UMLSeqDiaGui(SeqDiagram seqDiagram){
        this.seqDiagram = seqDiagram;

        this.messageCounter = 0;
        this.actorsCounter  = 0;
        this.yPos           = 75;

        this.actorsGui  = new ArrayList<>();
        this.messageGui = new ArrayList<>();


        loadActorsFromBE(seqDiagram);
        loadMessagesFromBE(seqDiagram);
    }


    /**
     * Load all the messages from backend class
     * @param seqDiagram BE sequence diagram implementation that hold all the data
     */
    private void loadMessagesFromBE(SeqDiagram seqDiagram){
        System.out.println("GET ALL MESSAGES");

        List<UMLMessage> messages = seqDiagram.getMessages();

        for (UMLMessage mes : messages){
            paintMessage(mes);
        }
    }

    /**
     * Load all the actors from backend class
     *
     * @param seqDiagram BE sequence diagram implementation that hold all the data
     */
    private void loadActorsFromBE(SeqDiagram seqDiagram) {

        // three information about actors they are interconnected by index.
        List<UMLClass> actors          = seqDiagram.getActors();
        List<String> actorsName        = seqDiagram.getActorsName();
        List<Boolean> createdByMessage = seqDiagram.getActorsCreatedByMessage();

        UMLClass oneActorClass   = null;
        String oneActorName      = null;
        Boolean oneCreated;

        System.out.println("LOADING ACTORS");
        for (int i = 0; i < actors.size(); i++) {

            oneActorName = actorsName.get(i);
            oneActorClass = actors.get(i);
            oneCreated = createdByMessage.get(i);

            // if Actor is created by message we will paint him later
            if (oneCreated == Boolean.FALSE){
                this.paintActor(oneActorName, oneActorClass);
            }
        }
    }

    /**
     * Paint actor
     * @param actorName actor name
     * @param actorClass class where is actor instanced from (could be null)
     */
    public void paintActor(String actorName, UMLClass actorClass){
        int n = getActorsCounter();

        String aClass;
        if (actorClass == null){
            aClass = ":(null)";
        }
        else{
            aClass = ":(" + actorClass.getName() + ")";
        }

        UMLActorGui newActor = new UMLActorGui(actorName , aClass, n, this.getyPos());
        this.actorsGui.add(newActor);

        this.getChildren().add(newActor.getTextField());

        setActorsCounter(n+1);
    }

    /**
     * Finds actor gui class
     * @param actorName name to look for
     * @return UMLActorGui or null
     */
    public UMLActorGui findActorGui(String actorName){

        // find class
        for (UMLActorGui act : this.getActorsGui()) {
            if (act.getActorName().equals(actorName)) {
                return act;
            }
        }
        // not found -> null
        return null;
    }

    /**
     * Use int Ypos
     *
     * ... synch
     * ... asynch
     * ... response
     * ... creat
     * ... free
     *
     * message (synch,asynch) is consistent if method is not null
     * Always paint vertical lines where messages are painted
     *
     * @param message one message
     */
    public void paintMessage(UMLMessage message){

        UMLActorGui temp = this.findActorGui(message.getFromActor());

        UMLMessageGui umlMessGui = new UMLMessageGui(message, getMessageGui().size(),this);
        this.addMessageGui(umlMessGui);

        // then paint message
        if (umlMessGui.getArrow() != null)
            this.getChildren().add(umlMessGui.getArrow());

        // Paint vertical line for all the actors
        for (UMLActorGui act : this.getActorsGui()) {
            if (act.getFreed() == false){
                this.getChildren().add(act.paintLine(2));
            }
        }
        this.incrementYpos();

    }

    /**
     * @param actorName
     * @param actClass
     * @return index of actor if there is no actor return -1
     */
    public int getActorGuiIndex(String actorName, UMLClass actClass){
        String aClass;
        if (actClass == null){
            aClass = ":(null)";
        }
        else{
            aClass = ":(" + actClass.getName() + ")";
        }

        String searchStr = actorName + aClass;

        // find class
        int i = 0;
        for (UMLActorGui act : this.getActorsGui()) {
            if (act.getDisplayedName().equals(searchStr)) {
                System.out.println(searchStr + "ANO");
                return i;
            }
            i++;
        }
        System.out.println(searchStr + "NE");
        // not found -> -1
        return -1;
    }

    public void addMessageGui(UMLMessageGui mess){
        this.messageGui.add(mess);
    }

    public List<UMLMessageGui> getMessageGui(){
        return this.messageGui;
    }

    public List<UMLActorGui> getActorsGui(){
        return this.actorsGui;
    }
    public UMLActorGui getNthActorGui(int n){
        return this.actorsGui.get(n);
    }

    // getrs
    public int getMessageCounter() {
        return this.messageCounter;
    }
    public int getActorsCounter() {
        return this.actorsCounter;
    }

    // setrs
    public void setActorsCounter(int i) {
        this.actorsCounter = i;
    }
    public void setMessageCounter(int i) {
        this.messageCounter = i;
    }

    public void incrementYpos(){
        this.yPos = this.yPos + 50;
    }

    public int getyPos(){
        return this.yPos;
    }

}

