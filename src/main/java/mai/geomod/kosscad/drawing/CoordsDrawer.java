package mai.geomod.kosscad.drawing;

import mai.geomod.kosscad.util.Coords;
import mai.geomod.kosscad.util.WorkSpace;

public class CoordsDrawer implements IDrawable<Coords> {

    @Override
    public void Draw(WorkSpace space, Coords coords) {
        space.getWorkSpace().getChildren().add(coords);
    }
}
