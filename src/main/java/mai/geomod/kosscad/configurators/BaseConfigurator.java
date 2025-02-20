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
        points = new ArrayList<MyPoint>();
    }

    public abstract void Activate(ToggleButton btn);

    public void Canceletion() {
        space.removePoints(points);
        points.clear();
        space.setLeftMouseClick(null);
    }
}