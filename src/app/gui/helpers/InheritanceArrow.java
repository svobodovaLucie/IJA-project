package app.gui.helpers;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

// https://stackoverflow.com/questions/41353685/how-to-draw-arrow-javafx-pane
public class InheritanceArrow extends Group implements Arrow {

    private final Line line;

    public InheritanceArrow(double sX, double sY, double eX, double eY) {
        this(new Line(sX, sY, eX, eY), new Polygon(), sX, sY);
    }

    private InheritanceArrow(Line line, Polygon triangle, double sX, double sY) {
        super(line, triangle);
        this.line = line;
        triangle.setStroke(Color.BLACK);
        triangle.setFill(Color.WHITE);
        triangle.setStrokeWidth(2);

        sX = getStartX() + 27;
        sY = getStartY() - 27;

        triangle.getPoints().setAll(
                (sX-20.0), (sY-10.0),
                (sX+0.0), (sY+0.0),
                (sX-20.0), (sY+10.0)
        );

        triangle.setRotate(90);

        InvalidationListener updater = o -> {
            double posX = getEndX();
            double posY = getEndY();

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

    // start/end properties

    public final void setStartX(double value) {
        line.setStartX(value);
    }

    public final double getStartX() {
        return line.getStartX();
    }

    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    public final void setStartY(double value) {
        line.setStartY(value);
    }

    public final double getStartY() {
        return line.getStartY();
    }

    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    public void setEndX(double value) {
        line.setEndX(value);
    }

    public final double getEndX() {
        return line.getEndX();
    }

    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    public void setEndY(double value) {
        line.setEndY(value);
    }

    public final double getEndY() {
        return line.getEndY();
    }

    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }
}