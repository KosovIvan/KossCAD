package mai.geomod.kosscad.editors;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import mai.geomod.kosscad.figures.Figure;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.figures.MySpline;
import mai.geomod.kosscad.util.WorkSpace;

public class SplineEditor {
    private final WorkSpace space;
    private final Pane workspace;
    private final MySpline spline;
    private MyPoint selectedPoint;

    public SplineEditor(WorkSpace space, Figure figure) {
        this.space = space;
        workspace = space.getWorkSpace();
        spline = (MySpline)figure;
    }

    public void pointMovement() {
        final double[] start = new double[2];
        workspace.setOnMousePressed(e -> {
            space.getDefaultMousePressedHandler().handle(e);
            if (e.getButton() == MouseButton.PRIMARY) {
                selectedPoint = spline.getSelectedPoint(e.getX(), e.getY()).orElse(null);
                start[0] = e.getX();
                start[1] = e.getY();
            }
        });

        workspace.setOnMouseDragged(e -> {
            space.getDefaultMouseDraggedHandler().handle(e);
            if (e.getButton() == MouseButton.PRIMARY) {
                if (selectedPoint != null) {
                    double deltaX = e.getX() - start[0];
                    double deltaY = e.getY() - start[1];

                    selectedPoint.Move(deltaX, deltaY);
                    spline.Update();

                    start[0] = e.getX();
                    start[1] = e.getY();
                }
            }
        });
    }
}
