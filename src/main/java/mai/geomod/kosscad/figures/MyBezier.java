package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyBezier extends MySpline {
    private final List<MyPoint> controlPoints = new ArrayList<>();
    private final List<CubicCurve> splines = new ArrayList<>();
    private static long counter = 0;

    public MyBezier() {
        id = ++counter;
    }

    @Override
    public void Update() {
        splines.clear();
        lines.clear();
        getChildren().clear();

        if (points.size() == 1)
            controlPoints.add(new MyPoint(points.getLast().getX() - 50, points.getLast().getY()));
        else if (points.size() * 2 > controlPoints.size()) {
            controlPoints.add(new MyPoint(points.getLast().getX() + 50, points.getLast().getY()));
            controlPoints.add(new MyPoint(points.getLast().getX() - 50, points.getLast().getY()));
        }

        for (int i = 1; i < points.size(); i++) {
            MyPoint p0 = points.get(i - 1);
            MyPoint p1 = controlPoints.get(2 * i - 2);
            MyPoint p2 = controlPoints.get(2 * i - 1);
            MyPoint p3 = points.get(i);
            MyLine line1 = new MyLine(p0, p1);
            MyLine line2 = new MyLine(p3, p2);
            line1.setColor(Color.BLUE);
            line2.setColor(Color.BLUE);
            lines.add(line1);
            lines.add(line2);

            CubicCurve spline = new CubicCurve();
            spline.setStartX(p0.getX());
            spline.setStartY(p0.getY());
            spline.setControlX1(p1.getX());
            spline.setControlY1(p1.getY());
            spline.setControlX2(p2.getX());
            spline.setControlY2(p2.getY());
            spline.setEndX(p3.getX());
            spline.setEndY(p3.getY());
            splines.add(spline);

            setColor(color);
            setThickness(thickness);
            setLinePattern(linePattern);
            getChildren().addAll(spline, line1, line2);
            if (!getChildren().contains(p0)) getChildren().add(p0);
            if (!getChildren().contains(p1)) getChildren().add(p1);
            if (!getChildren().contains(p2)) getChildren().add(p2);
            if (!getChildren().contains(p3)) getChildren().add(p3);
        }
    }

    @Override
    public Optional<MyPoint> getSelectedPoint(double x, double y) {
        return controlPoints.stream().filter(p -> p.isHover(x, y)).findFirst();
    }

    @Override
    public void Draw(WorkSpace space) { space.getWorkSpace().getChildren().add(this); }

    @Override
    public void Move(double deltaX, double deltaY) {
        points.forEach(p -> p.Move(deltaX, deltaY));
        controlPoints.forEach(p -> p.Move(deltaX, deltaY));
        Update();
    }

    @Override
    public void Scale(double scale, double x, double y) {
        points.forEach(p -> p.Scale(scale, x, y));
        controlPoints.forEach(p -> p.Scale(scale, x, y));
        Update();
    }

    @Override
    public void Rotate(MyPoint centralPoint, double angle) {
        points.forEach(p -> p.Rotate(centralPoint, angle));
        controlPoints.forEach(p -> p.Rotate(centralPoint, angle));
        Update();
    }

    @Override
    public void Remove(WorkSpace space) {
        space.getWorkSpace().getChildren().remove(this);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        splines.forEach(e -> {
            e.setStroke(color);
            e.setFill(null);
        });
    }

    @Override
    public void setThickness(double thickness) {
        super.setThickness(thickness);
        splines.forEach(e -> e.setStrokeWidth(thickness));
    }

    @Override
    public void setLineType(LineType lineType, double scale) {
        super.setLineType(lineType, scale);
        setLinePattern(linePattern);
    }

    @Override
    protected void setLinePattern(List<Double> pattern) {
        super.setLinePattern(pattern);
        splines.forEach(e -> {
            e.getStrokeDashArray().clear();
            e.getStrokeDashArray().addAll(pattern);
        });
    }

    @Override
    public boolean isHover(double x, double y) {
        for (int i = 0; i <= points.size() - 2; i++)
            if (new MyLine(points.get(i), points.get(i + 1)).isHover(x, y))
                return true;
        return controlPoints.stream().anyMatch(p -> p.isHover(x, y));
    }

    @Override
    public String getName() {
        return "БЕЗЬЕ_" + id;
    }
}