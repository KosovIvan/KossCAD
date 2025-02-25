package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import mai.geomod.kosscad.util.Math;
import mai.geomod.kosscad.util.WorkSpace;

import static mai.geomod.kosscad.util.Math.PointsDistance;

public class MyCircle extends Figure{
    private final MyPoint cPoint;
    private double r;
    private Circle circle;

    public MyCircle(MyPoint cPoint, MyPoint vertex) {
        this.cPoint = cPoint;
        this.r = PointsDistance(cPoint, vertex);
        Build();
        getChildren().add(circle);
    }

    private void Build() {
        circle = new Circle();
        circle.setCenterX(cPoint.getX());
        circle.setCenterY(cPoint.getY());
        circle.setRadius(r);
        circle.setStrokeWidth(thickness);
        circle.setStroke(color);
        circle.setFill(null);
    }

    public void setCoords() {
        circle.setCenterX(cPoint.getX());
        circle.setCenterY(cPoint.getY());
    }

    public void setCoords(double scale) {
        setCoords();
        scale = (scale == 0.9) ? 1.1 : 0.9;
        r *= scale;
        circle.setRadius(r);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        cPoint.setColor(color);
        circle.setStroke(color);
    }

    @Override
    public boolean isHover(double x, double y) {
        double eps = 5;
        double distance = PointsDistance(cPoint, new MyPoint(x, y));
        return java.lang.Math.abs(distance - r) < eps;
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
        setCoords(scale);
    }
}