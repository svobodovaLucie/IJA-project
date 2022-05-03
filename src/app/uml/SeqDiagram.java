package app.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for storing sequence diagram.
 */
public class SeqDiagram {
    // TODO add necessary elements

    // name
    String name;
    // actors
    List <UMLClass> actors;
    List <Boolean> actorsCreatedByMessage;
    // messages
    List <UMLMessage> messages;


    public SeqDiagram(String name) {
        this.name                    = name;
        this.actors                  = new ArrayList<UMLClass>();
        this.messages                = new ArrayList<UMLMessage>();
        this.actorsCreatedByMessage = new ArrayList<Boolean>();
    }

    public void addActor(UMLClass actor, boolean createdByMessage) {
        this.actors.add(actor);

    }

    public void addMessages(List <String> messages) {
        this.messages.addAll(messages);
    }

    public Boolean getNActorCreatedByMessage(int n){ return  this.actorsCreatedByMessage.get(n);}

    public UMLClass getNActor(int n){ return  this.actors.get(n);}

    public UMLMethod getNMessage(int n){ return  this.messages.get(n);}

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
