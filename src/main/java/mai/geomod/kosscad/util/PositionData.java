package mai.geomod.kosscad.util;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import mai.geomod.kosscad.figures.MyPoint;

public class PositionData {
    private final VBox vbox;
    private final WorkSpace space;

    public PositionData(VBox vbox, WorkSpace space) {
        Label xlabel = new Label("X: ");
        Label ylabel = new Label("Y: ");
        this.vbox = vbox;
        this.space = space;
        xlabel.setStyle("-fx-text-fill: #D3DAE4;");
        ylabel.setStyle("-fx-text-fill: #D3DAE4;");
        vbox.getChildren().addAll(xlabel, ylabel);
    }

    public void setPosition(MyPoint pt){
        Label xlabel = (Label)vbox.getChildren().get(0);
        xlabel.setText("X: " + String.format("%.1f", (pt.getX() - space.getxDelta() - space.getXStart()) * space.getScale()));
        Label ylabel = (Label)vbox.getChildren().get(1);
        ylabel.setText("Y: " + String.format("%.1f", (space.getyDelta() + space.getYStart() - pt.getY()) * space.getScale()));
    }

    public VBox getVbox() {
        return vbox;
    }
}
