/*
 * File:         UMLClassDiagramGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the class diagram that is represented in GUI.
 */
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

/**
 * Class implements class diagram presented in GUI. It extends the JavaFX class Group,
 * it contains the list of relationships, backend class diagram and invoker for UNDO.
 */
public class UMLClassDiagramGui extends Group implements PropertyChangeListener {

    private ClassDiagram classDiagram;

    private List<UMLRelationGui> relationships = new ArrayList<>();

    private final CommandBuilder.Invoker invoker = new CommandBuilder.Invoker();

    /**
     * UMLClassDiagramGui constructor.
     *
     * @param classDiagram backend class diagram
     */
    public UMLClassDiagramGui(ClassDiagram classDiagram) {
        this.classDiagram = classDiagram;
        this.classDiagram.addPropertyChangeListener(this);
    }

    /**
     * Method adds a GUI relation to the class diagram.
     *
     * @param umlRelationGui relation to be added
     */
    public void addRelation(UMLRelationGui umlRelationGui) {
        this.relationships.add(umlRelationGui);
    }

    /**
     * Method implements the design pattern observer. If the observable fired
     * an event, it reacts to this event.
     * Method removes class from GUI class diagram if "removeClass" event was fired.
     * Method removes interface if "removeInterface" event was fired.
     * Method removes relationship if "removeRelationship" event was fired.
     *
     * @param evt event that caused the observer to react
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (Objects.equals(evt.getPropertyName(), "removeClass")) {
            try {
                // remove relationships associated with the class
                for (UMLRelationGui rel : this.relationships) {
                    if (rel.umlRelation.getClassFrom() == evt.getOldValue() ||
                        rel.umlRelation.getClassTo() == evt.getOldValue()) {
                        // remove relation
                        this.classDiagram.removeRelation(rel.umlRelation.getClassFrom().getName(), rel.umlRelation.getClassTo().getName(), rel.umlRelation.getRelationType());
                    }
                }
                // remove the class
                this.getChildren().remove(findClassGui((UMLClass) evt.getOldValue()));
            } catch (Exception ignored) {
            }
        } else if (Objects.equals(evt.getPropertyName(), "removeInterface")) {
            try {
                // remove relationships associated with the interface
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
                // remove the relationship
                Node relationGui = findRelationGui((UMLRelation) evt.getOldValue());
                this.getChildren().removeIf(rel -> (rel == relationGui));
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Method executes a command.
     *
     * @param command command to be executed
     */
    public void executeCommand(CommandBuilder.Command command) {
        invoker.execute(command);
    }

    /**
     * Method executes undo operation.
     */
    public void undo() {
        invoker.undo();
    }

    /**
     * Method finds GUI class in the UMLClassDiagramGui.
     *
     * @param classToFind class to be found
     * @return UMLClassGui if found, null if not
     */
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

    /**
     * Method finds GUI interface in the UMLClassDiagramGui.
     *
     * @param interfaceToFind class to be found
     * @return UMLClassGui if found, null if not
     */
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

    /**
     * Method finds GUI relationship in the UMLClassDiagramGui.
     *
     * @param toFind relationship to be found
     * @return Node if found, null if not
     */
    public Node findRelationGui(UMLRelation toFind) {
        // check all relationships
        for (UMLRelationGui relationGui : this.relationships) {
            if (relationGui.umlRelation == toFind) {
                return relationGui.getRelationArrow();
            }
        }
        // not found
        return null;
    }

    /**
     * Method finds "toFind" element (class or interface).
     *
     * @param toFind class/interface to be found
     * @return UMLClassGui class/interface if found, null if not
     */
    public UMLClassGui findClassInterfaceGui(UMLClass toFind) {
        UMLClassGui result = findClassGui(toFind);
        if (result == null) {
            result = findInterfaceGui(toFind);
        }
        return result;
    }

    /**
     * Method returns the backend class diagram.
     * @return
     */
    public ClassDiagram getClassDiagram() {
        return this.classDiagram;
    }
}
