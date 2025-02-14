package mai.geomod.kosscad.drawing;

import mai.geomod.kosscad.util.MyCursor;
import mai.geomod.kosscad.util.WorkSpace;

public class CursorDrawer implements IDrawable<MyCursor> {

    @Override
    public void Draw(WorkSpace space, MyCursor cursor){
        space.getWorkSpace().getChildren().add(cursor);
    }
}
