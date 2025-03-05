package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static mai.geomod.kosscad.util.Math.PointsDistance;

public class MyLine extends ModifiableFigure {
    private final MyPoint startPoint;
    private final MyPoint endPoint;
    private Line line;
    private static long counter = 0;

    public MyLine() {
        this(new MyPoint(), new MyPoint());
    }
    public MyLine(MyPoint startPoint, MyPoint endPoint) {
        id = ++counter;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        Build();
    }
    public MyLine(MyPoint point1, double angle, double length) {
        id = ++counter;
        double x = point1.getX() + Math.cos(Math.toRadians(-angle)) * length;
        double y = point1.getY() + Math.sin(Math.toRadians(-angle)) * length;
        MyPoint point2 = new MyPoint(x, y);
        this.startPoint = point1;
        this.endPoint = point2;
        Build();
    }

    public MyPoint getStartPoint() {
        return startPoint;
    }

    public MyPoint getEndPoint() {
        return endPoint;
    }

    private void Build() {
        line = new Line();
        line.setStartX(startPoint.getX());
        line.setStartY(startPoint.getY());
        line.setEndX(endPoint.getX());
        line.setEndY(endPoint.getY());
        line.setStroke(color);
        line.setStrokeWidth(thickness);
        getChildren().add(line);
    }

    public void setPoints(MyPoint point1, MyPoint point2) {
        startPoint.setX(point1.getX());
        startPoint.setY(point1.getY());
        endPoint.setX(point2.getX());
        endPoint.setY(point2.getY());
        setCoords();
    }

    public void setCoords() {
        line.setStartX(startPoint.getX());
        line.setStartY(startPoint.getY());
        line.setEndX(endPoint.getX());
        line.setEndY(endPoint.getY());
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        startPoint.setColor(color);
        endPoint.setColor(color);
        line.setStroke(color);
    }

    @Override
    public void setThickness(double thickness) {
        super.setThickness(thickness);
        line.setStrokeWidth(thickness);
        startPoint.setThickness(thickness + 1);
        endPoint.setThickness(thickness + 1);
    }

    @Override
    public void setLineType(LineType lineType, double scale) {
        super.setLineType(lineType, scale);
        line.getStrokeDashArray().clear();
        line.getStrokeDashArray().addAll(lineType.getPattern(scale));
    }

    @Override
    public boolean isHover(double x, double y) {
        double x1 = startPoint.getX();
        double y1 = startPoint.getY();
        double x2 = endPoint.getX();
        double y2 = endPoint.getY();
        double eps = 3;
        double distance = 10;

        if (x >= Math.min(x1, x2) - eps && x <= Math.max(x1, x2) + eps && y >= Math.min(y1, y2) - eps && y <= Math.max(y1, y2) + eps) {
            double a = PointsDistance(startPoint, endPoint);
            double b = PointsDistance(startPoint, new MyPoint(x, y));
            double scalar = (x2 - x1) * (x - x1) + (y2 - y1) * (y - y1);
            double cos = scalar / (a * b);
            distance = b * Math.sqrt(1 - cos * cos);
        }

        return distance < eps;
    }

    @Override
    public String getName() {
        return "ЛИНИЯ_" + id;
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

    @Override
    public void setValuesFromInputs(List<Double> values, MyPoint center) {
        startPoint.setX(values.get(0) + center.getX());
        startPoint.setY(center.getY() - values.get(1));
        endPoint.setX(values.get(2) + center.getX());
        endPoint.setY(center.getY() - values.get(3));
        setCoords();
    }

    @Override
    public Map<String, Double> getValuesForOutput(MyPoint center) {
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("X1", startPoint.getX() - center.getX());
        map.put("Y1", center.getY() - startPoint.getY());
        map.put("X2", endPoint.getX() - center.getX());
        map.put("Y2", center.getY() - endPoint.getY());
        return map;
    }
}