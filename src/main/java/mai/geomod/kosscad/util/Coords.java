package mai.geomod.kosscad.util;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import mai.geomod.kosscad.figures.MyPoint;

public class Coords extends Group {
    private final Line lineVert;
    private final Line lineHor;
    private final MyPoint point;
    private final WorkSpace space;

    public Coords(WorkSpace space, double x, double y) {
        this.space = space;
        lineVert = new Line();
        lineHor = new Line();
        lineVert.setStartX(x);
        lineVert.setEndX(x);
        lineVert.setStartY(0);
        lineVert.setEndY(space.getWorkSpace().getPrefHeight());
        lineHor.setStartX(0);
        lineHor.setEndX(space.getWorkSpace().getPrefWidth());
        lineHor.setStartY(y);
        lineHor.setEndY(y);
        point = new MyPoint(x, y);
        lineVert.setStroke(Color.GREEN);
        lineHor.setStroke(Color.RED);
        point.setColor(Color.YELLOW);
        getChildren().addAll(lineVert, lineHor, point);
    }

    public Line getLineVert() {
        return lineVert;
    }

    public Line getLineHor() {
        return lineHor;
    }

    public MyPoint getPoint() {
        return point;
    }

    public void setCoords(double x, double y) {
        point.setX(x);
        point.setY(y);
        lineVert.setStartX(x);
        lineVert.setEndX(x);
        lineVert.setStartY(0);
        lineVert.setEndY(space.getWorkSpace().getPrefHeight());
        lineHor.setStartX(0);
        lineHor.setEndX(space.getWorkSpace().getPrefWidth());
        lineHor.setStartY(y);
        lineHor.setEndY(y);
    }
}
