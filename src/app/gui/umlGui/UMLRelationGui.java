package app.gui.umlGui;

import app.gui.helpers.AggregationArrow;
import app.gui.helpers.AssociationArrow;
import app.gui.helpers.InheritanceArrow;
import app.backend.uml.UMLMethod;
import app.backend.uml.UMLRelation;
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

    public void checkOverridenMethods() {
        // check all methods in classFrom and classTO
        for (UMLMethod methodFrom : this.umlRelation.getClassFrom().getMethods())
            for (UMLMethod methodTo : this.umlRelation.getClassTo().getMethods())
                // if the names are equal, find the method in methodsGui
                if (Objects.equals(methodFrom.getName(), methodTo.getName()))
                    for (UMLMethodGui methodGui : this.owner.findClassGui(this.umlRelation.getClassFrom()).getMethods())
                        // and change the color of the text
                        if (methodGui.getMethod() == methodFrom)
                            methodGui.setOverriden();
    }

    public String getClassFromName() {
        return umlRelation.getClassFrom().getName();
    }

    public String getClassToName() {
        return umlRelation.getClassTo().getName();
    }
}