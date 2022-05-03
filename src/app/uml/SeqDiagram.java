package app.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for storing sequence diagram.
 */
public class SeqDiagram {
    // TODO add necessary elements
    // actors
    List <String> actors;

    // messages
    List <String> messages;

    // name
    String name;

    public SeqDiagram(String name) {
        this.name = name;
        this.actors = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public void addActors(List<String> actors) {
        this.actors.addAll(actors);
    }

    public void addMessages(List <String> messages) {
        this.messages.addAll(messages);
    }
}
