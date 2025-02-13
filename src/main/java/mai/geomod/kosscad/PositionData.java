package mai.geomod.kosscad;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PositionData {
    private final VBox vbox;

    public PositionData(VBox vbox) {
        Label xlabel = new Label("X: ");
        Label ylabel = new Label("Y: ");
        this.vbox = vbox;
        xlabel.setStyle("-fx-text-fill: #D3DAE4;");
        ylabel.setStyle("-fx-text-fill: #D3DAE4;");
        vbox.getChildren().addAll(xlabel, ylabel);
    }

    public void setPosition(MyPoint pt){
        Label xlabel = (Label)vbox.getChildren().get(0);
        xlabel.setText("X: " + String.format("%.1f", pt.getX()));
        Label ylabel = (Label)vbox.getChildren().get(1);
        ylabel.setText("Y: " + String.format("%.1f", pt.getY()));
    }

    public VBox getVbox() {
        return vbox;
    }
}
