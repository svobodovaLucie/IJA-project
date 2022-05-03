package app.uml;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;
import app.backend.Diagrams;
import app.umlGui.DiagramLoader;


// todo komentare
// todo popis souboru

/**
 * Class used for storing sequence diagram.
 */
public class SeqDiagram {
    // TODO add necessary elements

    // name
    String name;

    // actors - every actor has booleans on different list but same index
    List <UMLClass> actors;
    List <String> actorsName;
    List <Boolean> actorsCreatedByMessage;

    // messages - every message has booleans on different list but same index
    List <UMLMessage> messages;


    public SeqDiagram(String name) {
        this.name                    = name;
        this.actors                  = new ArrayList<UMLClass>();
        this.messages                = new ArrayList<UMLMessage>();
        this.actorsCreatedByMessage  = new ArrayList<Boolean>();
        this.actorsName              = new ArrayList<String>();
    }

    /**
     * actorList = ActorName -> class2 -> true -> ActorName
     *    -> class2 -> false ...
     *
     * add all actors
     * @param actorsList
     */
    public void addAllActors(List<String> actorsList, Diagrams diagrams){
        Boolean creByMess;
        String actName;
        String className;
        UMLClass tempClass;

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
     * messageList = from1 -> to1 -> type1 -> methodName1 ->
     *   -> from2 -> to2 -> type2 -> methodName2
     *
     * add all messages and set their consistency
     *
     * @param messageList
     */
    public void addAllMessages(List<String> messageList, Diagrams diagrams){
        System.out.println("ADD ALL MESSAGES");

        String from;
        String to;
        String type;
        String metName;

        UMLClass classFrom;
        UMLClass classTo;
        UMLMethod oMethodName;

        for (int i = 0; i < messageList.size();){
            from = messageList.get(i++);
            to = messageList.get(i++);
            type = messageList.get(i++);
            metName = messageList.get(i++);


            classFrom = diagrams.getClassDiagram().findClass(from);
            classTo = diagrams.getClassDiagram().findClass(to);
            if (classFrom != null){
                oMethodName = classFrom.findMethod(metName);
            }
            else {
                oMethodName = null;
            }

            UMLMessage message = new UMLMessage(classFrom, classTo, type, oMethodName);
            addMessage(message);
        }
    }

    public void addMessage(UMLMessage mess){
        this.messages.add(mess);
    }

    public void removeMessage(){
        //todo
        return;
    }
    public void removeActor(){
        //todo
        return;
    }

    public void addActor(UMLClass actor, Boolean createdByMessage, String name) {
        this.actors.add(actor);
        this.actorsName.add(name);
        this.actorsCreatedByMessage.add(createdByMessage);
    }

    public Boolean getNActorCreatedByMessage(int n){
        return  this.actorsCreatedByMessage.get(n);
    }

    public UMLClass getNActor(int n){
        return  this.actors.get(n);
    }

    public UMLMessage getNMessage(int n) {
        return this.messages.get(n);
    }

    public List<Boolean> getActorCreatedByMessage(){ return this.actorsCreatedByMessage; }

    public String getName() {
        return this.name;
    }

    public List <UMLClass> getActors() {
        return this.actors;
    }

    public UMLClass getActor(String name) {
        for (UMLClass actor : this.getActors()) {
            if (actor.getName().equals(name)) {
                return actor;
            }
        }
        // not found
        return null;
    }

    /*
     * if class exist returns true
     * @param className
     * @return
    private Boolean classExist(String className, Diagrams diagrams){
        UMLClass tempClass = diagrams.getClassDiagram().findClass(className);
        if (tempClass == null) {
            // TODO poresit
            System.out.println("NEKONZISTENCE!!!");
            System.out.println(className);
            return false;
        } else {
            System.out.println("KONZISTENCE!!!");
            System.out.println(className);
            return true;
            //seqDiagram.addActor(umlClass);
        }
    }
    */
}
