package mai.geomod.kosscad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = (Parent)FXMLLoader.load(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(root, 1280.0, 720.0);
        stage.setTitle("KossCAD");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}