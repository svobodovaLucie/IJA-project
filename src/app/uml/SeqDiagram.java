package app.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for storing sequence diagram.
 */
public class SeqDiagram {
    // TODO add necessary elements
    // actors
    List <UMLClass> actors;

    // messages
    // TODO UML methods?
    List <String> messages;

    // name
    String name;

    public SeqDiagram(String name) {
        this.name = name;
        this.actors = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public void addActor(UMLClass actor) {
        this.actors.add(actor);
    }

    public void addMessages(List <String> messages) {
        this.messages.addAll(messages);
    }

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
