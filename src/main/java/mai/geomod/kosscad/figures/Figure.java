package mai.geomod.kosscad.figures;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.WorkSpace;

public abstract class Figure extends Group {
    protected Color color;
    protected double thickness;
    protected LineType lineType;
    protected long id;

    public Figure() {
        color = Color.WHITE;
        thickness = 2;
        lineType = LineType.SOLID;
    }
    public Figure(Color color, int thickness) {
        this.color = color;
        this.thickness = thickness;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public double getThickness() {
        return thickness;
    }
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public void setLineType(LineType lineType, double scale) {
        this.lineType = lineType;
    }
    public LineType getLineType() {
        return lineType;
    }

    public abstract String getName();
    public abstract boolean isHover(double x, double y);
    public abstract void Draw(WorkSpace space);
    public abstract void Move(double xDelta, double yDelta);
    public abstract void Scale(double scale, double cursorX, double cursorY);
}