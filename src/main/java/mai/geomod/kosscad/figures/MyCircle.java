package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.Math;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.List;
import java.util.Map;

import static mai.geomod.kosscad.util.Math.PointsDistance;

public class MyCircle extends ModifiableFigure{
    private MyPoint cPoint;
    private double r;
    private Circle circle;
    private static long counter = 0;

    public MyCircle(MyPoint cPoint, MyPoint vertex) {
        id = ++counter;
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
        r *= scale;
        circle.setRadius(r);
    }

    public void setCenter(MyPoint center) {
        cPoint = center;
        circle.setCenterX(center.getX());
        circle.setCenterY(center.getY());
    }

    public void setRadius(double r) {
        this.r = r;
        circle.setRadius(r);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        cPoint.setColor(color);
        circle.setStroke(color);
    }

    @Override
    public void setThickness(double thickness) {
        super.setThickness(thickness);
        cPoint.setThickness(thickness);
        circle.setStrokeWidth(thickness);
    }

    @Override
    public void setLineType(LineType lineType, double scale) {
        super.setLineType(lineType, scale);
        circle.getStrokeDashArray().clear();
        circle.getStrokeDashArray().addAll(lineType.getPattern(scale));
    }

    @Override
    public String getName() {
        return "КРУГ_" + id;
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

    @Override
    public void setValuesFromInputs(List<Double> values, MyPoint center) {
        double x = values.get(0) + center.getX();
        double y = center.getY() - values.get(1);
        setCenter(new MyPoint(x, y));
        setRadius(values.get(2));
    }

    @Override
    public Map<String, Double> getValuesForOutput(MyPoint center) {
        return getCenterRadiusForOutput(center, this.cPoint, r);
    }
}