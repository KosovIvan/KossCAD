package mai.geomod.kosscad.util;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import mai.geomod.kosscad.figures.MyPoint;

public class MyCursor extends Group {
    private final Line lineVert;
    private final Line lineHor;

    public MyCursor(){
        lineVert = new Line();
        lineHor = new Line();
        lineVert.setStroke(Color.WHITE);
        lineHor.setStroke(Color.WHITE);
        getChildren().addAll(lineVert, lineHor);
    }

    public void update(MouseEvent e) {
        lineVert.setStartX(e.getX());
        lineVert.setEndX(e.getX());
        lineVert.setStartY(e.getY() - 20);
        lineVert.setEndY(e.getY() + 20);

        lineHor.setStartX(e.getX() - 20);
        lineHor.setEndX(e.getX() + 20);
        lineHor.setStartY(e.getY());
        lineHor.setEndY(e.getY());
    }

    public MyPoint getPosition(){
        double x = lineVert.getStartX();
        double y = lineHor.getStartY();
        return new MyPoint(x, y);
    }
}
