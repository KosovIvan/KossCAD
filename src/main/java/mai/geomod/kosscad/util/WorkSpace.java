package mai.geomod.kosscad.util;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import mai.geomod.kosscad.figures.Figure;
import mai.geomod.kosscad.moving.IMovable;

import java.util.ArrayList;
import java.util.List;

public class WorkSpace {
    private final double xStart;
    private final double yStart;
    private double xDelta;
    private double yDelta;
    private double scale;
    private final Pane workSpace;
    private final List<Group> objectList;

    public WorkSpace(Pane workSpace) {
        this.workSpace = workSpace;
        xStart = workSpace.getPrefWidth() / 2;
        yStart = workSpace.getPrefHeight() / 2;
        xDelta = 0;
        yDelta = 0;
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

    public void addObject(Group object) {
        objectList.add(object);
    }

    public void setxDelta(double xDelta) {
        this.xDelta += xDelta;
    }
    public double getxDelta() {
        return xDelta;
    }

    public void setyDelta(double yDelta) {
        this.yDelta += yDelta;
    }
    public double getyDelta() {
        return yDelta;
    }

    public void pan() {
        for (Group o : objectList) {
            if (o instanceof Figure figure) {
                figure.getMover().Move(this, figure);
            }
            else if (o instanceof Coords coords) {
                coords.getMover().Move(this, coords);
            }
        }
    }
}