
package app.gui.umlGui;

import app.backend.CommandBuilder;
import app.backend.uml.ClassDiagram;
import app.backend.uml.UMLClass;
import app.backend.uml.UMLRelation;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UMLClassDiagramGui extends Group implements PropertyChangeListener {

    private ClassDiagram classDiagram;

    private List<UMLRelationGui> relationships = new ArrayList<>();

    private final CommandBuilder.Invoker invoker = new CommandBuilder.Invoker();

    public UMLClassDiagramGui(ClassDiagram classDiagram) {
        // new Group()
        this.classDiagram = classDiagram;
        this.classDiagram.addPropertyChangeListener(this);
    }

    public void addRelation(UMLRelationGui umlRelationGui) {
        this.relationships.add(umlRelationGui);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (Objects.equals(evt.getPropertyName(), "removeClass")) {
            try {
                // remove relationships
                for (UMLRelationGui rel : this.relationships) {
                    if (rel.umlRelation.getClassFrom() == evt.getOldValue() ||
                        rel.umlRelation.getClassTo() == evt.getOldValue()) {
                        // remove relation
                        this.classDiagram.removeRelation(rel.umlRelation.getClassFrom().getName(), rel.umlRelation.getClassTo().getName(), rel.umlRelation.getRelationType());
                    }
                }
                // remove class itself
                this.getChildren().remove(findClassGui((UMLClass) evt.getOldValue()));
            } catch (Exception ignored) {
            }
        } else if (Objects.equals(evt.getPropertyName(), "removeInterface")) {
            try {
                // TODO remove relationships
                // remove relationships
                for (UMLRelationGui rel : this.relationships) {
                    if (rel.umlRelation.getClassFrom() == evt.getOldValue() ||
                            rel.umlRelation.getClassTo() == evt.getOldValue()) {
                        // remove relation
                        this.classDiagram.removeRelation(rel.umlRelation.getClassFrom().getName(), rel.umlRelation.getClassTo().getName(), rel.umlRelation.getRelationType());
                    }
                }
                // remove the interface
                this.getChildren().remove(findInterfaceGui((UMLClass) evt.getOldValue()));
            } catch (Exception ignored) {
            }
        } else if (Objects.equals(evt.getPropertyName(), "removeRelationship")) {
            try {
                Node relationGui = findRelationGui((UMLRelation) evt.getOldValue());
                this.getChildren().removeIf(rel -> (rel == relationGui));
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

    public Node findRelationGui(UMLRelation toFind) {
        // prochazime vsechny RelationsGui
        for (UMLRelationGui relationGui : this.relationships) {
            if (relationGui.umlRelation == toFind) {
                return relationGui.getRelationArrow();
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
