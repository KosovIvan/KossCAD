package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import mai.geomod.kosscad.drawing.LineDrawer;

public class MyLine extends Figure
{
    private final MyPoint startPoint;
    private final MyPoint endPoint;

    public MyLine() {
        this(new MyPoint(), new MyPoint());
    }
    public MyLine(MyPoint startPoint, MyPoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        drawer = new LineDrawer();
    }
    public MyLine(MyPoint startPoint, MyPoint endPoint, Color color, int thickness) {
        super(color, thickness);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        drawer = new LineDrawer();
    }

    public MyPoint getStartPoint() {
        return startPoint;
    }

    public MyPoint getEndPoint() {
        return endPoint;
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
