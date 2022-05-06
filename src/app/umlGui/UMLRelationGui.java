package app.umlGui;

import app.uml.UMLRelation;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Objects;

public class UMLRelationGui extends Line implements PropertyChangeListener {
    // UML relation to be represented
    UMLRelation umlRelation;

    // class diagram in GUI
    UMLClassDiagramGui owner;

    // startPoint
    //MoveTo start;

    // end point
    //LineTo end;

    //DraggableObject draggableObject = new DraggableObject();

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
        classFromGui.addPropertyChangeListener(this);
        // draggable
        classFromGui.draggableObject.addNodeFrom(this);
        assert classToGui != null;
        classToGui.addPropertyChangeListener(this);
        // draggable
        classToGui.draggableObject.addNodeTo(this);

        // print type
        String type = umlRelation.getRelationType();
        System.out.println("Type: " + type);

        //List <Double> pos;
        //SimpleDoubleProperty s1 = ne
        //pos = classFromGui.draggableObject.getPosition();
        //this.startXProperty().bind(classFromGui.draggableObject.getPosition());

        /*
        // start point
        start = new MoveTo();
        List<Double> pos = classFromGui.draggableObject.getPosition();
        setStartPoint(pos.get(0), pos.get(1));
         */
        /*
        // line
        end = new LineTo();
        pos = classToGui.draggableObject.getPosition();
        setEndPoint(pos.get(0), pos.get(1));
         */
        //this.getElements().addAll(start, end);

    }

    private void setStartPoint(double x, double y) {
        //start.setX(x);
        //start.setY(y);
    }

    private void setEndPoint(double x, double y) {
        //end.setX(x);
        //end.setY(y);
    }

    public String getClassFromName() {
        return umlRelation.getClassFrom().getName();
    }

    public String getClassToName() {
        return umlRelation.getClassTo().getName();
    }

    public void propertyChange(PropertyChangeEvent event) {
        System.out.println(event.getPropertyName());
        // TODO podle toho zmenit bud from point nebo to point
        if (Objects.equals(event.getPropertyName(), this.getClassFromName())) {
            System.out.println("Change FROM path");
            // get points from GUI classFrom
            //List<Double> pos = this.owner.findClassGui(umlRelation.getClassFrom()).draggableObject.getAfterMouseReleased();
            //setStartPoint(pos.get(0), pos.get(1));
        } else if (Objects.equals(event.getPropertyName(), this.getClassToName())) {
            System.out.println("Change TO path");
            // get points from GUI classTo
            //List<Double> pos = this.owner.findClassGui(umlRelation.getClassTo()).draggableObject.getAfterMouseReleased();
            //setEndPoint(pos.get(0), pos.get(1));
        } else {
            System.out.println("propertyChange umlRelationGui something else, wtf");
        }
    }
}
