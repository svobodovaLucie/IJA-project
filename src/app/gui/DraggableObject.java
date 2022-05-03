package app.gui;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for making objects draggable.
 */
public class DraggableObject {
    private double mouseX;
    private double mouseY;

    private double oldX;
    private List<Double> oldXs = new ArrayList<>();
    private List<Double> oldYs = new ArrayList<>();

    private double oldY;

    private Node node;

    /**
     * Method makes an object draggable in the GUI.
     *
     * @param node the object to be made draggable
     */
    public void makeDraggable(Node node) {
        this.node = node;

        node.setOnMousePressed(mouseEvent -> {
            //oldX = (mouseEvent.getSceneX() - mouseX);
            //oldY = (mouseEvent.getSceneY() - mouseY);
            oldXs.add(0, mouseX);
            oldYs.add(0, mouseY);
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();

            System.out.println("oldX = " + oldX);
            System.out.println("mouseX = " + mouseX);
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseY);
        });
    }

    public List <Double> getPosition() {
        List<Double> position = new ArrayList<>();
        position.add(mouseX);
        position.add(mouseY);
        return position;
    }

    public List <Double> getOldPosition() {
        List<Double> position = new ArrayList<>();
        position.add(oldX);
        position.add(oldY);
        return position;
    }

    public void setPosition(List <Double> oldPosition) {
        this.mouseX = oldPosition.get(0);
        this.mouseY = oldPosition.get(1);
    }

    public void setOldPosition() {
        System.out.println("setOldPosition():");
        System.out.println("oldX = " + oldX);
        System.out.println("mouseX = " + mouseX);
        this.node.setLayoutX(oldXs.get(0));
        this.node.setLayoutY(oldYs.get(0));
        this.mouseX = oldXs.remove(0);
        this.mouseY = oldYs.remove(0);
    }
}
