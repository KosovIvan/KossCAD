package mai.geomod.kosscad.configurators;

import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.modes.DrawingMode;
import mai.geomod.kosscad.util.InputBuilder;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseConfigurator {
    protected final List<MyPoint> points;
    protected final WorkSpace space;
    protected InputBuilder inputBuilder;
    protected final ComboBox<DrawingMode> modes;

    public BaseConfigurator(WorkSpace space) {
        this.space = space;
        inputBuilder = new InputBuilder(space.getInputTool());
        points = new ArrayList<>();
        modes = inputBuilder.addModeSelection();
        modes.setOnAction(e -> Activate());
    }

    public abstract BaseConfigurator Activate();

    public void Cancellation() {
        space.removePoints(points);
        space.getWorkSpace().getChildren().removeAll(points);
        points.clear();
    }

    protected interface PointHandler {
        void handle(double x, double y);
    }

    protected void setInputHandlers(PointHandler nextAction) {
        space.getWorkSpace().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                nextAction.handle(e.getX(), e.getY());
            }
        });

        space.getInputTool().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                List<Double> inputs = inputBuilder.readInputValues();
                double x = (space.getCoords().getPoint().getX() + inputs.get(0) * space.getScale());
                double y = (space.getCoords().getPoint().getY() - inputs.get(1) * space.getScale());
                nextAction.handle(x, y);
            }
        });
    }
}