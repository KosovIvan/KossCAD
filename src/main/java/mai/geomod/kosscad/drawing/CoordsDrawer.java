package mai.geomod.kosscad.drawing;

import mai.geomod.kosscad.util.Coords;
import mai.geomod.kosscad.util.WorkSpace;

public class CoordsDrawer implements IDrawable {
    private final WorkSpace space;
    private final Coords coords;

    public CoordsDrawer(WorkSpace space, Coords coords) {
        this.space = space;
        this.coords = coords;
    }

    @Override
    public void Draw() {
        space.getWorkSpace().getChildren().add(coords);
    }
}
