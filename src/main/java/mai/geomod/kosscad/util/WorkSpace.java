package mai.geomod.kosscad.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import mai.geomod.kosscad.figures.Figure;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.utilObjects.Coords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkSpace {
    private double scale;
    private final Pane workSpace;
    private final ToolBar inputTool;
    private MyPoint center;
    private Coords coords;
    private final List<Group> objectList;
    private EventHandler<MouseEvent> defaultMouseMovedHandler, defaultMouseClickedHandler, defaultMouseDraggedHandler, defaultMousePressedHandler;

    public WorkSpace(Pane workSpace, ToolBar inputTool) {
        this.workSpace = workSpace;
        this.inputTool = inputTool;
        double xStart = workSpace.getPrefWidth() / 2;
        double yStart = workSpace.getPrefHeight() / 2;
        center = new MyPoint(xStart, yStart);
        scale = 1;
        objectList = new ArrayList<Group>();
    }

    public void setHandlers(List<EventHandler<MouseEvent>> mouseHandlers) {
        this.defaultMouseMovedHandler = mouseHandlers.get(0);
        this.defaultMouseClickedHandler = mouseHandlers.get(1);
        this.defaultMouseDraggedHandler = mouseHandlers.get(2);
        this.defaultMousePressedHandler = mouseHandlers.get(3);
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }
    public Coords getCoords() {
        return coords;
    }

    public Pane getWorkSpace() {
        return workSpace;
    }

    public ToolBar getInputTool() {
        return inputTool;
    }

    public MyPoint getCenter() { return center; }

    public void addObject(Group object) {
        objectList.add(object);
    }
    public void addObjects(Group[] objects) {
        objectList.addAll(Arrays.asList(objects));
    }

    public void removeObject(Group object) { objectList.remove(object); }
    public void removePoints(List<MyPoint> objects) { objectList.removeAll(objects); }

    public double getScale() {
        return scale;
    }

    public void pan(double xDelta, double yDelta) {
        for (Group o : objectList) {
            if (o instanceof Figure figure) {
                figure.Move(xDelta, yDelta);
            }
            else if (o instanceof Coords coords) {
                coords.Move(xDelta, yDelta);
            }
        }
    }

    public void scale(ScrollEvent e) {
        double curScale = (e.getDeltaY() > 0) ? 1.1 : 0.9;
        scale *= curScale;
        for (Group o: objectList) {
            if (o instanceof Figure figure) {
                figure.Scale(curScale, e.getX(), e.getY());
                figure.setLineType(figure.getLineType(), scale);
            }
            else if (o instanceof Coords coords) {
                coords.Scale(curScale, e.getX(), e.getY());
            }
        }
    }

    public void scaleByBtn(double curScale) {
        scale *= curScale;
        for (Group o: objectList) {
            if (o instanceof Figure figure) {
                figure.Scale(curScale, 0, 0);
            }
            else if (o instanceof Coords coords) {
                coords.Scale(curScale, 0, 0);
            }
        }
    }

    public void rotate(List<Figure> figures) {
        InputBuilder inputBuilder = new InputBuilder(inputTool);
        inputBuilder.setPrompts("Выберите объекты для поворта");
        Button button = inputBuilder.addApplyButton();
        button.requestFocus();

        EventHandler<ActionEvent> eventHandler = new EventHandler<>() {
            private MyPoint centralPoint;
            private Double angle;

            @Override
            public void handle(ActionEvent event) {
                List<Double> inputs = inputBuilder.readInputValues();
                if (!figures.isEmpty() && centralPoint == null && inputBuilder.getCoordsInputs().isEmpty()) {
                    inputBuilder.setPrompts("Укажите координаты центральной точки вращения", "X", "Y");
                } else if (centralPoint == null && !inputBuilder.getCoordsInputs().isEmpty()) {
                    double x = (coords.getPoint().getX() + inputs.get(0) * scale);
                    double y = (coords.getPoint().getY() - inputs.get(1) * scale);
                    centralPoint = new MyPoint(x, y);
                    inputBuilder.setPrompts("Укажите угол поворота", "Угол");
                } else if (centralPoint != null) {
                    angle = inputs.get(0);
                    figures.forEach(figure -> figure.Rotate(centralPoint, angle));
                    centralPoint = null;
                    inputBuilder.setPrompts("Выберите объекты для поворта");
                }
            }
        };

        button.setOnAction(eventHandler);
        inputTool.setOnKeyPressed(null);
        workSpace.setOnMouseClicked(getDefaultMouseClickedHandler());
    }


    public EventHandler<MouseEvent> getDefaultMouseMovedHandler() {
        return defaultMouseMovedHandler;
    }

    public EventHandler<MouseEvent> getDefaultMouseClickedHandler() {
        return defaultMouseClickedHandler;
    }

    public EventHandler<MouseEvent> getDefaultMouseDraggedHandler() {
        return defaultMouseDraggedHandler;
    }

    public EventHandler<MouseEvent> getDefaultMousePressedHandler() { return defaultMousePressedHandler; }
}