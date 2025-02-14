package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MyPoint extends Figure {
    private final double xStart;
    private final double yStart;
    private double x;
    private double y;
    private final Circle circle;

    public MyPoint() {
        this(0, 0);
    }
    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
        xStart = x;
        yStart = y;
        circle = new Circle(x, y, thickness + 1.5);
        circle.setFill(color);
        getChildren().add(circle);
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
        circle.setCenterX(x);
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
        circle.setCenterY(y);
    }

    public double getxStart() {
        return xStart;
    }

    public double getyStart() {
        return yStart;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        circle.setFill(color);
    }
}