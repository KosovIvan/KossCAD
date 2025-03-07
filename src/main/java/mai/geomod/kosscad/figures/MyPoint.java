package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static mai.geomod.kosscad.util.Math.pointsDistance;

public class MyPoint extends ModifiableFigure {
    private double x;
    private double y;
    private final Circle circle;

    public MyPoint() {
        this(0, 0);
    }
    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
        circle = new Circle(x, y, thickness + 1.5);
        circle.setFill(color);
        getChildren().add(circle);
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
        circle.setCenterX(x);
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
        circle.setCenterY(y);
    }

    public void setCoords(double x, double y) {
        this.x = x;
        this.y = y;
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        circle.setFill(color);
    }

    @Override
    public void setThickness(double thickness) {
        super.setThickness(thickness);
        circle.setRadius(thickness);
    }

    @Override
    public String getName() {
        return "ТОЧКА";
    }

    @Override
    public boolean isHover(double x, double y) {
        return Math.abs(this.x - x) < 3 && Math.abs(this.y - y) < 3;
    }

    @Override
    public void Draw(WorkSpace space) {
        space.getWorkSpace().getChildren().add(this);
    }

    @Override
    public void Move(double xDelta, double yDelta) {
        setX(x + xDelta);
        setY(y + yDelta);
    }

    @Override
    public void Scale(double scale, double cursorX, double cursorY) {
        double xDif = x - cursorX;
        double yDif = cursorY - y;
        setX(x + xDif * scale - xDif);
        setY(y - yDif * scale + yDif);
    }

    @Override
    public void Rotate(MyPoint centralPoint, double angle) {
        double distance = pointsDistance(this, centralPoint);
        if (distance == 0)
            return;
        double extraAngle = Math.toDegrees(Math.asin((centralPoint.getY() - y) / distance));
        if (x < centralPoint.getX())
            extraAngle = 180 - extraAngle;

        double x = distance * Math.cos(Math.toRadians(angle + extraAngle));
        double y = distance * Math.sin(Math.toRadians(angle + extraAngle));
        setX(centralPoint.getX() + x);
        setY(centralPoint.getY() - y);
    }

    @Override
    public void Remove(WorkSpace space) {}

    @Override
    public void setValuesFromInputs(List<Double> values, MyPoint center) {
        setX(values.get(0) + center.getX());
        setY(center.getY() - values.get(1));
    }

    @Override
    public Map<String, Double> getValuesForOutput(MyPoint center) {
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("X", x - center.getX());
        map.put("Y", center.getY() - y);
        return map;
    }
}