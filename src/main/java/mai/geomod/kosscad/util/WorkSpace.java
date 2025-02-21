package mai.geomod.kosscad.util;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import mai.geomod.kosscad.figures.Figure;
import mai.geomod.kosscad.figures.MyPoint;

import java.util.ArrayList;
import java.util.List;

public class WorkSpace {
    private final double xStart;
    private final double yStart;
    private double scale;
    private final Pane workSpace;
    private MyPoint center;
    private final List<Group> objectList;
    private EventHandler<MouseEvent> leftMouseClick;

    public WorkSpace(Pane workSpace) {
        this.workSpace = workSpace;
        xStart = workSpace.getPrefWidth() / 2;
        yStart = workSpace.getPrefHeight() / 2;
        scale = 1;
        objectList = new ArrayList<Group>();
    }

    public Pane getWorkSpace() {
        return workSpace;
    }

    public double getXStart() {
        return xStart;
    }

    public double getYStart() {
        return yStart;
    }

    public MyPoint getCenter() {
        return center;
    }

    public void setCenter(MyPoint center) {
        this.center = center;
    }

    public void addObject(Group object) {
        objectList.add(object);
    }

    public void removePoints(List<MyPoint> objects) {
        objectList.removeAll(objects);
    }

    public double getScale() {
        return scale;
    }

    public void setLeftMouseClick(EventHandler<MouseEvent> leftMouseClick) {
        this.leftMouseClick = leftMouseClick;
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
        double curScale = (e.getDeltaY() > 0) ? 0.9 : 1.1;
        scale *= curScale;
        for (Group o: objectList) {
            if (o instanceof Figure figure) {
                figure.Scale(curScale, e.getX(), e.getY());
            }
            else if (o instanceof Coords coords) {
                coords.Scale(curScale, e.getX(), e.getY());
            }
        }
    }
}