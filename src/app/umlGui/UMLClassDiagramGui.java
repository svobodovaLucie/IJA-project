package app.umlGui;

import app.backend.CommandBuilder;
import app.uml.ClassDiagram;
import javafx.scene.Group;

public class UMLClassDiagramGui extends Group {

    private ClassDiagram classDiagram;

    private final CommandBuilder.Invoker invoker = new CommandBuilder.Invoker();

    public UMLClassDiagramGui(ClassDiagram classDiagram) {
        // new Group()
        this.classDiagram = classDiagram;
    }

    public void executeCommand(CommandBuilder.Command command) {
        invoker.execute(command);
    }

    public void undo() {
        invoker.undo();
    }
}
