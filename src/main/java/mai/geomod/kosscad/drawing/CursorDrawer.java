package mai.geomod.kosscad.drawing;

import mai.geomod.kosscad.util.MyCursor;
import mai.geomod.kosscad.util.WorkSpace;

public class CursorDrawer implements IDrawable {
    private final WorkSpace space;
    private final MyCursor cursor;

    public CursorDrawer(WorkSpace space, MyCursor cursor) {
        this.space = space;
        this.cursor = cursor;
    }

    @Override
    public void Draw(){
        space.getWorkSpace().getChildren().add(cursor);
    }
}
