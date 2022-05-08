/*
 * File:         AggregationArrow.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the aggregation arrow.
 */
package app.gui.helpers;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * Class represents the aggregation and composition arrow types.
 * It implements the Arrow interface.
 */
public class AggregationArrow extends Group implements Arrow {
    private final Line line;

    /**
     * AggregationArrow constructor.
     *
     * @param color color of the arrow - white for aggregation,
     *              black for composition arrow
     */
    public AggregationArrow(Color color, double sX, double sY, double eX, double eY) {
        this(new Line(sX + 50, sY + 100, eX + 50, eY + 100), new Rectangle(15, 15, color));
    }

    /**
     * AggregationArrow constructor.
     *
     * @param line main arrow line
     * @param rec the arrow head
     */
    private AggregationArrow(Line line, Rectangle rec) {
        super(line, rec);
        this.line = line;

        rec.setRotate(45);
        rec.setStroke(Color.BLACK);
        rec.setStrokeWidth(2);

        InvalidationListener updater = o -> {
            double ex = getEndX();
            double ey = getEndY();

            rec.setX(ex - 7);
            rec.setY(ey - 7);
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
        line.setEndY(value + 15);
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