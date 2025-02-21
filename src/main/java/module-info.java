module mai.geomod.kosscad {
    requires javafx.controls;
    requires javafx.fxml;


    opens mai.geomod.kosscad to javafx.fxml;
    exports mai.geomod.kosscad;
    exports mai.geomod.kosscad.figures;
    opens mai.geomod.kosscad.figures to javafx.fxml;
    exports mai.geomod.kosscad.util;
    opens mai.geomod.kosscad.util to javafx.fxml;
}