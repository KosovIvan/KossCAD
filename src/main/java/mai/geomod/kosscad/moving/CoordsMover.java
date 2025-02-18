package mai.geomod.kosscad.moving;

import mai.geomod.kosscad.util.Coords;
import mai.geomod.kosscad.util.WorkSpace;

public class CoordsMover implements IMovable<Coords> {

    @Override
    public void Move(WorkSpace space, Coords coords){
        coords.setCoords(coords.getPoint().getxStart() + space.getxDelta(),coords.getPoint().getyStart() + space.getyDelta());
    }
}
