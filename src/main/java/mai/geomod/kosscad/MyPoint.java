package mai.geomod.kosscad;

import javafx.scene.shape.Circle;

public class MyPoint extends Figure{
    private double x;
    private double y;
    private Circle circle;

    public MyPoint() {}
    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
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
}
