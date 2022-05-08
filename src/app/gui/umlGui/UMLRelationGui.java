/*
 * File:         UMLRelationGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLRelationGui class that represents
 * the UML relationship displayed in the GUI.
 */
package app.gui.umlGui;

import app.gui.helpers.AggregationArrow;
import app.gui.helpers.AssociationArrow;
import app.gui.helpers.InheritanceArrow;
import app.backend.uml.UMLMethod;
import app.backend.uml.UMLRelation;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import java.util.Objects;

/**
 * Class represents the UML relationship.
 */
public class UMLRelationGui {
    // UML relation to be represented
    UMLRelation umlRelation;

    // class diagram in GUI
    UMLClassDiagramGui owner;

    // types of arrows
    AssociationArrow associationArrow;
    InheritanceArrow inheritanceArrow;
    AggregationArrow aggregationArrow;

    /**
     * UMLRelationGui constructor.
     *
     * @param umlRelation UMLRelation that is represented in the GUI
     * @param umlClassDiagramGui UMLClassDiagramGui that contains the relationship
     */
    public UMLRelationGui(UMLRelation umlRelation, UMLClassDiagramGui umlClassDiagramGui) {
        this.umlRelation = umlRelation;
        this.owner = umlClassDiagramGui;

        // get GUI class from
        UMLClassGui classFromGui = umlClassDiagramGui.findClassInterfaceGui(umlRelation.getClassFrom());

        // get GUI class to
        UMLClassGui classToGui = umlClassDiagramGui.findClassInterfaceGui(umlRelation.getClassTo());

        // check if any of the classes/interfaces is null
        if (classFromGui == null || classToGui == null) {
            return;
        }

        // create arrows, add observers
        if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "association")) {
            this.associationArrow = new AssociationArrow(classFromGui.getXpos(), classFromGui.getYpos(),
                    classToGui.getXpos(), classToGui.getYpos());

            classFromGui.draggableObject.addNodeFrom(this.associationArrow);
            classToGui.draggableObject.addNodeTo(this.associationArrow);
        } else if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "inheritance")) {
            this.inheritanceArrow = new InheritanceArrow(classFromGui.getXpos(), classFromGui.getYpos(),
                    classToGui.getXpos(), classToGui.getYpos());
            classFromGui.draggableObject.addNodeFrom(this.inheritanceArrow);
            classToGui.draggableObject.addNodeTo(this.inheritanceArrow);
            checkOverridenMethods();
        } else if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "aggregation")) {
            this.aggregationArrow = new AggregationArrow(Color.WHITE, classFromGui.getXpos(),
                    classFromGui.getYpos(), classToGui.getXpos(), classToGui.getYpos());
            classFromGui.draggableObject.addNodeFrom(this.aggregationArrow);
            classToGui.draggableObject.addNodeTo(this.aggregationArrow);
        } else if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "composition")) {
            this.aggregationArrow = new AggregationArrow(Color.BLACK, classFromGui.getXpos(),
                    classFromGui.getYpos(), classToGui.getXpos(), classToGui.getYpos());
            classFromGui.draggableObject.addNodeFrom(this.aggregationArrow);
            classToGui.draggableObject.addNodeTo(this.aggregationArrow);
        }
    }

    /**
     * Method returns the arrow type that is used by this relationship as a Node.
     *
     * @return arrow as a Node type
     */
    public Node getRelationArrow() {
        if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "association")) {
            return this.associationArrow;
        } else if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "inheritance")) {
            return this.inheritanceArrow;
        } else if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "aggregation")) {
            return this.aggregationArrow;
        } else if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "composition")) {
            return this.aggregationArrow;
        }
        return null;
    }

    /**
     * Method checks if any of the methods is overridden (in inheritance relationship).
     */
    public void checkOverridenMethods() {
        // check all methods in classFrom and classTo
        for (UMLMethod methodFrom : this.umlRelation.getClassFrom().getMethods())
            for (UMLMethod methodTo : this.umlRelation.getClassTo().getMethods())
                // if the names are equal, find the method in methodsGui
                if (Objects.equals(methodFrom.getName(), methodTo.getName()))
                    for (UMLMethodGui methodGui : this.owner.findClassGui(this.umlRelation.getClassFrom()).getMethods())
                        // and change the color of the text
                        if (methodGui.getMethod() == methodFrom)
                            methodGui.setOverridden();
    }
}
