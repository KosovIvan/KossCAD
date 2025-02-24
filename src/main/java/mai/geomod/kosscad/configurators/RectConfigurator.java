package mai.geomod.kosscad.configurators;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import mai.geomod.kosscad.figures.MyLine;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.figures.MyRect;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.Arrays;

public class RectConfigurator extends BaseConfigurator{
    private EventHandler<MouseEvent> e;

    public RectConfigurator(WorkSpace space) {
        super(space);
    }

    @Override
    public BaseConfigurator Activate(ToggleButton btn) {
        if (btn.isSelected()) {
            e = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        double x = event.getX();
                        double y = event.getY();
                        MyPoint point = new MyPoint(x, y);
                        points.add(point);
                        space.addObject(point);
                        point.Draw(space);
                        if (points.size() >= 2) {
                            MyRect rect = new MyRect(points.get(0), points.get(1));
                            points.addAll(Arrays.asList(rect.getOtherPoints()));
                            space.addObjects(rect.getOtherPoints());
                            space.addObject(rect);
                            rect.Draw(space);
                            points.clear();
                        }
                    }
                }
            };
            space.getWorkSpace().setOnMouseClicked(e);
            return this;
        }
        else Cancellation();
        return null;
    }

    @Override
    public void Cancellation() {
        super.Cancellation();
        space.getWorkSpace().setOnMouseClicked(null);
        e = null;
    }
}
