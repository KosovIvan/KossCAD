package mai.geomod.kosscad;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MyLine extends Figure
{
    MyPoint startPoint;
    MyPoint endPoint;

    public MyLine() {}
    public MyLine(MyPoint startPoint, MyPoint endPoint, Color color, int thickness) {
        super(color, thickness);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public MyPoint getStartPoint() {
        return startPoint;
    }
    public void setStartPoint(MyPoint startPoint) {
        this.startPoint = startPoint;
    }

    public MyPoint getEndPoint() {
        return endPoint;
    }
    public void setEndPoint(MyPoint endPoint) {
        this.endPoint = endPoint;
    }

    public Line Build() {
        Line line = new Line();
        line.setStartX(startPoint.getX());
        line.setStartY(startPoint.getY());
        line.setEndX(endPoint.getX());
        line.setEndY(endPoint.getY());
        line.setStroke(color);
        line.setStrokeWidth(thickness);
        return line;
    }
}
