package mai.geomod.kosscad.configurators;

import mai.geomod.kosscad.figures.MyArc;
import mai.geomod.kosscad.figures.MyLine;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.modes.DrawingMode;
import mai.geomod.kosscad.util.WorkSpace;

public class ArcConfigurator extends BaseConfigurator {
    public ArcConfigurator(WorkSpace space) {
        super(space);
        modes.getItems().addAll(DrawingMode.BY_3_POINTS, DrawingMode.CHORD);
        modes.setValue(DrawingMode.BY_3_POINTS);
    }

    @Override
    public BaseConfigurator Activate() {
        switch (modes.getValue()) {
            case BY_3_POINTS:
                inputBuilder.setPrompts("Укажите координаты точки 1", "X", "Y");
                setInputHandlers(this::drawPoints);
                break;
            case CHORD:
                inputBuilder.setPrompts("Укажите координаты центральной точки", "X", "Y");
                setInputHandlers(this::drawChord);
                break;
        }
        return this;
    }

    private void drawPoints(double x, double y) {
        MyPoint point = new MyPoint(x, y);
        points.add(point);
        space.addObject(point);
        point.Draw(space);
        if (points.size() == 3) {
            MyArc arc = new MyArc(points.get(0), points.get(1), points.get(2));
            space.addObject(arc);
            arc.Draw(space);
            space.getWorkSpace().getChildren().removeAll(points);
            space.removePoints(points);
            points.clear();
            inputBuilder.setPrompts("Укажите координаты точки 1", "X", "Y");
        }
        else {
            inputBuilder.setPrompts("Укажите координаты точки " + (points.size() + 1), "X", "Y");
        }
    }

    private void drawChord(double x, double y) {
        if (points.size() == 2) {
            MyPoint point = new MyPoint(x, y);
            points.add(point);
            MyArc arc = new MyArc(points.get(0), new MyLine(points.get(1), points.get(2)));
            space.addObject(arc);
            arc.Draw(space);
            space.getWorkSpace().getChildren().removeAll(points);
            space.removePoints(points);
            points.clear();
            inputBuilder.setPrompts("Укажите координаты центральной точки", "X", "Y");
        }
        else {
            MyPoint point = new MyPoint(x, y);
            points.add(point);
            space.addObject(point);
            point.Draw(space);
            inputBuilder.setPrompts("Укажите координаты точки " + points.size(), "X", "Y");
        }
    }
}
