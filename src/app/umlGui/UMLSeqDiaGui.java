/*
 * File:         UMLClassGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLClass sequence diagram 
 */
package app.umlGui;

import app.gui.DraggableObject;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UMLSeqDiaGui extends AnchorPane {

    // NO GUI data
    // name_met1 -> class1 -> name_met2 -> class2 -> name_met2 -> class2 ....
    private List<String> actorsList;
    // from1 -> to1 -> type1 -> from2 -> to2 -> type2 ....
    private List<String> messageList;
    private String       name;
    private int messageCounter;
    private int actorsCounter;

    // Gui data
    private List<UMLActorGui> ActorsGui;

    /**
     * UMLSeqDiaGui - UML Sequence diagram gui - Constructor
     * When constructor is called everything is painted
     *
     * todo convert to graphics nodes
     *
     * @param actorsList
     * @param messageList
     * @param name
     */
    public UMLSeqDiaGui(List<String> actorsList, List<String> messageList, String name){

        this.actorsList  = actorsList;
        this.messageList = messageList;
        this.name        = name;

        this.messageCounter = 0;
        this.actorsCounter  = 0;

        this.ActorsGui = new ArrayList<>();

        // set transparent border for easier dragging
        /*
        BorderStroke borderStroke = new BorderStroke(Color.TRANSPARENT,
                BorderStrokeStyle.SOLID, null, new BorderWidths(5));
        this.setBorder(new Border(borderStroke));

         */

    }

    /**
     * Paint all the actors that are stored in actorList
     * Put then on static places base on actorsCounter
     *
     * Call only on empty !! (in Constructor)
     *
     */
    public void addAllActorsGUI(){

        setActorsCounter(0);

        //for (int i = 0; i < getActorsList() )

        this.addActorGUI();
        this.addActorGUI();

        // add name label

    }

    public void addActorGUI(){
        // Find out actor order
        int n = getActorsCounter();

        UMLActorGui newActor = new UMLActorGui(name, n);
        this.ActorsGui.add(newActor);

        System.out.println("tu");
        this.getChildren().add(newActor.getTextField());
        System.out.println( this.getChildren().get(0));

        setActorsCounter(n+1);

    }

    public void addAllMessagesGUI(){
        return;
    }

    public void addMessageGUI(){
        return;
    }

    // getrs
    public String getNActorActorName(int n){ return getActorsList().get((n*2)); }
    public String getNActorClass(int n){ return getActorsList().get((n*2)+1); }
    public String getNMessageFrom(int n){ return getActorsList().get((n*3)); }
    public String getNMessageTo(int n){ return getActorsList().get((n*3) + 1); }
    public String getNMessageType(int n){ return getActorsList().get((n*3) + 2); }
    public List<String> getActorsList(){ return this.actorsList; }
    public List<String> getMessageList(){ return this.messageList; }
    public String getName() { return this.name; }
    public int getMessageCounter() { return this.messageCounter; }
    public int getActorsCounter() { return this.actorsCounter; }

    // setrs or (adds to list)
    public void setActorsCounter(int i) { this.actorsCounter = i;}
    public void setMessageCounter(int i) { this.messageCounter = i;}
    public void addActorToList(String ActorName, String Class){
        this.actorsList.add(ActorName);
        this.actorsList.add(Class);
    }
    public void addMessageToList(String from, String to, String type){
        this.messageList.add(from);
        this.messageList.add(to);
        this.messageList.add(type);
    }
}

