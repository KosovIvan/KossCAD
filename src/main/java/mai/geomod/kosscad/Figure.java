package mai.geomod.kosscad;

import javafx.scene.paint.Color;

public abstract class Figure {
    protected Color color;
    protected int thickness;
    public Figure() {}
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
}
