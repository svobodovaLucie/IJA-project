/*
 * File:         UMLSeqDiaGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the sequence diagram represented in GUI.
 */
package app.gui.umlGui;

import app.backend.uml.SeqDiagram;
import app.backend.uml.UMLClass;
import app.backend.uml.UMLMessage;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent Sequence diagram in GUI. It is connected with it Backend
 * representation via SeqDiagram class.
 */
public class UMLSeqDiaGui extends AnchorPane implements PropertyChangeListener {

    // SeqDiagram to be represented
    private SeqDiagram seqDiagram;

    // counters
    private int messageCounter;
    private int actorsCounter;

    // GUI data
    private ArrayList<UMLActorGui> actorsGui;
    private ArrayList<UMLMessageGui> messageGui;

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
     * Load all the messages from backend class.
     *
     * @param seqDiagram BE sequence diagram implementation that hold all the data
     */
    private void loadMessagesFromBE(SeqDiagram seqDiagram){
        List<UMLMessage> messages = seqDiagram.getMessages();
        for (UMLMessage mes : messages){
            paintMessage(mes);
        }
    }

    /**
     * Load all the actors from backend class.
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
     * Paint new actor.
     *
     * @param actorName name of the actor
     * @param actorClass actor's class
     */
    public void paintNewActor(String actorName, UMLClass actorClass){
        int n = getActorsCounter();
        String aClass;
        if (actorClass == null){
            aClass = ":(null)";
        } else{
            aClass = ":(" + actorClass.getName() + ")";
        }

        UMLActorGui newActor = new UMLActorGui(actorClass, actorName , aClass, n, 75);
        this.actorsGui.add(newActor);

        for (int i = 0; i < this.getMessageCounter(); i++) {
            this.getChildren().add(newActor.paintLine(2));
        }
        this.getChildren().add(newActor.getTextField());
        setActorsCounter(n+1);
    }

    /**
     * Method implements the observer design pattern. It reacts to the event that
     * happened in observable.  It sets the actor's name to the new value if name
     * of the backend class changed.
     *
     * @param ev event
     */
    public void propertyChange(PropertyChangeEvent ev) {
        for (UMLActorGui actor : this.getActorsGui()) {
            if (actor.getUmlClass() == ev.getOldValue()) {
                actor.setUmlClass(null);
                actor.setRed();
            }
        }
    }

