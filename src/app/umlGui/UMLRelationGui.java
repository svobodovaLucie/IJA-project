package app.umlGui;

import app.gui.Arrow;
import app.uml.UMLRelation;

public class UMLRelationGui extends Arrow {
    // UML relation to be represented
    UMLRelation umlRelation;

    // class diagram in GUI
    UMLClassDiagramGui owner;

    public UMLRelationGui(UMLRelation umlRelation, UMLClassDiagramGui umlClassDiagramGui) {
        this.umlRelation = umlRelation;
        this.owner = umlClassDiagramGui;

        //draggableObject.makeDraggable(this);

        // get GUI class from
        UMLClassGui classFromGui = umlClassDiagramGui.findClassGui(umlRelation.getClassFrom());

        // get GUI class to
        UMLClassGui classToGui = umlClassDiagramGui.findClassGui(umlRelation.getClassTo());

        // check null and add observers
        assert classFromGui != null;
        // draggable
        classFromGui.draggableObject.addNodeFrom(this);
        assert classToGui != null;
        // draggable
        classToGui.draggableObject.addNodeTo(this);

        // print type
        String type = umlRelation.getRelationType();
        System.out.println("Type: " + type);
    }

    public String getClassFromName() {
        return umlRelation.getClassFrom().getName();
    }

    public String getClassToName() {
        return umlRelation.getClassTo().getName();
    }
}
