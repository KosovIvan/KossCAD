package mai.geomod.kosscad.configurators;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import mai.geomod.kosscad.figures.MyCircle;
import mai.geomod.kosscad.figures.MyLine;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.figures.MyRect;
import mai.geomod.kosscad.modes.DrawingMode;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.Arrays;
import java.util.List;

public class CircleConfigurator extends BaseConfigurator{

    public CircleConfigurator(WorkSpace space) {
        super(space);
        modes.getItems().addAll(DrawingMode.RADIUS, DrawingMode.BY_3_POINTS);
        modes.setValue(DrawingMode.RADIUS);
    }

    @Override
    public BaseConfigurator Activate() {
        switch (modes.getValue()) {
            case RADIUS:
                drawRadius();
                break;
            case BY_3_POINTS:
                inputBuilder.setPrompts("Укажите координаты точки 1", "X", "Y");
                setInputHandlers(this::drawPoints);
                break;
        }
        return this;
    }

    private void drawPoints(double x, double y) {
        MyPoint point = new MyPoint(x, y);
        points.add(point);
        space.addObject(point);
        point.Draw(space);
        if (points.size() == 3) {
            MyCircle circle = new MyCircle(points.get(0), points.get(1), points.get(2));
            space.addObject(circle.getCenter());
            circle.getCenter().Draw(space);
            space.addObject(circle);
            circle.Draw(space);
            space.getWorkSpace().getChildren().removeAll(points);
            space.removePoints(points);
            points.clear();
            inputBuilder.setPrompts("Укажите координаты точки 1", "X", "Y");
        }
        else {
            inputBuilder.setPrompts("Укажите координаты точки " + (points.size() + 1), "X", "Y");
        }
    }

    private void drawRadius() {
        inputBuilder.setPrompts("Укажите координаты центральной точки", "X", "Y");
        space.getWorkSpace().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double x = e.getX();
                double y = e.getY();
                MyPoint point = new MyPoint(x, y);
                points.add(point);
                if (points.size() == 1){
                    space.addObject(point);
                    point.Draw(space);
                    inputBuilder.setPrompts("Укажите радиус", "R");
                }
                if (points.size() >= 2) {
                    MyCircle circle = new MyCircle(points.get(0), points.get(1));
                    space.addObject(circle);
                    circle.Draw(space);
                    points.clear();
                    inputBuilder.setPrompts("Укажите координаты центральной точки", "X", "Y");
                }
            }
        });
        space.getInputTool().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                List<Double> inputs = inputBuilder.readInputValues();
                if (!points.isEmpty()) {
                    double radius = inputs.getFirst() * space.getScale();
                    inputBuilder.setPrompts("Укажите координаты центральной точки", "X", "Y");
                    MyCircle circle = new MyCircle(points.getFirst(), radius);
                    space.addObject(circle);
                    circle.Draw(space);
                    points.clear();
                } else {
                    double x = (space.getCoords().getPoint().getX() + inputs.get(0) * space.getScale());
                    double y = (space.getCoords().getPoint().getY() - inputs.get(1) * space.getScale());
                    MyPoint point = new MyPoint(x, y);
                    points.add(point);
                    space.addObject(point);
                    point.Draw(space);
                    inputBuilder.setPrompts("Укажите радиус", "R");
                }
            }
        });
    }
}