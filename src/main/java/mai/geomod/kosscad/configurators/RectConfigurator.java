package mai.geomod.kosscad.configurators;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import mai.geomod.kosscad.figures.MyLine;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.figures.MyRect;
import mai.geomod.kosscad.modes.DrawingMode;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.Arrays;
import java.util.List;

public class RectConfigurator extends BaseConfigurator{

    public RectConfigurator(WorkSpace space) {
        super(space);
        modes.getItems().addAll(DrawingMode.BY_2_POINTS, DrawingMode.SIDES);
        modes.setValue(DrawingMode.BY_2_POINTS);
    }

    @Override
    public BaseConfigurator Activate() {
        switch (modes.getValue()) {
            case BY_2_POINTS:
                inputBuilder.setPrompts("Укажите координаты точки 1", "X", "Y");
                setInputHandlers(this::drawPoints);
                break;
            case SIDES:
                drawSides();
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
            MyRect rect = new MyRect(points.get(0), points.get(1));
            points.addAll(Arrays.asList(rect.get2OtherPoints()));
            space.addObjects(rect.get2OtherPoints());
            space.addObject(rect);
            rect.Draw(space);
            points.clear();
            inputBuilder.setPrompts("Укажите координаты точки 1", "X", "Y");
        }
        else {
            inputBuilder.setPrompts("Укажите координаты точки " + 2, "X", "Y");
        }
    }

    private void drawSides() {
        inputBuilder.setPrompts("Укажите координаты центральной точки", "X", "Y");
        space.getInputTool().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                List<Double> inputs = inputBuilder.readInputValues();
                double param1 = inputs.get(0) * space.getScale();
                double param2 = inputs.get(1) * space.getScale();
                if (!points.isEmpty()) {
                    space.removeObject(points.getFirst());
                    space.getWorkSpace().getChildren().remove(points.getFirst());
                    MyRect rect = new MyRect(points.getFirst(), param1, param2);
                    space.addObjects(rect.getAllPoints());
                    space.addObject(rect);
                    rect.Draw(space);
                    points.clear();
                    inputBuilder.setPrompts("Укажите координаты центральной точки", "X", "Y");
                } else {
                    double x = space.getCoords().getPoint().getX() + param1;
                    double y = space.getCoords().getPoint().getY() - param2;
                    MyPoint point = new MyPoint(x, y);
                    points.add(point);
                    space.addObject(point);
                    point.Draw(space);
                    inputBuilder.setPrompts("Укажите размеры сторон", "Ширина", "Высота");
                }
            }
        });
        space.getWorkSpace().setOnMouseClicked(null);
    }
}
