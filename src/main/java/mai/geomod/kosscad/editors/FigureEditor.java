package mai.geomod.kosscad.editors;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import mai.geomod.kosscad.figures.Figure;
import mai.geomod.kosscad.figures.ModifiableFigure;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.figures.MySpline;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.InputBuilder;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.List;
import java.util.Map;

public class FigureEditor {
    private final WorkSpace space;
    private final ToolBar toolBar;
    private final Figure figure;
    private final double scale;
    private final MyPoint center;
    private final InputBuilder inputBuilder;
    private ComboBox<LineType> lineTypes;

    public FigureEditor(WorkSpace space, Figure figure) {
        this.space = space;
        this.figure = figure;
        this.toolBar = space.getInputTool();
        this.scale = space.getScale();
        this.center = space.getCoords().getPoint();
        inputBuilder = new InputBuilder(toolBar);
    }

    public void inputBarInit() {
        if (figure instanceof ModifiableFigure)
            showCoords();

        showLineType();

        Button applyBtn = inputBuilder.addApplyButton();

        applyBtn.setOnAction(e -> applyInputs());
        toolBar.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER)
                applyInputs();
        });
    }

    private void showCoords() {
        Map<String, Double> prompts = ((ModifiableFigure) figure).getValuesForOutput(center);
        inputBuilder.setPrompts(figure.getName(), prompts, scale);
    }

    private void showLineType() {
        inputBuilder.setLabel(figure.getName());
        lineTypes = inputBuilder.setLineType(figure.getThickness(), figure.getLineType().getDashSpace());
        lineTypes.getItems().addAll(LineType.SOLID, LineType.DASHED, LineType.DASH_DOT, LineType.DASH_DOT_DOT);
        lineTypes.setValue(figure.getLineType());
        lineTypes.setOnAction(e -> {
            LineType lineType = LineType.copy(lineTypes.getValue());
            inputBuilder.setLineType(figure.getThickness(), lineType.getDashSpace());
            figure.setLineType(lineType, scale);
        });
    }

    private void applyInputs() {
        if (figure instanceof ModifiableFigure f) {
            List<Double> values = inputBuilder.readInputValues().stream()
                    .map(value -> value * space.getScale())
                    .toList();
            f.setValuesFromInputs(values, center);
        }

        figure.setThickness(inputBuilder.getThicknessValue());
        LineType lineType = LineType.copy(figure.getLineType());
        lineType.setDashSpace(inputBuilder.getDashSpace());
        figure.setLineType(lineType, space.getScale());
    }
}
