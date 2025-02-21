package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import mai.geomod.kosscad.util.WorkSpace;

public class MyLine extends Figure {
    private final MyPoint startPoint;
    private final MyPoint endPoint;
    private Line line;

    public MyLine() {
        this(new MyPoint(), new MyPoint());
    }
    public MyLine(MyPoint startPoint, MyPoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        line = Build();
        getChildren().add(line);
    }
    public MyLine(MyPoint startPoint, MyPoint endPoint, Color color, int thickness) {
        super(color, thickness);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        line = Build();
        getChildren().add(line);
    }

    public MyPoint getStartPoint() {
        return startPoint;
    }

    public MyPoint getEndPoint() {
        return endPoint;
    }

    public Line Build() {
        Line new_line = new Line();
        new_line.setStartX(startPoint.getX());
        new_line.setStartY(startPoint.getY());
        new_line.setEndX(endPoint.getX());
        new_line.setEndY(endPoint.getY());
        new_line.setStroke(color);
        new_line.setStrokeWidth(thickness);
        return new_line;
    }

    public void setCoords() {
        line.setStartX(startPoint.getX());
        line.setStartY(startPoint.getY());
        line.setEndX(endPoint.getX());
        line.setEndY(endPoint.getY());
    }

    @Override
    public void Draw(WorkSpace space) {
        space.getWorkSpace().getChildren().add(this);
    }

    @Override
    public void Move(double xDelta, double yDelta) {
        setCoords();
    }

    @Override
    public void Scale(double scale, double cursorX, double cursorY) {
        setCoords();
    }
}