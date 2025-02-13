package mai.geomod.kosscad;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Controller {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Pane workSpace;
    @FXML
    private ToolBar bottomBar;
    @FXML
    private Region spacer;
    @FXML
    private VBox position;
    @FXML
    private ToggleButton lineBtn;
    @FXML
    private ToggleButton rectBtn;
    @FXML
    private ToggleButton circleBtn;
    @FXML
    private ToggleButton splineBtn;
    @FXML
    private ToggleButton arcBtn;
    @FXML
    private ToggleButton polygonBtn;
    @FXML
    private ToggleButton panBtn;
    @FXML
    private ToggleButton rotationBtn;
    @FXML
    private ToolBar inputTool;

    private MyCursor cursor;
    private Coords coords;
    private WorkSpace space;

    @FXML
    public void initialize() {
        workSpaceInit();
        coordsInit();
        cursorInit();
    }

    private void coordsInit(){
        coords = new Coords(10,10);

    }

    private void cursorInit() {
        cursor = new MyCursor();
        PositionData data = new PositionData(position);
        space.getWorkSpace().setCursor(Cursor.NONE);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        (new CursorDrawer(space, cursor)).Draw();
        space.getWorkSpace().setOnMouseMoved(e -> {
            cursor.update(e);
            data.setPosition(cursor.getPosition());
        });
    }

    private void workSpaceInit() {
        space = new WorkSpace(workSpace);
    }

    @FXML
    private void lineDrawing(ActionEvent event) {

    }

    @FXML
    private void rectDrawing(ActionEvent event) {

    }

    @FXML
    private void circleDrawing(ActionEvent event) {

    }

    @FXML
    private void splineDrawing(ActionEvent event) {

    }

    @FXML
    private void arcDrawing(ActionEvent event) {

    }

    @FXML
    private void polygonDrawing(ActionEvent event) {

    }

    @FXML
    private void panByLBM(ActionEvent event) {

    }

    @FXML
    private void zoomPlus(ActionEvent event) {

    }

    @FXML
    private void zoomMinus(ActionEvent event) {

    }

    @FXML
    private void rotate() {

    }
}