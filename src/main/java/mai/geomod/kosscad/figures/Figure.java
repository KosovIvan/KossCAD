package mai.geomod.kosscad.figures;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import mai.geomod.kosscad.util.WorkSpace;

public abstract class Figure extends Group {
    protected Color color;
    protected int thickness;

    public Figure() {
        color = Color.WHITE;
        thickness = 2;
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

    public int getThickness() {
        return thickness;
    }
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public abstract boolean isHover(double x, double y);
    public abstract void Draw(WorkSpace space);
    public abstract void Move(double xDelta, double yDelta);
    public abstract void Scale(double scale, double cursorX, double cursorY);
}