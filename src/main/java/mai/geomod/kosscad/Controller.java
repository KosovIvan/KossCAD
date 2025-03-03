package mai.geomod.kosscad;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import mai.geomod.kosscad.configurators.BaseConfigurator;
import mai.geomod.kosscad.configurators.CircleConfigurator;
import mai.geomod.kosscad.configurators.LineConfigurator;
import mai.geomod.kosscad.configurators.RectConfigurator;
import mai.geomod.kosscad.figures.Figure;
import mai.geomod.kosscad.util.*;
import mai.geomod.kosscad.utilObjects.Coords;
import mai.geomod.kosscad.utilObjects.MyCursor;

import java.util.LinkedList;
import java.util.List;

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
    private ToggleButton lineBtn, rectBtn, circleBtn, splineBtn, arcBtn, polygonBtn, panBtn, rotationBtn;
    @FXML
    private ToolBar inputTool;

    private WorkSpace space;
    private Coords coords;
    private MyCursor cursor;
    private BaseConfigurator lastConf;
    private LineConfigurator lineConf;
    private RectConfigurator rectConf;
    private CircleConfigurator circleConf;
    private EventHandler<? super MouseEvent> previousMouseClickHandler;
    private EventHandler<MouseEvent> defaultMouseMovedHandler, defaultMouseClickedHandler, defaultMouseDraggedHandler, defaultMousePressedHandler;
    private final List<Figure> selectedFigures = new LinkedList<>();
    private double[] startCord = new double[2];

    @FXML
    public void initialize() {
        workSpaceInit();
        coordsInit();
        cursorInit();
        defaultMouseHandlersInit();

        List<EventHandler<MouseEvent>> mouseHandlers = List.of(
                defaultMouseMovedHandler,
                defaultMouseClickedHandler,
                defaultMouseDraggedHandler,
                defaultMousePressedHandler
        );
        space.setHandlers(mouseHandlers);
        borderPane.setLeft(null);

        lineConf = new LineConfigurator(space);
        rectConf = new RectConfigurator(space);
        circleConf = new CircleConfigurator(space);
    }

    private void workSpaceInit() {
        space = new WorkSpace(workSpace, inputTool);
        space.getWorkSpace().setOnScroll(e -> {
            space.scale(e);
        });
    }

    private void coordsInit(){
        coords = new Coords(space, space.getCenter().getX(), space.getCenter().getY());
        coords.Draw(space);
        space.addObject(coords);
        space.setCoords(coords);
    }

    private void cursorInit() {
        cursor = new MyCursor();
        space.getWorkSpace().setCursor(Cursor.NONE);
        cursor.Draw(space);
    }

    private void setDefaultMouseMovedHandler() {
        PositionData data = new PositionData(position, space, coords);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        defaultMouseMovedHandler = e -> {
            cursor.update(e);
            data.setPosition(cursor.getPosition());
            hovering(e);
        };
        space.getWorkSpace().setOnMouseMoved(defaultMouseMovedHandler);
    }

    private void setDefaultMouseClickedHandler() {
        defaultMouseClickedHandler = this::figureSelecting;
        space.getWorkSpace().setOnMouseClicked(defaultMouseClickedHandler);
    }

    private void setDefaultMousePressedHandler() {
        defaultMousePressedHandler = e -> {
            startCord[0] = e.getX();
            startCord[1] = e.getY();
        };
        space.getWorkSpace().setOnMousePressed(defaultMousePressedHandler);
    }

    private void setDefaultMouseDraggedHandler(MouseButton button) {
        defaultMouseDraggedHandler = event -> {
            cursor.update(event);
            if (event.getButton() == button)
                space.pan(event.getX() - startCord[0], event.getY() - startCord[1]);
                startCord[0] = event.getX();
                startCord[1] = event.getY();
        };
        space.getWorkSpace().setOnMouseDragged(defaultMouseDraggedHandler);
    }

    private void defaultMouseHandlersInit() {
        setDefaultMouseMovedHandler();
        setDefaultMouseClickedHandler();
        setDefaultMousePressedHandler();
        setDefaultMouseDraggedHandler(MouseButton.MIDDLE);
    }

    private void resetColors() {
        space.getWorkSpace().getChildren().forEach(elem -> {
            if (elem instanceof Figure figure)
                figure.setColor(Color.WHITE);
        });
    }

    private Figure findHoveredFigure(MouseEvent e) {
        return (Figure)space.getWorkSpace().getChildren()
                .stream()
                .filter(elem ->
                        elem instanceof Figure figure &&
                                figure.isHover(e.getX(), e.getY()))
                .findFirst()
                .orElse(null);
    }

    private void hovering(MouseEvent e) {
        Figure hoveredFigure = findHoveredFigure(e);
        resetColors();
        if (hoveredFigure != null)
            hoveredFigure.setColor(Color.GRAY);
        selectedFigures.forEach(figure -> figure.setColor(Color.ORANGE));
    }

    private void figureSelecting(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            Figure hoveredFigure = findHoveredFigure(e);
            if (!rotationBtn.isSelected())
                borderPane.setLeft(null);
            resetColors();

            if (hoveredFigure != null) {
                if (!e.isShiftDown())
                    selectedFigures.clear();

                selectedFigures.add(hoveredFigure);
                selectedFigures.forEach(figure -> figure.setColor(Color.ORANGE));

                if (selectedFigures.size() == 1 && !rotationBtn.isSelected()) {
                    //new FigureEditor(drawingContext, hoveredFigure).inputBarInit();
                    borderPane.setLeft(inputTool);
                }
            } else {
                selectedFigures.clear();
                space.getWorkSpace().setOnMouseDragged(defaultMouseDraggedHandler);
            }
        }
    }

    @FXML
    private void lineDrawing(ActionEvent event) {
        figureDrawing(lineBtn, lineConf);
    }

    @FXML
    private void rectDrawing(ActionEvent event) {
        figureDrawing(rectBtn, rectConf);
    }

    @FXML
    private void circleDrawing(ActionEvent event) {
        figureDrawing(circleBtn, circleConf);
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

    private void figureDrawing(ToggleButton button, BaseConfigurator conf) {
        if (lastConf != null) lastConf.Cancellation();
        resetColors();
        selectedFigures.clear();

        if (button.isSelected()) {
            borderPane.setLeft(inputTool);
            panBtn.setSelected(false);
            conf.Activate();
        } else {
            conf.Cancellation();
            space.getWorkSpace().setOnMouseClicked(defaultMouseClickedHandler);
            borderPane.setLeft(null);
            previousMouseClickHandler = null;
        }
    }

    @FXML
    private void panByLBM(ActionEvent event) {
        if (space.getWorkSpace().getOnMouseClicked() != null)
            previousMouseClickHandler = space.getWorkSpace().getOnMouseClicked();

        if (panBtn.isSelected()) {
            space.getWorkSpace().setOnMouseClicked(null);
            setDefaultMouseDraggedHandler(MouseButton.PRIMARY);
        } else {
            setDefaultMouseDraggedHandler(MouseButton.MIDDLE);
            space.getWorkSpace().setOnMouseClicked(previousMouseClickHandler);
        }
    }

    @FXML
    private void zoomPlus(ActionEvent event) {
        space.scaleByBtn(0.9);
    }

    @FXML
    private void zoomMinus(ActionEvent event) {
        space.scaleByBtn(1.1);
    }

    @FXML
    private void rotate() {

    }
}