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
                drawByAngleAndLength();
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

    private void drawByAngleAndLength() {
        inputBuilder.setPrompts("Укажите координаты первой точки", "X", "Y");
        /*
        toolBar.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                List<Double> inputs = inputBuilder.readInputValues();
                if (firstPoint != null) {
                    double angle = inputs.get(0);
                    double length = inputs.get(1) * drawingContext.getScale();
                    Line line = new Line(firstPoint, angle, length);
                    workspace.getChildren().add(line);
                    firstPoint = null;
                    inputBuilder.setPrompts("Укажите координаты первой точки", "X", "Y");
                } else {
                    double x = coordsCenter.getX() + inputs.get(0) * drawingContext.getScale();
                    double y = coordsCenter.getY() - inputs.get(1) * drawingContext.getScale();
                    firstPoint = new Point(x, y);
                    workspace.getChildren().add(firstPoint);
                    inputBuilder.setPrompts("Укажите угол и длину линии", "Угол", "Длина");
                }
            }
        });
        */
        space.getWorkSpace().setOnMouseClicked(null);
    }

    @Override
    public void Cancellation() {
        super.Cancellation();
        space.getWorkSpace().setOnMouseClicked(null);
    }
}