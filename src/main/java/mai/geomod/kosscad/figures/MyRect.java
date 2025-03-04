package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static mai.geomod.kosscad.util.Math.getMiddlePoint;

public class MyRect extends ModifiableFigure {
    private final MyPoint[] points = new MyPoint[4];
    private final MyLine[] lines = new MyLine[4];
    private double width;
    private double height;
    private final MyPoint center;
    private static long counter = 0;

    public MyRect() {
        this(new MyPoint(), new MyPoint());
    }

    public MyRect(MyPoint point1, MyPoint point2) {
        id = ++counter;
        MyPoint point3 = new MyPoint(point1.getX(), point2.getY());
        MyPoint point4 = new MyPoint(point2.getX(), point1.getY());
        width = Math.abs(point2.getX() - point1.getX());
        height = Math.abs(point2.getY() - point1.getY());
        double x = point1.getX() + (point3.getX() - point1.getX()) / 2;
        double y = point3.getY() + (point1.getY() - point3.getY()) / 2;
        center = new MyPoint(x, y);
        Build(point1, point2, point3, point4);
    }

    public MyRect(MyPoint center, double width, double height) {
        id = ++counter;
        MyPoint point1 = new MyPoint(center.getX() - width / 2, center.getY() - height / 2);
        MyPoint point3 = new MyPoint(center.getX() - width / 2, center.getY() + height / 2);
        MyPoint point2 = new MyPoint(center.getX() + width / 2, center.getY() + height / 2);
        MyPoint point4 = new MyPoint(center.getX() + width / 2, center.getY() - height / 2);
        this.width = width;
        this.height = height;
        this.center = center;
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

    public MyPoint[] get2OtherPoints() {
        return new MyPoint[]{points[1], points[3]};
    }
    public MyPoint[] getAllPoints() {
        return points;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public MyPoint getCenter() {
        return center;
    }

    public void setCenter(double x, double y) {
        double deltaX = x - this.center.getX();
        double deltaY = y - this.center.getY();
        for (MyPoint p : points) p.Move(deltaX, deltaY);
        Move(deltaX, deltaY);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        for (MyLine line : lines) line.setColor(color);
    }

    @Override
    public void setThickness(double thickness) {
        super.setThickness(thickness);
        for (MyLine line : lines)
            line.setThickness(thickness);
    }

    @Override
    public void setLineType(LineType lineType, double scale) {
        super.setLineType(lineType, scale);
        for (MyLine line : lines)
            line.setLineType(lineType, scale);
    }

    @Override
    public String getName() {
        return "ПРЯМОУГОЛЬНИК_" + id;
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
        center.Move(xDelta, yDelta);
        setCoords();
    }

    @Override
    public void Scale(double scale, double cursorX, double cursorY) {
        center.Scale(scale, cursorX, cursorY);
        width *= scale;
        height *= scale;
        setCoords();
    }

    private void setWidth(double width) {
        double scale = width / this.width;
        this.width = width;
        MyPoint middlePoint1 = getMiddlePoint(points[0], points[3]);
        MyPoint middlePoint2 = getMiddlePoint(points[1], points[2]);
        calculateCoords(scale, middlePoint1, middlePoint2, middlePoint2.getX(), middlePoint2.getY(), middlePoint1.getX(), middlePoint1.getY());
    }

    private void setHeight(double height) {
        double scale = height / this.height;
        this.height = height;
        MyPoint middlePoint1 = getMiddlePoint(points[0], points[1]);
        MyPoint middlePoint2 = getMiddlePoint(points[2], points[3]);
        calculateCoords(scale, middlePoint1, middlePoint2, middlePoint1.getX(), middlePoint1.getY(), middlePoint2.getX(), middlePoint2.getY());
    }

    private void calculateCoords(double scale, MyPoint middlePoint1, MyPoint middlePoint2, double x, double y, double x4, double y4) {
        double x0 = (points[0].getX() - middlePoint1.getX()) * scale + middlePoint1.getX();
        double y0 = (points[0].getY() - middlePoint1.getY()) * scale + middlePoint1.getY();
        double x1 = (points[1].getX() - x) * scale + x;
        double y1 = (points[1].getY() - y) * scale + y;
        double x2 = (points[2].getX() - middlePoint2.getX()) * scale + middlePoint2.getX();
        double y2 = (points[2].getY() - middlePoint2.getY()) * scale + middlePoint2.getY();
        double x3 = (points[3].getX() - x4) * scale + x4;
        double y3 = (points[3].getY() - y4) * scale + y4;

        points[0].setCoords(x0, y0);
        points[1].setCoords(x1, y1);
        points[2].setCoords(x2, y2);
        points[3].setCoords(x3, y3);
    }

    @Override
    public void setValuesFromInputs(List<Double> values, MyPoint cPoint) {
        setCenter(values.get(0) + cPoint.getX(), cPoint.getY() - values.get(1));
        setWidth(values.get(2));
        setHeight(values.get(3));
        setCoords();
    }

    @Override
    public Map<String, Double> getValuesForOutput(MyPoint center) {
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("Центр X", this.center.getX() - center.getX());
        map.put("Центр Y", center.getY() - this.center.getY());
        map.put("Ширина", width);
        map.put("Высота", height);
        return map;
    }
}
