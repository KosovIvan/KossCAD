package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MyPoint extends Figure {
    private double x;
    private double y;
    private final Circle circle;

    public MyPoint() {
        x = 0;
        y = 0;
        circle = new Circle(0,0, thickness + 1.5);
    }
    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
        circle = new Circle(x, y, thickness + 1.5);
        circle.setFill(color);
        getChildren().add(circle);
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        circle.setFill(color);
    }
}
