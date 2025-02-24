package mai.geomod.kosscad.figures;

import javafx.scene.shape.Circle;
import mai.geomod.kosscad.util.Math;
import mai.geomod.kosscad.util.WorkSpace;

public class MyCircle extends Figure{
    private final MyPoint cPoint;
    private double r;
    private Circle circle;

    public MyCircle(MyPoint cPoint, MyPoint vertex) {
        this.cPoint = cPoint;
        this.r = Math.PointsDistance(cPoint, vertex);
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
    public boolean isHover(double x, double y) {
        return true;
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