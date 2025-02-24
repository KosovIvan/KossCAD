package mai.geomod.kosscad.configurators;

import javafx.scene.Group;
import javafx.scene.control.ToggleButton;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseConfigurator {
    protected final List<MyPoint> points;
    protected final WorkSpace space;

    public BaseConfigurator(WorkSpace space) {
        this.space = space;
        points = new ArrayList<>();
    }

    public abstract BaseConfigurator Activate(ToggleButton btn);

    public void Cancellation() {
        space.removePoints(points);
        space.getWorkSpace().getChildren().removeAll(points);
        points.clear();
        space.setLeftMouseClick(null);
    }
}