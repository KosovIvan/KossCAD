package mai.geomod.kosscad.configurators;

import mai.geomod.kosscad.figures.MyBezier;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.figures.MyQuad;
import mai.geomod.kosscad.figures.MySpline;
import mai.geomod.kosscad.modes.DrawingMode;
import mai.geomod.kosscad.util.WorkSpace;

public class SplineConfigurator extends BaseConfigurator {
    private MySpline spline;

    public SplineConfigurator(WorkSpace space) {
        super(space);
        modes.getItems().addAll(DrawingMode.QUAD, DrawingMode.BEZIER);
        modes.setValue(DrawingMode.QUAD);
    }

    @Override
    public BaseConfigurator Activate() {
        switch (modes.getValue()) {
            case QUAD:
                drawSpline(DrawingMode.QUAD);
                break;
            case BEZIER:
                drawSpline(DrawingMode.BEZIER);
                break;
        }
        return this;
    }

    private void drawSpline(DrawingMode mode) {
        points.clear();
        if (mode == DrawingMode.QUAD) spline = new MyQuad();
        else spline = new MyBezier();
        space.addObject(spline);
        spline.Draw(space);
        inputBuilder.setPrompts("Укажите координаты точки 1", "X", "Y");
        setInputHandlers(this::drawPoint);
    }

    private void drawPoint(double x, double y) {
        MyPoint point = new MyPoint(x, y);
        points.add(point);
        spline.addPoint(point);
        if (points.size() == 1) point.Draw(space);
        if (points.size() == 2) space.getWorkSpace().getChildren().remove(points.getFirst());

        inputBuilder.setPrompts("Укажите координаты точки " + (points.size() + 1), "X", "Y");
    }
}
