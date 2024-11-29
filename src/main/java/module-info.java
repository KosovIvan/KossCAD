module mai.geomod.kosscad {
    requires javafx.controls;
    requires javafx.fxml;


    opens mai.geomod.kosscad to javafx.fxml;
    exports mai.geomod.kosscad;
}