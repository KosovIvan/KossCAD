package mai.geomod.kosscad.configurators;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import mai.geomod.kosscad.util.WorkSpace;

public class LineConfigurator extends BaseConfigurator {
    private EventHandler<MouseEvent> ev;

    public LineConfigurator(WorkSpace space) {
        super(space);
    }

    @Override
    public void Activate(ToggleButton btn) {
        if (btn.isSelected()) {
            space.getWorkSpace().setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                }
            });
        }
        else Canceletion();
    }

    public void firstPoint(MouseEvent e) {

    }
}