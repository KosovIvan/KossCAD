package mai.geomod.kosscad;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
    private Label mouseX;
    @FXML
    private Label mouseY;
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

    public Controller() {

    }

    @FXML
    public void initialize() {

    }

    private void coordsSystemInit() {

    }

    private void cursorInit() {

    }

    private void toggleGroupInit() {

    }

    private void setDefaultMouseMovedHandler() {

    }

    private void setDefaultMouseClickedHandler() {

    }

    private void resetColors() {

    }

    private void hovering(MouseEvent e) {

    }

    private void figureSelecting(MouseEvent e) {

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