package app.uml;

/*
 * File:         SeqDiagram.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation Sequence diagram class that represents
 * one sequence diagram.
 */

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;
import app.backend.Diagrams;
import app.umlGui.DiagramLoader;


/**
 * Class represents one sequence diagram. This is Backend data representation.
 */
public class SeqDiagram {

    // name
    String name;

    // actors - every actor has booleans on different list but same index
    List <UMLClass> actors;
    List <String> actorsName;
    List <Boolean> actorsCreatedByMessage;

    // messages - every message has booleans on different list but same index
    List <UMLMessage> messages;

    /**
     * Sequence diagram constructor.
     * @param name name of diagram
     */
    public SeqDiagram(String name) {
        this.name                    = name;
        this.actors                  = new ArrayList<UMLClass>();
        this.messages                = new ArrayList<UMLMessage>();
        this.actorsCreatedByMessage  = new ArrayList<Boolean>();
        this.actorsName              = new ArrayList<String>();
    }

    /**
     * Method store all Actors from actorsList to this Class.
     * @param actorsList List that contains information based on JSON format
     *      actorList = ActorName -> class2 -> true -> ActorName
     *           -> class2 -> false ...
     * @param diagrams Backend where are all the sequences diagrams are stored
     */
    public void addAllActors(List<String> actorsList, Diagrams diagrams){

        // temp variables' information that are extracted from list
        Boolean creByMess;
        String actName;
        String className;
        UMLClass tempClass;

        // go through list and store elements to this class
        for (int i = 0; i < actorsList.size();){
            actName = actorsList.get(i++);
            className = actorsList.get(i++);
            creByMess = Boolean.parseBoolean(actorsList.get(i++));
            tempClass = diagrams.getClassDiagram().findClass(className);

            addActor(tempClass, creByMess, actName);
            //consistent = this.classExist(className, diagrams);
        }
    }

    /**
     *
     * add all messages and set their consistency
     *
     * @param messageList List that contains information based on JSON format
     *      messageList = from1 -> to1 -> type1 -> methodName1 ->
     *           -> from2 -> to2 -> type2 -> methodName2
     *
     * @param diagrams Backend where are all the sequences diagrams are stored
     */
    public void addAllMessages(List<String> messageList, Diagrams diagrams){

        // temp variables' information that are extracted from list
        String from;
        String fromClass;
        String to;
        String type;
        String metName;
        UMLClass classFrom;
        UMLMethod oMethodName;

        for (int i = 0; i < messageList.size();){
            fromClass = messageList.get(i++);
            from      = messageList.get(i++);
            to        = messageList.get(i++);
            type      = messageList.get(i++);
            metName   = messageList.get(i++);


            classFrom = diagrams.getClassDiagram().findClass(fromClass);
            if (classFrom != null){
                oMethodName = classFrom.findMethod(metName);
            }
            else {
                oMethodName = null;
            }

            UMLMessage message = new UMLMessage(classFrom, from, to, type, oMethodName);
            addMessage(message);
        }
    }

    /**
     * Add message to sequence diagram class
     * @param mess message
     */
    public void addMessage(UMLMessage mess){
        this.messages.add(mess);
    }

    /**
     * Add actor to sequence diagram class
     * @param actor Class that actor is instance of.
     * @param createdByMessage Is actor crated by message <<create>> message?
     * @param name name of actor
     */
    public void addActor(UMLClass actor, Boolean createdByMessage, String name) {
        this.actors.add(actor);
        this.actorsName.add(name);
        this.actorsCreatedByMessage.add(createdByMessage);
    }



    public void removeMessage(){
        //todo
        return;
    }

    public void removeActor(){
        //todo
        return;
    }


    /*
    List <UMLClass> actors;
    List <String> actorsName;
    List <Boolean> actorsCreatedByMessage;
    */


    public Boolean getNActorCreatedByMessage(int n){
        return  this.actorsCreatedByMessage.get(n);
    }

    public UMLClass getNActor(int n){
        return  this.actors.get(n);
    }

    public UMLMessage getNMessage(int n) {
        return this.messages.get(n);
    }

    public List<Boolean> getActorCreatedByMessage() {
        return this.actorsCreatedByMessage;
    }

    public List<UMLMessage> getMessages(){
        return this.messages;
    }

    public String getName() {
        return this.name;
    }

    public List <UMLClass> getActors() {
        return this.actors;
    }

    public List <String> getActorsName(){
        return this.actorsName;
    }

    public List <Boolean> getActorsCreatedByMessage(){
        return this.actorsCreatedByMessage;
    }


}
