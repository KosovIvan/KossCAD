package mai.geomod.kosscad;

import javafx.scene.layout.Pane;

public class WorkSpace {
    private double xTop;
    private double yTop;
    private double xBot;
    private double yBot;
    private final Pane workSpace;

    public WorkSpace(Pane workSpace) {
        this.workSpace = workSpace;
        xTop = 0;
        yTop = 0;
        xBot = workSpace.getWidth();
        yBot = workSpace.getHeight();
    }

    public Pane getWorkSpace() {
        return workSpace;
    }
}
