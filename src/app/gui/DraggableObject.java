package app.gui;

import app.umlGui.UMLClassGui;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for making objects draggable.
 */
public class DraggableObject extends Group {
    private double mouseX;
    private double mouseY;


    private List<Double> oldXs = new ArrayList<>();
    private List<Double> oldYs = new ArrayList<>();

    private List<Double> afterMouseReleased = new ArrayList<>();

    private double oldXscene;
    private double oldYscene;

    private Node node;
    private List<Node> nodesFrom = new ArrayList<>();
    private List<Node> nodesTo = new ArrayList<>();

    public double posX;
    public double posY;

    public UMLClassGui owner;

    /**
     * Method makes an object draggable in the GUI.
     *
     * @param node the object to be made draggable
     */
    public void makeDraggable(Node node) {
        this.node = node;
        this.afterMouseReleased.add(0, 0.0);
        this.afterMouseReleased.add(1, 0.0);

        posX = node.getLayoutX();
        posY = node.getLayoutY();

        this.node.setOnMousePressed(mouseEvent -> {
            oldXscene = (mouseEvent.getSceneX() - mouseX);
            oldYscene = (mouseEvent.getSceneY() - mouseY);
            oldXs.add(0, mouseX);
            oldYs.add(0, mouseY);
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();

            node.setMouseTransparent(true);

            posX = node.localToScene(node.getBoundsInLocal()).getMinX();
            posY = node.localToScene(node.getBoundsInLocal()).getMinY();

            VBox classNode = (VBox) this.node;
            for (Node n : this.nodesFrom) {
                Arrow l = (Arrow) n;
                l.setStartX((mouseEvent.getSceneX() - mouseX) + (classNode.getWidth())/2);
                l.setStartY((mouseEvent.getSceneY() - mouseY) + 10);
            }
            for (Node n : this.nodesTo) {
                Arrow l = (Arrow) n;
                l.setEndX((mouseEvent.getSceneX() - mouseX) + (classNode.getWidth())/2);
                l.setEndY((mouseEvent.getSceneY() - mouseY) + classNode.getHeight() - 10);
            }
        });

        this.node.setOnMouseDragged(mouseEvent -> {
            VBox classNode = (VBox) this.node;
            node.setLayoutX(mouseEvent.getSceneX() - mouseX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseY);
            for (Node n : this.nodesFrom) {
                Arrow l = (Arrow) n;
                l.setStartX((mouseEvent.getSceneX() - mouseX) + (classNode.getWidth())/2);
                l.setStartY((mouseEvent.getSceneY() - mouseY) + 10);
            }
            for (Node n : this.nodesTo) {
                Arrow l = (Arrow) n;
                l.setEndX((mouseEvent.getSceneX() - mouseX) + (classNode.getWidth())/2);
                l.setEndY((mouseEvent.getSceneY() - mouseY) + classNode.getHeight() - 10);
            }

            oldXscene = (mouseEvent.getSceneX() - mouseX);
            oldYscene = (mouseEvent.getSceneY() - mouseY);
        });

        this.node.setOnMouseReleased(mouseEvent -> {
            afterMouseReleased.set(0, mouseEvent.getSceneX() - mouseX);
            afterMouseReleased.set(1, mouseEvent.getSceneY() - mouseY);

            node.setMouseTransparent(false);

            posX = mouseEvent.getSceneX();
            posY = mouseEvent.getSceneY();
        });
    }

    public void addNodeFrom(Node node) {
        this.nodesFrom.add(node);
        this.node.setViewOrder(-1);
    }

    public void addNodeTo(Node node) {
        this.nodesTo.add(node);
        this.node.setViewOrder(-1);
    }


    public List<Double> getAfterMouseReleased() {
        return this.afterMouseReleased;
    }

    public List <Double> getPosition() {
        List<Double> position = new ArrayList<>();
        position.add(oldXscene);
        position.add(oldYscene);
        return position;
    }

    /*
    public List <Double> getOldPosition() {
        List<Double> position = new ArrayList<>();
        position.add(oldX);
        position.add(oldY);
        return position;
    }

     */

    public void setPosition(List <Double> oldPosition) {
        this.mouseX = oldPosition.get(0);
        this.mouseY = oldPosition.get(1);
    }

    public void setOldPosition() {
        this.mouseX = oldXs.remove(0);
        this.mouseY = oldYs.remove(0);
    }
}
