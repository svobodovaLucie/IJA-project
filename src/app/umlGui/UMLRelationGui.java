package app.umlGui;

import app.gui.AggregationArrow;
import app.gui.AssociationArrow;
import app.gui.InheritanceArrow;
import app.uml.UMLRelation;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.Objects;

public class UMLRelationGui {
    // UML relation to be represented
    UMLRelation umlRelation;

    // class diagram in GUI
    UMLClassDiagramGui owner;

    AssociationArrow associationArrow;
    InheritanceArrow inheritanceArrow;
    AggregationArrow aggregationArrow;

    public UMLRelationGui(UMLRelation umlRelation, UMLClassDiagramGui umlClassDiagramGui) {
        //super();
        this.umlRelation = umlRelation;
        this.owner = umlClassDiagramGui;

        // get GUI class from
        UMLClassGui classFromGui = umlClassDiagramGui.findClassInterfaceGui(umlRelation.getClassFrom());

        // get GUI class to
        UMLClassGui classToGui = umlClassDiagramGui.findClassInterfaceGui(umlRelation.getClassTo());

        // check if any of the classes/interfaces is null
        if (classFromGui == null || classToGui == null) {
            System.out.println("Class/interface doesn't exist - relation not created");
            return;
        }

        // get relationship type
        String type = umlRelation.getRelationType();
        System.out.println("Type: " + type);
        // create arrows, add observers
        if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "association")) {
            this.associationArrow = new AssociationArrow();
            this.associationArrow.setStartX(classFromGui.getXpos());
            this.associationArrow.setStartY(classFromGui.getYpos());
            this.associationArrow.setEndX(classToGui.getXpos());
            this.associationArrow.setEndY(classToGui.getYpos());
            classFromGui.draggableObject.addNodeFrom(this.associationArrow);
            classToGui.draggableObject.addNodeTo(this.associationArrow);
        } else if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "inheritance")) {
            this.inheritanceArrow = new InheritanceArrow();
            classFromGui.draggableObject.addNodeFrom(this.inheritanceArrow);
            classToGui.draggableObject.addNodeTo(this.inheritanceArrow);
        } else if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "aggregation")) {
            this.aggregationArrow = new AggregationArrow(Color.WHITE);
            classFromGui.draggableObject.addNodeFrom(this.aggregationArrow);
            classToGui.draggableObject.addNodeTo(this.aggregationArrow);
        } else if (umlRelation.getRelationType().toLowerCase().equals("composition")) {
            this.aggregationArrow = new AggregationArrow(Color.BLACK);
            classFromGui.draggableObject.addNodeFrom(this.aggregationArrow);
            classToGui.draggableObject.addNodeTo(this.aggregationArrow);
        }
    }

    public Node getRelationArrow() {
        if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "association")) {
            return this.associationArrow;
        } else if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "inheritance")) {
            return this.inheritanceArrow;
        } else if (Objects.equals(umlRelation.getRelationType().toLowerCase(), "aggregation")) {
            return this.aggregationArrow;
        } else if (umlRelation.getRelationType().toLowerCase().equals("composition")) {
            return this.aggregationArrow;
        }
        return null;
    }

    public String getClassFromName() {
        return umlRelation.getClassFrom().getName();
    }

    public String getClassToName() {
        return umlRelation.getClassTo().getName();
    }
}
