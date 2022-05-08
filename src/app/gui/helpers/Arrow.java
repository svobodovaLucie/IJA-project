/*
 * File:         Arrow.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the Arrow interface.
 */
package app.gui.helpers;

/**
 * Interface represents the arrow type. Various types of arrows
 * implements this interface.
 */
public interface Arrow {
    /**
     * Method sets the startX property.
     *
     * @param value X position to be set
     */
    void setStartX(double value);

    /**
     * Method sets the startY property.
     *
     * @param value Y position to be set
     */
    void setStartY(double value);

    /**
     * Method sets the endX property.
     *
     * @param value X position to be set
     */
    void setEndX(double value);

    /**
     * Method sets the endY property.
     *
     * @param value Y position to be set
     */
    void setEndY(double value);
}