    /**
     * Method returns an index of the message.
     *
     * @param umlA message
     * @return index of message in message list
     */
    public int getMessageGuiIndex(UMLMessageGui umlA){
        int i = 0;
        for (UMLMessageGui act : this.getMessageGui()){
            if(umlA == act){
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Remove message from message list.
     *
     * @param type message type
     * @param order message order
     */
    public void removeMessage(String type, int order){
        order = order - 1;

        for( UMLMessageGui mes : this.getMessageGui()){
            if(mes.getMessage().getType().equals(type)){
                if(mes.getOrder() == order){
                    this.getChildren().remove(mes.getArrow());
                    this.getMessageGui().remove(mes);
                }
            }
        }
    }

    /**
     * Remove messages that contain the given actor as toActor.
     *
     * @param umlA actor
     */
    public void removeMessagesToActor(UMLActorGui umlA){
        int actIndex = umlA.getActorOrder();
        for(UMLMessageGui mesG : getMessageGui()){
            if(mesG.getIndexActTo() == actIndex){
                this.getChildren().remove(mesG.getArrow());
                this.getMessageGui().remove(mesG);
            }
        }
    }

    /**
     * Remove messages that contain fromActor given actor.
     *
     * @param umlA actor
     */
    public void removeMessagesFromActor(UMLActorGui umlA){
        int actIndex = umlA.getActorOrder();

        for(UMLMessageGui mesG : getMessageGui()){
            if(mesG.getIndexActFrom() == actIndex){
                this.getChildren().remove(mesG.getArrow());
                this.getMessageGui().remove(mesG);
            }
        }
    }

    /**
     * Remove actor.
     *
     * @param umlA actor to be removed
     */
    public void removeActor(UMLActorGui umlA){

        for(Line line : umlA.getLines()){
            this.getChildren().remove(line);
        }

        removeMessagesToActor(umlA);
        removeMessagesFromActor(umlA);

        this.getChildren().remove(umlA.getTextField());
        this.getActorsGui().remove(umlA);
    }

    /**
     * Paint actor.
     *
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

        UMLActorGui newActor = new UMLActorGui(actorClass, actorName , aClass, n, this.getyPos());
        this.actorsGui.add(newActor);
        this.getChildren().add(newActor.getTextField());
        setActorsCounter(n+1);
    }


    /**
     * Find actor in actor list by actor gui name.
     *
     * @param actorName actor gui name.
     * @return UMLActorGui if found or null
     */
    public UMLActorGui findActorGuiByWholeName(String actorName){

        // find class
        for (UMLActorGui act : this.getActorsGui()) {
            if (act.getDisplayedName().equals(actorName)) {
                return act;
            }
        }
        // not found -> null
        return null;

    }

    /**
     * Use int Ypos for paintin a message.
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

        UMLMessageGui umlMessGui = new UMLMessageGui(message, getMessageGui().size(),this);
        this.addMessageGui(umlMessGui);

        // then paint message
        if (umlMessGui.getArrow() != null)
            this.getChildren().add(umlMessGui.getArrow());

        // Paint vertical line for all the actors
        for (UMLActorGui act : this.getActorsGui()) {
            if (!act.getFreed()){
                this.getChildren().add(act.paintLine(2));
            }
        }
        this.incrementYpos();
        this.setMessageCounter(this.messageCounter+1);
    }

    /**
     * Method returns an index of the actor.
     *
     * @param actorName actor
     * @param actClass actor's class
     * @return index of actor in actor list
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
                return i;
            }
            i++;
        }
        // not found -> -1
        return -1;
    }

    /**
     * Add message to message list.
     *
     * @param mess message to be added
     */
    public void addMessageGui(UMLMessageGui mess){
        this.messageGui.add(mess);
    }

    /**
     * Method returns the list of GUI messages.
     *
     * @return list with all the messages
     */
    public List<UMLMessageGui> getMessageGui(){
        return this.messageGui;
    }

    /**
     * Method returns list of GUI actors.
     *
     * @return list with all the actors
     */
    public List<UMLActorGui> getActorsGui(){
        return this.actorsGui;
    }

    /**
     * Method returns actor on the index n.
     *
     * @param n index of the actor to be returned
     * @return UMLActorGui
     */
    public UMLActorGui getNthActorGui(int n){
        return this.actorsGui.get(n);
    }

    /**
     * Method returns the message COUNTER.
     *
     * @return message counter
     */
    public int getMessageCounter() {
        return this.messageCounter;
    }

    /**
     * Method returns the actors counter.
     *
     * @return actor counter
     */
    public int getActorsCounter() {
        return this.actorsCounter;
    }

    /**
     * Set actor counter.
     *
     * @param i number to be set
     */
    public void setActorsCounter(int i) {
        this.actorsCounter = i;
    }

    /**
     * Set message counter.
     *
     * @param i number to be set
     */
    public void setMessageCounter(int i) {
        this.messageCounter = i;
    }

    /**
     * Increment y pos by constant.
     */
    public void incrementYpos(){
        this.yPos = this.yPos + 50;
    }

    /**
     * Method returns current Y position.
     *
     * @return current y position.
     */
    public int getyPos(){
        return this.yPos;
    }

    /**
     * Method returns sequence diagram.
     *
     * @return backend sequence diagram
     */
    public SeqDiagram getSeqDiagram() {
        return seqDiagram;
    }

    /**
     * Method returns name of the sequence diagram.
     *
     * @return name of the sequence diagram
     */
    public String getName() {
        return this.seqDiagram.getName();
    }
}

