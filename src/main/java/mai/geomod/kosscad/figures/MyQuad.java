package mai.geomod.kosscad.figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mai.geomod.kosscad.util.Math.middlePoint;

public class MyQuad extends MySpline{
    private final List<QuadCurve> splines = new ArrayList<>();
    private static long counter = 0;

    public MyQuad() {
        id = ++counter;
    }

    @Override
    public void Update() {
        splines.clear();
        lines.clear();
        getChildren().clear();

        if (points.size() > 1) {
            MyLine line = new MyLine(points.getFirst(), points.get(1));
            line.setColor(Color.BLUE);
            lines.add(line);
            getChildren().add(line);
        }

        for (int i = 1; i < points.size() - 1; i++) {
            MyPoint p0 = points.get(i - 1);
            MyPoint p1 = points.get(i);
            MyPoint p2 = points.get(i + 1);

            MyPoint start = middlePoint(p0, p1);
            MyPoint end = middlePoint(p1, p2);

            if (i == 1)
                start = new MyPoint(p0.getX(), p0.getY());
            if (i == points.size() - 2)
                end = new MyPoint(p2.getX(), p2.getY());
            if (i > 1) {
                splines.getLast().setEndX(start.getX());
                splines.getLast().setEndY(start.getY());
            }

            MyLine line = new MyLine(p1, p2);
            line.setColor(Color.BLUE);
            lines.add(line);
            getChildren().add(line);
            if (!getChildren().contains(p1)) getChildren().add(p0);
            if (!getChildren().contains(p1)) getChildren().add(p1);
            if (!getChildren().contains(p2)) getChildren().add(p2);

            QuadCurve spline = new QuadCurve();
            spline.setStartX(start.getX());
            spline.setStartY(start.getY());
            spline.setControlX(p1.getX());
            spline.setControlY(p1.getY());
            spline.setEndX(end.getX());
            spline.setEndY(end.getY());
            splines.add(spline);

            setColor(color);
            setThickness(thickness);
            setLinePattern(linePattern);
            getChildren().add(spline);
        }
    }

    @Override
    public Optional<MyPoint> getSelectedPoint(double x, double y) {
        return points.stream().filter(p -> p.isHover(x, y)).findFirst();
    }

    @Override
    public void Draw(WorkSpace space) { space.getWorkSpace().getChildren().add(this); }

    @Override
    public void Move(double deltaX, double deltaY) {
        points.forEach(p -> p.Move(deltaX, deltaY));
        Update();
    }

    @Override
    public void Scale(double scale, double x, double y) {
        points.forEach(p -> p.Scale(scale, x, y));
        Update();
    }

    @Override
    public void Remove(WorkSpace space) {
        space.getWorkSpace().getChildren().remove(this);
    }

    /*@Override
    public void rotate(MyPoint centralPoint, double angle) {
        points.forEach(p -> p.rotate(centralPoint, angle));
        update();
    }*/

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
        Optional<MyLine> optional = lines.stream().filter(line -> line.isHover(x, y)).findFirst();
        return optional.isPresent();
    }

    @Override
    public String getName() { return "КВАДРАТИЧНЫЙ_СПЛАЙН_" + id; }
}
