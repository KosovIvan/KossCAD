package mai.geomod.kosscad.configurators;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import mai.geomod.kosscad.figures.MyCircle;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.figures.MyRect;
import mai.geomod.kosscad.modes.DrawingMode;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.Arrays;

public class CircleConfigurator extends BaseConfigurator{
    private EventHandler<MouseEvent> e;

    public CircleConfigurator(WorkSpace space) {
        super(space);
        modes.getItems().addAll(DrawingMode.RADIUS, DrawingMode.BY_3_POINTS);
        modes.setValue(DrawingMode.RADIUS);
    }

    @Override
    public BaseConfigurator Activate() {
        e = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    double x = event.getX();
                    double y = event.getY();
                    MyPoint point = new MyPoint(x, y);
                    points.add(point);
                    if (points.size() == 1){
                        space.addObject(point);
                        point.Draw(space);
                    }
                    if (points.size() >= 2) {
                        MyCircle circle = new MyCircle(points.get(0), points.get(1));
                        space.addObject(circle);
                        circle.Draw(space);
                        points.clear();
                    }
                }
            }
        };
        space.getWorkSpace().setOnMouseClicked(e);
        return this;
    }

    @Override
    public void Cancellation() {
        super.Cancellation();
        space.getWorkSpace().setOnMouseClicked(null);
        e = null;
    }
}
