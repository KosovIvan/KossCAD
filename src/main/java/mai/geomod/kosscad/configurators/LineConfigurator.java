package mai.geomod.kosscad.configurators;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import mai.geomod.kosscad.figures.MyLine;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.modes.DrawingMode;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.List;

public class LineConfigurator extends BaseConfigurator {
    private MyPoint point;

    public LineConfigurator(WorkSpace space) {
        super(space);
        modes.getItems().addAll(DrawingMode.BY_2_POINTS, DrawingMode.ANGLE_LENGTH);
        modes.setValue(DrawingMode.BY_2_POINTS);
    }

    @Override
    public BaseConfigurator Activate() {
        switch (modes.getValue()) {
            case BY_2_POINTS:
                inputBuilder.setPrompts("Укажите координаты точки 1", "X", "Y");
                setInputHandlers(this::drawPoints);
                break;
            case ANGLE_LENGTH:
                drawAngleLength();
                break;
        }
        return this;
    }

    private void drawPoints(double x, double y) {
        MyPoint point = new MyPoint(x, y);
        points.add(point);
        space.addObject(point);
        point.Draw(space);
        if (points.size() >= 2) {
            MyLine line = new MyLine(points.get(0), points.get(1));
            space.addObject(line);
            line.Draw(space);
            points.clear();
            inputBuilder.setPrompts("Укажите координаты точки 1", "X", "Y");
        }
        else inputBuilder.setPrompts("Укажите координаты точки " + 2, "X", "Y");
    }

    private void drawAngleLength() {
        inputBuilder.setPrompts("Укажите координаты первой точки", "X", "Y");
        space.getInputTool().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                List<Double> inputs = inputBuilder.readInputValues();
                if (point != null) {
                    double angle = inputs.get(0);
                    double length = inputs.get(1) * space.getScale();
                    double x = point.getX() + Math.cos(Math.toRadians(-angle)) * length;
                    double y = point.getY() + Math.sin(Math.toRadians(-angle)) * length;
                    MyPoint point2 = new MyPoint(x, y);
                    points.add(point2);
                    space.addObject(point2);
                    point2.Draw(space);
                    MyLine line = new MyLine(point, point2);
                    space.addObject(line);
                    line.Draw(space);
                    points.clear();
                    point = null;
                    inputBuilder.setPrompts("Укажите координаты первой точки", "X", "Y");
                } else {
                    double x = space.getCoords().getPoint().getX() + inputs.get(0) * space.getScale();
                    double y = space.getCoords().getPoint().getY() - inputs.get(1) * space.getScale();
                    point = new MyPoint(x, y);
                    points.add(point);
                    space.addObject(point);
                    point.Draw(space);
                    inputBuilder.setPrompts("Укажите угол и длину линии", "Угол", "Длина");
                }
            }
        });
        space.getWorkSpace().setOnMouseClicked(null);
    }

    @Override
    public void Cancellation() {
        super.Cancellation();
        space.getWorkSpace().setOnMouseClicked(null);
        point = null;
    }
}