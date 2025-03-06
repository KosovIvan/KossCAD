package mai.geomod.kosscad.figures;

import mai.geomod.kosscad.modes.LineType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class MySpline extends Figure{
    protected final List<MyPoint> points = new ArrayList<>();
    protected final List<MyLine> lines = new ArrayList<>();
    protected List<Double> linePattern = new ArrayList<>();

    public void addPoint(MyPoint point) {
        points.add(point);
        Update();
    }

    @Override
    public void setLineType(LineType lineType, double scale) {
        super.setLineType(lineType, scale);
        linePattern = lineType.getPattern(scale);
    }

    protected void setLinePattern(List<Double> pattern) {
        linePattern = pattern;
    }

    public List<MyPoint> getPoints() {
        return points;
    }

    public abstract void Update();
    public abstract Optional<MyPoint> getSelectedPoint(double x, double y);
}
