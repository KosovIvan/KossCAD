package mai.geomod.kosscad.drawing;

import mai.geomod.kosscad.figures.MyLine;
import mai.geomod.kosscad.util.WorkSpace;

public class LineDrawer implements IDrawable{
    private final WorkSpace space;
    private final MyLine line;

    public LineDrawer(WorkSpace space, MyLine line) {
        this.space = space;
        this.line = line;
    }

    @Override
    public void Draw() {

    }
}
