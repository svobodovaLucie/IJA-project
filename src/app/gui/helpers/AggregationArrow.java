package app.gui.helpers;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

// https://stackoverflow.com/questions/41353685/how-to-draw-arrow-javafx-pane
public class AggregationArrow extends Group implements Arrow{

    private final Line line;

    public AggregationArrow(Color color) {
        this(new Line(), new Rectangle(15, 15, color));
    }

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

    // start/end properties

    public final void setStartX(double value) {
        line.setStartX(value);
    }
    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    public final void setStartY(double value) {
        line.setStartY(value);
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
        line.setEndY(value + 15);
    }

    public final double getEndY() {
        return line.getEndY();
    }

    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }
}