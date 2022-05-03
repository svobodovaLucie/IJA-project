package app.uml;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;
import app.backend.Diagrams;
import app.umlGui.DiagramLoader;


/**
 * Class used for storing sequence diagram.
 */
public class SeqDiagram {
    // TODO add necessary elements

    // name
    String name;

    // actors - every actor has booleans on different list but same index
    List <UMLClass> actors;
    List <Boolean> actorConsistent;
    List <Boolean> actorsCreatedByMessage;

    // messages - every message has booleans on different list but same index
    List <UMLMessage> messages;
    List <Boolean> messageConsistent;


    public SeqDiagram(String name) {
        this.name                    = name;
        this.actors                  = new ArrayList<UMLClass>();
        this.messages                = new ArrayList<UMLMessage>();
        this.actorsCreatedByMessage  = new ArrayList<Boolean>();
        this.actorConsistent         = new ArrayList<Boolean>();
        this.messageConsistent       = new ArrayList<Boolean>();
    }


    /**
     * actorList = ActorName -> class2 -> true -> ActorName
     *    -> class2 -> false ...
     *
     * add all actors
     * @param actorsList
     */
    public void addAllActors(List<String> actorsList, Diagrams diagrams){

        System.out.println("ADD ALL ACTORS");

        Boolean creByMess;
        Boolean consistent;
        String actName;
        String className;

        //boolean b1=Boolean.parseBoolean(s1);

        for (int i = 0; i < actorsList.size();){
            actName = actorsList.get(i++);
            className = actorsList.get(i++);
            creByMess = Boolean.parseBoolean(actorsList.get(i++));

            System.out.println("actN " + actName);
            System.out.println("claN " + className);
            System.out.println("CreBM " + creByMess);

            consistent = this.classExist(className, diagrams);
            
        }

        System.out.println("####");
        System.out.println(actorsList);
        System.out.println("####");
    }

    /**
     * if class exist returns true
     * @param className
     * @return
     */
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

    /**
     * messageList = from1 -> to1 -> type1 -> methodName1 ->
     *   -> from2 -> to2 -> type2 -> methodName2
     *
     * add all messages and set their consistency
     *
     * @param messageList
     */
    public void addAllMessages(List<String> messageList, Diagrams diagram){
        System.out.println("ADD ALL MESSAGES");
        // todo check if actor exist

        System.out.println("####");
        System.out.println(messageList);
        System.out.println("####");
    }

    public void addMessage(UMLMessage mess, Boolean consistent){
        this.messages.add(mess);
        this.messageConsistent.add(consistent);
    }


    public void addActor(UMLClass actor, Boolean createdByMessage,
                            Boolean consistent) {
        this.actors.add(actor);
        this.actorsCreatedByMessage.add(createdByMessage);
        this.actorConsistent.add(consistent);
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
}
