/*
 * File:         AssociationArrow.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the AssociationArrow arrow type.
 */
package app.gui.helpers;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 * Class represents the association arrow. It implements
 * the Arrow interface.
 */
public class AssociationArrow extends Group implements Arrow {
    private final Line line;
    private static final double arrowLength = 20;
    private static final double arrowWidth = 7;

    /**
     * AssociationArrow constructor.
     *
     * @param sX start X property
     * @param sY start Y property
     * @param eX end X property
     * @param eY end Y property
     */
    public AssociationArrow(double sX, double sY, double eX, double eY) {
        this(new Line(sX + 50, sY + 100, eX + 50, eY + 100), new Line(), new Line());
    }

    /**
     * AssociationArrow constructor.
     *
     * @param line main arrow line
     * @param arrow1 arrow part 1
     * @param arrow2 arrow part 2
     */
    private AssociationArrow(Line line, Line arrow1, Line arrow2) {
        super(line, arrow1, arrow2);
        this.line = line;

        InvalidationListener updater = o -> {
            double ex = getEndX();
            double ey = getEndY();
            double sx = getStartX();
            double sy = getStartY();

            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // arrow parts of length 0
                arrow1.setStartX(ex);
                arrow1.setStartY(ey);
                arrow2.setStartX(ex);
                arrow2.setStartY(ey);
            } else {
                double factor = arrowLength / Math.hypot(sx-ex, sy-ey);
                double factorO = arrowWidth / Math.hypot(sx-ex, sy-ey);

                // part in direction of main line
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // part ortogonal to main line
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                arrow1.setStartX(ex + dx - oy);
                arrow1.setStartY(ey + dy + ox);
                arrow2.setStartX(ex + dx + oy);
                arrow2.setStartY(ey + dy - ox);
            }
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
     * Method returns startX property.
     *
     * @return startX property
     */
    public final double getStartX() {
        return line.getStartX();
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
     * Method returns startY property
     *
     * @return startY property
     */
    public final double getStartY() {
        return line.getStartY();
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
    public final void setEndX(double value) {
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
    public final void setEndY(double value) {
        line.setEndY(value + 7);
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