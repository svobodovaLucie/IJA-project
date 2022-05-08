/*
 * File:         DraggableObject.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of DraggableObject class that is used
 * for moving and dragging objects on the scene.
 */

package app.gui.helpers;

import app.gui.umlGui.UMLClassGui;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used for making objects in the GUI draggable.
 */
public class DraggableObject extends Group {
    // positions
    private double mouseX;
    private double mouseY;
    public double posX;
    public double posY;

    // nodes
    private Node node;
    private List<Node> nodesFrom = new ArrayList<>();
    private List<Node> nodesTo = new ArrayList<>();

    // owner
    public UMLClassGui owner;

    /**
     * Method makes an object draggable in the GUI.
     *
     * @param node the object to be made draggable
     */
    public void makeDraggable(Node node) {
        this.node = node;

        node.setLayoutX(Math.random() * 1300);
        node.setLayoutY(Math.random() * 500);
        posX = node.getLayoutX();
        posY = node.getLayoutY();

        this.node.setOnMousePressed(mouseEvent -> {
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
        });

        this.node.setOnMouseReleased(mouseEvent -> {

            node.setMouseTransparent(false);

            posX = mouseEvent.getSceneX();
            posY = mouseEvent.getSceneY();
        });
    }

    /**
     * Method adds a node to the list nodesFrom.
     *
     * @param node node to be added to the list
     */
    public void addNodeFrom(Node node) {
        this.nodesFrom.add(node);
        this.node.setViewOrder(-1);
    }

    /**
     * Method adds a node to the list nodesTo.
     *
     * @param node node to be added to the list
     */
    public void addNodeTo(Node node) {
        this.nodesTo.add(node);
        this.node.setViewOrder(-1);
    }

    /**
     * Method returns the X position.
     *
     * @return X position
     */
    public Double getPosX() {
        return posX;
    }

    /**
     * Method returns the Y position.
     *
     * @return Y position
     */
    public Double getPosY() {
        return posY;
    }

    /**
     * Method sets the current position to the (posX, posY).
     *
     * @param posX X position
     * @param posY Y position
     */
    public void setPos(double posX, double posY) {
        VBox classNode = (VBox) this.node;
        node.setLayoutX(posX);
        node.setLayoutY(posY);
        for (Node n : this.nodesFrom) {
            Arrow l = (Arrow) n;
            l.setStartX((posX) + (classNode.getWidth())/2);
            l.setStartY((posY) + 10);
        }
        for (Node n : this.nodesTo) {
            Arrow l = (Arrow) n;
            l.setEndX((posX) + (classNode.getWidth())/2);
            l.setEndY((posY) + classNode.getHeight() - 10);
        }
    }
}
