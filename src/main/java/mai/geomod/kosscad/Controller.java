package mai.geomod.kosscad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import mai.geomod.kosscad.drawing.CoordsDrawer;
import mai.geomod.kosscad.drawing.CursorDrawer;
import mai.geomod.kosscad.util.*;

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

    private WorkSpace space;
    private Input input;
    private Coords coords;
    private MyCursor cursor;
    private double[] startCord = new double[2];

    @FXML
    public void initialize() {
        workSpaceInit();
        toolBarInit();
        coordsInit();
        cursorInit();
    }

    private void workSpaceInit() {
        space = new WorkSpace(workSpace);
        space.getWorkSpace().setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.MIDDLE) {
                startCord[0] = e.getX();
                startCord[1] = e.getY();
            }
        });
        space.getWorkSpace().setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.MIDDLE) {
                space.setxDelta(e.getX() - startCord[0]);
                space.setyDelta(e.getY() - startCord[1]);
                space.pan();
                startCord[0] = e.getX();
                startCord[1] = e.getY();
            }
        });
    }

    private void toolBarInit() {
        input = new Input(inputTool);
        input.getToolBar().setManaged(false);
    }

    private void coordsInit(){
        coords = new Coords(space, space.getXStart(),space.getYStart());
        coords.getDrawer().Draw(space, coords);
        space.addObject(coords);
    }

    private void cursorInit() {
        cursor = new MyCursor();
        PositionData data = new PositionData(position);
        space.getWorkSpace().setCursor(Cursor.NONE);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        (new CursorDrawer()).Draw(space, cursor);
        space.getWorkSpace().setOnMouseMoved(e -> {
            cursor.update(e);
            data.setPosition(cursor.getPosition());
        });
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