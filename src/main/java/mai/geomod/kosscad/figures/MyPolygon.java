package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import mai.geomod.kosscad.modes.DrawingMode;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.List;
import java.util.Map;

import static mai.geomod.kosscad.util.Math.pointsDistance;

public class MyPolygon extends ModifiableFigure {
    private int n;
    private MyLine[] lines;
    private MyPoint[] points;
    private MyPoint center;
    private double R;
    private double r;
    private static long counter = 0;

    private void init(MyPoint center, int n) {
        this.center = center;
        this.n = n;
        lines = new MyLine[n];
        points = new MyPoint[n];
    }

    public MyPolygon(MyPoint center, double radius, int n, DrawingMode mode) {
        id = ++counter;
        if (mode == DrawingMode.INSCRIBED_IN_CIRCLE)
            this.R = radius;
        else if (mode == DrawingMode.CIRCUMSCRIBED_AROUND_CIRCLE)
            this.r = radius;

        init(center, n);
        build(mode, null);
    }

    public MyPolygon(MyPoint center, MyPoint vertex, int n, DrawingMode mode) {
        id = ++counter;
        init(center, n);
        build(mode, vertex);
    }

    private void build(DrawingMode mode, MyPoint vertex) {
        double extraAngle = 0;

        switch (mode) {
            case INSCRIBED_IN_CIRCLE:
                if (R == 0) {
                    R = pointsDistance(center, vertex);
                    points[0] = vertex;
                    extraAngle = Math.toDegrees(Math.acos((center.getY() - vertex.getY()) / R));
                } else if (vertex == null)
                    points[0] = new MyPoint(center.getX(), center.getY() - R);
                r = R * Math.cos(Math.PI / n);
                break;

            case CIRCUMSCRIBED_AROUND_CIRCLE:
                if (r == 0) {
                    r = pointsDistance(center, vertex);
                    R = r / Math.cos(Math.PI / n);
                    extraAngle = Math.toDegrees(Math.acos((center.getY() - vertex.getY()) / r));
                } else if (vertex == null) {
                    R = r / Math.cos(Math.PI / n);
                    points[0] = new MyPoint(center.getX(), center.getY() - R);
                }
        }

        double sideLength = 2 * R * Math.sin(Math.PI / n);
        double sidesAngle = 180.0 * (n - 2) / n;

        if (vertex != null && vertex.getX() < center.getX())
            extraAngle = 360 - extraAngle;

        if (mode == DrawingMode.CIRCUMSCRIBED_AROUND_CIRCLE && vertex != null) {
            double x = vertex.getX() + sideLength / 2 * Math.cos(Math.toRadians(extraAngle));
            double y = vertex.getY() + sideLength / 2 * Math.sin(Math.toRadians(extraAngle));
            points[0] = new MyPoint(x, y);

            extraAngle += 180.0 / n;
        }

        for (int i = 0; i < n - 1; i++) {
            lines[i] = new MyLine(points[i], (sidesAngle / 2 - 90) - i * (180 - sidesAngle) - extraAngle, sideLength);
            points[i + 1] = lines[i].getEndPoint();
        }
        lines[n - 1] = new MyLine(points[n - 1], points[0]);

        getChildren().addAll(lines);
    }

    @Override
    public void Draw(WorkSpace space) {
        space.getWorkSpace().getChildren().add(this);
    }

    @Override
    public void Move(double deltaX, double deltaY) {
        for (MyPoint point : points)
            point.Move(deltaX, deltaY);
        center.Move(deltaX, deltaY);
        updateLines();
    }

    @Override
    public void Scale(double scale, double x, double y) {
        for (MyPoint point : points)
            point.Scale(scale, x, y);
        center.Scale(scale, x, y);
        updateLines();
        R *= scale;
        r = R * Math.cos(Math.PI / n);
    }

    @Override
    public void Remove(WorkSpace space) {
        space.getWorkSpace().getChildren().remove(this);
    }

    @Override
    public boolean isHover(double x, double y) {
        for (MyLine line : lines)
            if (line.isHover(x, y))
                return true;
        return false;
    }

    private void updateLines() {
        for (int i = 0; i < n - 1; i++) lines[i].setPoints(points[i], points[i + 1]);
        lines[n - 1].setPoints(points[n - 1], points[0]);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        for (MyLine line : lines)
            line.setColor(color);
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

    private void setCenter(double x, double y) {
        double deltaX = x - this.center.getX();
        double deltaY = y - this.center.getY();
        Move(deltaX, deltaY);
    }

    private void setRadius(double R) {
        double scaleCoef = R / this.R;
        Scale(scaleCoef, center.getX(), center.getY());
        this.R = R;
        r = R * Math.cos(Math.PI / n);
    }

    @Override
    public void setValuesFromInputs(List<Double> values, MyPoint coordsCenter) {
        setCenter(values.get(0) + coordsCenter.getX(), coordsCenter.getY() - values.get(1));
        double R = values.get(2);
        setRadius(R);
    }

    @Override
    public Map<String, Double> getValuesForOutput(MyPoint center) {
        return getCenterRadiusForOutput(center, this.center, R);
    }

    @Override
    public String getName() {
        return "МНОГОУГОЛЬНИК_" + id;
    }
}
