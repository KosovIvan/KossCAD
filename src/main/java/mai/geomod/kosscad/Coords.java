package mai.geomod.kosscad;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Coords {
    private final Line lineVert;
    private final Line lineHor;
    private final MyPoint point;

    public Coords(double x, double y) {
        lineVert = new Line();
        lineHor = new Line();
        point = new MyPoint(x, y);
        lineVert.setStroke(Color.GREEN);
        lineHor.setStroke(Color.RED);
        point.setColor(Color.YELLOW);
    }

    public Line getLineVert() {
        return lineVert;
    }

    public Line getLineHor() {
        return lineHor;
    }

    public MyPoint getPoint() {
        return point;
    }

    public void setCoords(double x, double y) {
        point.setX(x);
        point.setY(y);
        lineVert.setStartX(x);
        lineVert.setStartY(y);
        lineHor.setStartX(x);
        lineHor.setStartY(y);
    }
}
