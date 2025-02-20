package mai.geomod.kosscad.scaling;

import mai.geomod.kosscad.util.WorkSpace;

public interface IScalable<T> {
    public void Scale(double scale, T obj, double cursorX, double cursorY);
}
