package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import mai.geomod.kosscad.util.WorkSpace;

public class MyRect extends Figure {
    private final MyPoint[] points = new MyPoint[4];
    private final MyLine[] lines = new MyLine[4];
    private double width;
    private double height;

    public MyRect() {
        this(new MyPoint(), new MyPoint());
    }

    public MyRect(MyPoint point1, MyPoint point2) {
        MyPoint point3 = new MyPoint(point1.getX(), point2.getY());
        MyPoint point4 = new MyPoint(point2.getX(), point1.getY());
        width = Math.abs(point2.getX() - point1.getX());
        height = Math.abs(point2.getY() - point1.getY());
        Build(point1, point2, point3, point4);
    }

    private void Build(MyPoint point1, MyPoint point2, MyPoint point3, MyPoint point4) {
        points[0] = point1;
        points[1] = point3;
        points[2] = point2;
        points[3] = point4;

        lines[0] = new MyLine(point1, point3);
        lines[1] = new MyLine(point3, point2);
        lines[2] = new MyLine(point1, point4);
        lines[3] = new MyLine(point2, point4);

        getChildren().addAll(lines);
    }

    private void setCoords() {
        for (MyLine i : lines) {
            i.setCoords();
        }
    }

    public MyPoint[] getOtherPoints() {
        return new MyPoint[]{points[1], points[3]};
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        for (MyLine line : lines) line.setColor(color);
    }

    @Override
    public boolean isHover(double x, double y) {
        for (MyLine line : lines) if (line.isHover(x, y)) return true;
        return false;
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
