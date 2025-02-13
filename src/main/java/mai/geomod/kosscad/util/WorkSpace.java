package mai.geomod.kosscad.util;

import javafx.scene.layout.Pane;

public class WorkSpace {
    private double xdelta;
    private double ydelta;
    private final Pane workSpace;

    public WorkSpace(Pane workSpace) {
        this.workSpace = workSpace;
        xdelta = 0;
        ydelta = 0;
    }

    public Pane getWorkSpace() {
        return workSpace;
    }
}
