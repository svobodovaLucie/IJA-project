
package app.umlGui;

import app.backend.CommandBuilder;
import app.uml.ClassDiagram;
import app.uml.UMLClass;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UMLClassDiagramGui extends Group implements PropertyChangeListener {

    private ClassDiagram classDiagram;

    private final CommandBuilder.Invoker invoker = new CommandBuilder.Invoker();

    public UMLClassDiagramGui(ClassDiagram classDiagram) {
        // new Group()
        this.classDiagram = classDiagram;
        this.classDiagram.addPropertyChangeListener(this);
    }
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "removeClass") {
            try {
                this.getChildren().remove(findClassGui((UMLClass) evt.getOldValue()));
                System.out.println("ClassDiagramGui - class removed");
            } catch (Exception ignored) {
            }
        } else {
            try {
                this.getChildren().remove(findInterfaceGui((UMLClass) evt.getOldValue()));
                System.out.println("ClassDiagramGui - interface removed");
            } catch (Exception ignored) {
            }
        }
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

    public UMLClassGui findInterfaceGui(UMLClass interfaceToFind) {
        ObservableList<Node> interfacesGui = this.getChildren();
        for (Node node : interfacesGui) {
            try {
                UMLClassGui umlInterfaceGui = (UMLClassGui)node;
                if (umlInterfaceGui.getUmlClass() == interfaceToFind) {
                    return umlInterfaceGui;
                }
            } catch (Exception e) {
                continue;
            }
        }
        // not found
        return null;
    }

    public UMLClassGui findClassInterfaceGui(UMLClass toFind) {
        UMLClassGui result = findClassGui(toFind);
        if (result == null) {
            result = findInterfaceGui(toFind);
        }
        return result;
    }

    public ClassDiagram getClassDiagram() {
        return this.classDiagram;
    }
}
