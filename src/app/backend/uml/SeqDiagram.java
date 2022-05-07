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

package app.backend.uml;
import java.util.ArrayList;
import java.util.List;
import app.backend.Diagrams;


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
        String toClass;
        String to;
        String type;
        String metName;
        UMLClass classFrom;
        UMLClass classTo;
        UMLMethod oMethodName;

        for (int i = 0; i < messageList.size();){
            fromClass = messageList.get(i++);
            toClass   = messageList.get(i++);
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
            classTo = diagrams.getClassDiagram().findClass(toClass);

            UMLMessage message = new UMLMessage(classFrom, classTo, from, to, type, oMethodName);
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

    /**
     * Find out if actor on n index was created by <<create>> message
     * @param n index of actor
     * @return true if actor was created by message.
     */
    public Boolean getNActorCreatedByMessage(int n){
        return  this.actorsCreatedByMessage.get(n);
    }

    /**
     * @param n actor index in list.
     * @return nth actor from list of actors.
     */
    public UMLClass getNActor(int n){
        return  this.actors.get(n);
    }

    /**
     * @param n message index in list.
     * @return nth message from list of messages
     */
    public UMLMessage getNMessage(int n) {
        return this.messages.get(n);
    }

    /**
     * @return list of booleans, that represents if actor was created by <<create>> message
     */
    public List<Boolean> getActorCreatedByMessage() {
        return this.actorsCreatedByMessage;
    }

    /**
     * @return List of all the messages.
     */
    public List<UMLMessage> getMessages(){
        return this.messages;
    }

    /**
     * @return Diagram name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return List with all the actors in diagram.
     */
    public List <UMLClass> getActors() {
        return this.actors;
    }

    /**
     * @return List with all the actors names in diagram.
     */
    public List <String> getActorsName(){
        return this.actorsName;
    }

    /**
     * @return List with created by message indicator.
     */
    public List <Boolean> getActorsCreatedByMessage(){
        return this.actorsCreatedByMessage;
    }

    /**
     * Method sets name of the sequence diagram.
     *
     * @param name name of the diagram
     */
    public void setName(String name) {
        this.name = name;
    }
}
