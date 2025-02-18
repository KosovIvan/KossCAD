package mai.geomod.kosscad.scaling;

import mai.geomod.kosscad.util.Coords;
import mai.geomod.kosscad.util.WorkSpace;

public class CoordsScaler implements IScalable<Coords>{

    @Override
    public void Scale(WorkSpace space, Coords coords, double cursorX, double cursorY) {
        double x = cursorX - coords.getPoint().getxStart();
        double y = cursorY - coords.getPoint().getyStart();;
        coords.setCoords(coords.getPoint().getX() + x * space.getScale() - x, coords.getPoint().getY() + y * space.getScale() - y);
    }
}
