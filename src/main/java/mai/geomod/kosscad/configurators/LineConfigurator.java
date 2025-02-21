package mai.geomod.kosscad.configurators;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import mai.geomod.kosscad.figures.MyLine;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.util.WorkSpace;

public class LineConfigurator extends BaseConfigurator {
    private EventHandler<MouseEvent> e;

    public LineConfigurator(WorkSpace space) {
        super(space);
    }

    @Override
    public void Activate(ToggleButton btn) {
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
                            MyLine line = new MyLine(points.get(0), points.get(1));
                            space.addObject(line);
                            line.Draw(space);
                            points.clear();
                        }
                    }
                }
            };
            space.getWorkSpace().setOnMouseClicked(e);
        }
        else Canceletion();
    }

    public void firstPoint(MouseEvent e) {

    }
}