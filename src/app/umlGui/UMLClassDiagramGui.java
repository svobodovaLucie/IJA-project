package app.umlGui;

import app.backend.CommandBuilder;
import app.uml.ClassDiagram;
import app.uml.UMLClass;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

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

    public UMLClassGui findClassGui(UMLClass classToFind) {
        ObservableList<Node> classesGui = this.getChildren();
        for (Node node : classesGui) {
            System.out.println("Node: " + node);
            try {
                UMLClassGui umlClassGui = (UMLClassGui)node;
                if (umlClassGui.getUmlClass() == classToFind) {
                    return umlClassGui;
                }
            } catch (Exception e) {
                continue;
            }
        }
        // not found
        return null;
    }
}
