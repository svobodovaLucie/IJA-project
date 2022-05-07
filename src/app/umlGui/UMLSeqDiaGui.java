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

import app.uml.SeqDiagram;
import app.uml.UMLClass;
import app.uml.UMLMessage;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.List;

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
     * Paint new actor
     * @param actorName
     * @param actorClass
     */
    public void paintNewActor(String actorName, UMLClass actorClass){

        int n = getActorsCounter();
        System.out.println("messages " + this.getMessageCounter());

        String aClass;
        if (actorClass == null){
            aClass = ":(null)";
        }
        else{
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
     * @param umlA
     * @return index of message in message list.
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
     * @param umlA
     * @return index of actor in actor list.
     */
    public int getActorGuiIndex(UMLActorGui umlA){
        int i = 0;
        for (UMLActorGui act : this.getActorsGui()){
            if(umlA == act){
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Remove message from message list.
     * @param type message type
     * @param order message order
     */
    public void removeMessage(String type, int order){
        order = order - 1;
        System.out.println(type);
        System.out.println(order);

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
     * Repaint all the object
     */
    public void paintEVERYTHINGAGAIN(){
        int i = 0;
        for (UMLActorGui acG : getActorsGui()){
            this.getChildren().remove(acG.getActorNameGui());
            for (Line lin : acG.getLines()){
               this.getChildren().remove(lin);
            }
            acG.freeLines();
            UMLActorGui newAcG = new UMLActorGui(acG.getUmlClass(), acG.getActorName(), acG.getClasNamespecial(), getActorGuiIndex(acG) ,75);
            getActorsGui().set(i, newAcG);
            i++;
        }
        int k = 0;
        for(UMLMessageGui meG : getMessageGui()){
            this.getChildren().remove(meG.getArrow());
            //public UMLMessageGui(UMLMessage message, int order, UMLSeqDiaGui seq){
            UMLMessageGui newMess = new UMLMessageGui(meG.getMessage(), k, this);
            getMessageGui().remove(meG);
            getMessageGui().add(newMess);
            k++;
        }
        this.yPos = 75;
        this.messageCounter = 0;
        this.actorsCounter = 0;

        for (UMLActorGui acG : getActorsGui()){
            // if Actor is created by message we will paint him later
            this.getChildren().add(acG.getTextField());
            this.setActorsCounter(this.getActorsCounter()+1);
        }

        for (UMLMessageGui meg : getMessageGui()){

            // then paint message
            if (meg.getArrow() != null)
                this.getChildren().add(meg.getArrow());

            for (UMLActorGui act : this.getActorsGui()) {
               if (act.getFreed() == false){
                   this.getChildren().add(act.paintLine(2));
               }
            }
            this.setMessageCounter(this.messageCounter+1);
            this.incrementYpos();

        }

    }

    /**
     * Remove messages that contains to actor given actor
     * @param umlA
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
     * Remove messages that contains from actor given actor
     * @param umlA
     */
    public void removeMessagesFromActor(UMLActorGui umlA){
        int actIndex = umlA.getActorOrder();

        for(UMLMessageGui mesG : getMessageGui()){
            if(mesG.getIndexActFrom() == actIndex){
                this.getChildren().remove(mesG.getArrow());
                System.out.println("------- " + this.getMessageGui());
                System.out.println("------- " + this.getMessageGuiIndex(mesG));
                this.getMessageGui().remove(mesG);
            }
        }
    }

    /**
     * Remove actor
     * @param umlA
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

        System.out.println("tu");
        UMLActorGui newActor = new UMLActorGui(actorClass, actorName , aClass, n, this.getyPos());
        this.actorsGui.add(newActor);

        this.getChildren().add(newActor.getTextField());

        setActorsCounter(n+1);
    }


    /**
     * Find actor in actor list by gis gui name
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
     * Finds actor gui class
     * @param actorName name to look for
     * @return UMLActorGui or null
     */
    public UMLActorGui findActorGuiByName(String actorName){

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

        //UMLActorGui temp = this.findActorGui(message.getFromActor());

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
        this.setMessageCounter(this.messageCounter+1);
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

    /**
     * Add message to message list
     * @param mess
     */
    public void addMessageGui(UMLMessageGui mess){
        this.messageGui.add(mess);
    }

    /**
     * @return list with all the messages
     */
    public List<UMLMessageGui> getMessageGui(){
        return this.messageGui;
    }

    /**
     * @return list with all the actors
     */
    public List<UMLActorGui> getActorsGui(){
        return this.actorsGui;
    }
    public UMLActorGui getNthActorGui(int n){
        return this.actorsGui.get(n);
    }

    /**
     * @return message counter
     */
    public int getMessageCounter() {
        return this.messageCounter;
    }

    /**
     * @return actor counter
     */
    public int getActorsCounter() {
        return this.actorsCounter;
    }

    /**
     * Set actor counter
     * @param i
     */
    public void setActorsCounter(int i) {
        this.actorsCounter = i;
    }

    /**
     * Set message counter
     * @param i
     */
    public void setMessageCounter(int i) {
        this.messageCounter = i;
    }

    /**
     * Increment y pos by constant
     */
    public void incrementYpos(){
        this.yPos = this.yPos + 50;
    }

    /**
     * @return current y position.
     */
    public int getyPos(){
        return this.yPos;
    }

    public SeqDiagram getSeqDiagram() {
        return seqDiagram;
    }
}

