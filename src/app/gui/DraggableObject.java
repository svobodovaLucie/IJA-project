package app.gui;

import app.uml.UMLClass;
import app.umlGui.UMLClassGui;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for making objects draggable.
 */
public class DraggableObject extends Group {
    private double mouseX;
    private double mouseY;

    //private double oldXscene;
    private List<Double> oldXs = new ArrayList<>();
    private List<Double> oldYs = new ArrayList<>();

    //private List<Double> afterMouseReleased = new ArrayList<>();

    //private double oldYscene;

    //private Node node;

    public SimpleDoubleProperty posX;
    public SimpleDoubleProperty posY;

    public UMLClassGui owner;

    public void setParentEntity(UMLClassGui owner) {
        this.owner = owner;
        this.posX = new SimpleDoubleProperty(owner.x.getValue());
        this.posY = new SimpleDoubleProperty(owner.y.getValue());
    }


    /**
     * Method makes an object draggable in the GUI.
     *
     * @param node the object to be made draggable
     */
    /*
    public void makeDraggable(Node node) {
        this.node = node;
        this.afterMouseReleased.add(0, 0.0);
        this.afterMouseReleased.add(1, 0.0);

        node.setOnMousePressed(mouseEvent -> {
            oldXscene = (mouseEvent.getSceneX() - mouseX);
            oldYscene = (mouseEvent.getSceneY() - mouseY);
            oldXs.add(0, mouseX);
            oldYs.add(0, mouseY);
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();

            System.out.println("mouseX = " + mouseX);

            node.setMouseTransparent(true);
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseY);
            oldXscene = (mouseEvent.getSceneX() - mouseX);
            oldYscene = (mouseEvent.getSceneY() - mouseY);
        });

        node.setOnMouseReleased(mouseEvent -> {
            afterMouseReleased.set(0, mouseEvent.getSceneX() - mouseX);
            afterMouseReleased.set(1, mouseEvent.getSceneY() - mouseY);

            System.out.println("after released");

            node.setMouseTransparent(false);
        });
        node.setOnDragDetected(e -> {
            System.out.println("fullDrag()");
            node.startFullDrag();
        });

    }

     */




    /*
    public List<Double> getAfterMouseReleased() {
        return this.afterMouseReleased;
    }

    public List <Double> getPosition() {
        List<Double> position = new ArrayList<>();
        position.add(oldXscene);
        position.add(oldYscene);
        return position;
    }

     */

    /*
    public List <Double> getOldPosition() {
        List<Double> position = new ArrayList<>();
        position.add(oldX);
        position.add(oldY);
        return position;
    }

     */

    /*
    public void setPosition(List <Double> oldPosition) {
        this.mouseX = oldPosition.get(0);
        this.mouseY = oldPosition.get(1);
    }

    public void setOldPosition() {
        System.out.println("setOldPosition():");
        System.out.println("mouseX = " + mouseX);
        //this.node.setLayoutX(oldXs.get(0));
        //this.node.setLayoutY(oldYs.get(0));
        this.mouseX = oldXs.remove(0);
        this.mouseY = oldYs.remove(0);
    }

     */
}
