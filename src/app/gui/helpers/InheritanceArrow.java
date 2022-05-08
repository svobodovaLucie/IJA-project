/*
 * File:         InheritanceArrow.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od InheritanceArrow type.
 */
package app.gui.helpers;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * Class represents the inheritance arrow. It implements
 * the Arrow interface.
 */
public class InheritanceArrow extends Group implements Arrow {

    private final Line line;

    /**
     * InheritanceArrow constructor.
     *
     * @param sX start X property
     * @param sY start Y property
     * @param eX end X property
     * @param eY end Y property
     */
    public InheritanceArrow(double sX, double sY, double eX, double eY) {
        this(new Line(sX + 50, sY + 100, eX + 50, eY + 100), new Polygon());
    }

    /**
     * InheritanceArrow constructor.
     *
     * @param line main arrow line
     * @param triangle arrow head
     */
    private InheritanceArrow(Line line, Polygon triangle) {
        super(line, triangle);
        this.line = line;
        triangle.setStroke(Color.BLACK);
        triangle.setFill(Color.WHITE);
        triangle.setStrokeWidth(2);

        InvalidationListener updater = o -> {
            double posX = getEndX();
            double posY = getEndY();

            posX += 10;
            posY -= 6;

            triangle.getPoints().setAll(
                    (posX - 20.0), (posY + 0.0),
                    (posX + 0.0), (posY - 10.0),
                    (posX + 0.0), (posY + 10.0)
            );
            triangle.setRotate(90);
        };

        // add updater to properties
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    }

    /**
     * Method sets startX property.
     *
     * @param value X position to be set
     */
    public final void setStartX(double value) {
        line.setStartX(value);
    }

    /**
     * Method returns DoubleProperty startX.
     *
     * @return startX DoubleProperty
     */
    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    /**
     * Method sets startY property.
     *
     * @param value Y position to be set
     */
    public final void setStartY(double value) {
        line.setStartY(value);
    }

    /**
     * Method returns DoubleProperty startX.
     *
     * @return startY DoubleProperty
     */
    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    /**
     * Method sets endX property.
     *
     * @param value X position to be set
     */
    public void setEndX(double value) {
        line.setEndX(value);
    }

    /**
     * Method returns endX property.
     *
     * @return endX property
     */
    public final double getEndX() {
        return line.getEndX();
    }

    /**
     * Method returns DoubleProperty endX.
     *
     * @return endX DoubleProperty
     */
    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    /**
     * Method sets endY property.
     *
     * @param value Y position to be set
     */
    public void setEndY(double value) {
        line.setEndY(value + 20);
    }

    /**
     * Method returns endY property.
     *
     * @return endY property
     */
    public final double getEndY() {
        return line.getEndY();
    }

    /**
     * Method returns DoubleProperty endY.
     *
     * @return endY DoubleProperty
     */
    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }
}