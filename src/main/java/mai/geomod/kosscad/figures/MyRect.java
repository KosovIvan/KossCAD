package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public MyPoint[] getOtherPoints() {
        return new MyPoint[]{points[1], points[3]};
    }

    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public MyPoint getCenter() {
        return center;
    }

    public void setCenter(double x, double y) {
        double deltaX = x - this.center.getX();
        double deltaY = y - this.center.getY();
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
        setCoords();
    }

    @Override
    public void Scale(double scale, double cursorX, double cursorY) {
        setCoords();
    }

    @Override
    public void setValuesFromInputs(List<Double> values, MyPoint cPoint) {
        setCenter(values.get(0) + cPoint.getX(), cPoint.getY() - values.get(1));
        setWidth(values.get(2));
        setHeight(values.get(3));
    }

    @Override
    public Map<String, Double> getValuesForOutput(MyPoint center) {
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("Центр [X]", this.center.getX() - center.getX());
        map.put("Центр [Y]", center.getY() - this.center.getY());
        map.put("Ширина", width);
        map.put("Высота", height);
        return map;
    }
}
