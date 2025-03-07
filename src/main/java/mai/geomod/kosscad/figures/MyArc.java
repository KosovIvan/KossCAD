package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static mai.geomod.kosscad.util.Math.*;

public class MyArc extends ModifiableFigure{
    private MyPoint center;
    private double radius;
    private MyPoint startPoint;
    private MyPoint endPoint;
    private Arc arc;
    private static long counter = 0;

    public MyArc(MyPoint point1, MyPoint point2, MyPoint point3) {
        id = ++counter;
        startPoint = point1;
        endPoint = point3;
        double[] values = getCenterAndRadius(point1, point2, point3);
        center = new MyPoint(values[0], values[1]);
        radius = values[2];
        Build(point1, point2, point3);
    }

    public MyArc(MyPoint center, MyLine chord) {
        id = ++counter;
        this.center = center;
        startPoint = chord.getStartPoint();
        radius = pointsDistance(center, startPoint);
        Build(chord);
    }

    private void Build(MyPoint point1, MyPoint point2, MyPoint point3) {
        double cx = center.getX();
        double cy = center.getY();

        double angle1 = Math.toDegrees(Math.acos((point1.getX() - cx) / radius));
        double angle2 = Math.toDegrees(Math.acos((point2.getX() - cx) / radius));
        double angle3 = Math.toDegrees(Math.acos((point3.getX() - cx) / radius));

        if (cy < point1.getY())
            angle1 = 360 - angle1;
        if (cy < point2.getY())
            angle2 = 360 - angle2;
        if (cy < point3.getY())
            angle3 = 360 - angle3;

        double arcAngle = (angle3 - angle1 + 360) % 360;

        boolean isClockwiseNormalCase = angle1 < angle3 && !(angle1 < angle2 && angle2 < angle3);
        boolean isClockwiseCrossZero = angle1 > angle3 && !(angle1 < angle2 || angle2 < angle3);

        if (isClockwiseNormalCase || isClockwiseCrossZero)
            arcAngle -= 360;

        arc = new Arc(cx, cy, radius, radius, angle1, arcAngle);
        arc.setStrokeWidth(thickness);
        setColor(color);
        getChildren().addAll(arc, point1, point3);
    }

    private void Build(MyLine chord) {
        double cx = center.getX();
        double cy = center.getY();
        List<MyPoint> intersections = findLineCircleIntersections(center, radius, chord.getStartPoint(), chord.getEndPoint());
        if (intersections.size() == 2) {
            if (Math.abs(startPoint.getX() - intersections.get(0).getX()) < 0.0001 && Math.abs(startPoint.getY() - intersections.get(0).getY()) < 0.0001)
                endPoint = intersections.get(1);
            else
                endPoint = intersections.get(0);
        }
        if (intersections.size() == 1)
            endPoint = intersections.get(0);

        double startAngle = Math.toDegrees(Math.acos((startPoint.getX() - cx) / radius));
        if (cy < startPoint.getY())
            startAngle = 360 - startAngle;

        double scalar = (startPoint.getX() - cx) * (endPoint.getX() - cx) + (startPoint.getY() - cy) * (endPoint.getY() - cy);
        double arcAngle = Math.toDegrees(Math.acos(scalar / (radius * radius)));

        double cross = (startPoint.getX() - cx) * (cy - endPoint.getY()) - (cy - startPoint.getY()) * (endPoint.getX() - cx);
        if (cross < 0)
            arcAngle *= -1;

        arc = new Arc(cx, cy, radius, radius, startAngle, arcAngle);
        arc.setStrokeWidth(thickness);
        setColor(color);
        getChildren().addAll(arc, startPoint, endPoint);
    }

    @Override
    public void Draw(WorkSpace space) {
        space.getWorkSpace().getChildren().add(this);
    }

    @Override
    public void Move(double deltaX, double deltaY) {
        startPoint.Move(deltaX, deltaY);
        endPoint.Move(deltaX, deltaY);
        center.Move(deltaX, deltaY);
        setCenter(center);
    }

    @Override
    public void Scale(double scale, double x, double y) {
        startPoint.Scale(scale, x, y);
        endPoint.Scale(scale, x, y);
        center.Scale(scale, x, y);
        radius *= scale;
        setCenter(center);
        setRadius(radius);
    }

    @Override
    public void Rotate(MyPoint centralPoint, double angle) {
        startPoint.Rotate(centralPoint, angle);
        endPoint.Rotate(centralPoint, angle);
        center.Rotate(centralPoint, angle);
        setCenter(center);
        arc.setStartAngle(arc.getStartAngle() + angle);
    }

    @Override
    public void Remove(WorkSpace space){
        space.getWorkSpace().getChildren().removeAll(startPoint, endPoint, this);
    }

    @Override
    public String getName() { return "ДУГА_" + id; }

    @Override
    public boolean isHover(double x, double y) {
        double eps = 5;
        double distance = pointsDistance(center, new MyPoint(x, y));
        return Math.abs(distance - radius) < eps;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        arc.setStroke(color);
        startPoint.setColor(color);
        endPoint.setColor(color);
        arc.setFill(null);
    }

    @Override
    public void setThickness(double thickness) {
        super.setThickness(thickness);
        arc.setStrokeWidth(thickness);
        startPoint.setThickness(thickness + 1);
        endPoint.setThickness(thickness + 1);
    }

    @Override
    public void setLineType(LineType lineType, double scale) {
        super.setLineType(lineType, scale);
        arc.getStrokeDashArray().clear();
        arc.getStrokeDashArray().addAll(lineType.getPattern(scale));
    }

    private void setCenter(MyPoint center) {
        arc.setCenterX(center.getX());
        arc.setCenterY(center.getY());
    }

    private void setRadius(double radius) {
        arc.setRadiusX(radius);
        arc.setRadiusY(radius);
    }

    @Override
    public void setValuesFromInputs(List<Double> values, MyPoint coordsCenter) {
        double deltaX = values.get(0) + coordsCenter.getX() - this.center.getX();
        double deltaY = coordsCenter.getY() - values.get(1) - this.center.getY();
        double radius = values.get(2);
        Scale(radius / this.radius, this.center.getX(), this.center.getY());
        Move(deltaX, deltaY);
        this.radius = radius;
    }

    @Override
    public Map<String, Double> getValuesForOutput(MyPoint center) {
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("Центр [X]", this.center.getX() - center.getX());
        map.put("Центр [Y]", center.getY() - this.center.getY());
        map.put("Радиус", radius);
        return map;
    }
}
