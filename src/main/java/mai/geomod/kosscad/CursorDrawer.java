package mai.geomod.kosscad;

public class CursorDrawer implements IDrawable {
    private final WorkSpace space;
    private final MyCursor cursor;

    public CursorDrawer(WorkSpace space, MyCursor cursor) {
        this.space = space;
        this.cursor = cursor;
    }

    public void Draw(){
        space.getWorkSpace().getChildren().add(cursor);
    }
}
