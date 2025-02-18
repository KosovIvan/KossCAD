package mai.geomod.kosscad.figures;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import mai.geomod.kosscad.drawing.IDrawable;
import mai.geomod.kosscad.moving.IMovable;
import mai.geomod.kosscad.scaling.IScalable;

public abstract class Figure extends Group {
    protected Color color;
    protected int thickness;
    protected IDrawable drawer;
    protected IMovable mover;
    protected IScalable scaler;

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

    public IDrawable getDrawer() {
        return drawer;
    }

    public IMovable getMover() {
        return mover;
    }

    public IScalable getScaler() {
        return scaler;
    }
}