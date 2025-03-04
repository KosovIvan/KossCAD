package mai.geomod.kosscad.util;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import mai.geomod.kosscad.modes.DrawingMode;
import mai.geomod.kosscad.modes.LineType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InputBuilder {
    private final ToolBar toolBar;
    private final Label label;
    private final List<Label> coordsPrompts;
    private final List<TextField> coordsInputs;
    private final List<Label> lineTypePrompts;
    private final List<TextField> lineTypeInputs;
    private final ComboBox<DrawingMode> modes;
    private ComboBox<LineType> lineTypes;
    private TextField thickness;
    private Button applyBtn;

    public InputBuilder(ToolBar toolBar) {
        this.toolBar = toolBar;
        label = new Label();
        label.setStyle("-fx-text-fill: #D3DAE4;");
        coordsPrompts = new ArrayList<>();
        coordsInputs = new ArrayList<>();
        lineTypePrompts = new ArrayList<>();
        lineTypeInputs = new ArrayList<>();
        thickness = new TextField();
        modes = new ComboBox<>();
    }

    public void setPrompts(String label, String... prompts) {
        coordsPrompts.clear();
        coordsInputs.clear();

        for (String text : prompts) {
            Label new_label = new Label(text + ":");
            new_label.setStyle("-fx-text-fill: #D3DAE4;");
            coordsPrompts.add(new_label);
            coordsInputs.add(new TextField());
        }
        this.label.setText(label + ":");

        update();
    }

    public void setPrompts(String label, Map<String, Double> prompts, double scale) {
        coordsPrompts.clear();
        coordsInputs.clear();

        for (Map.Entry<String, Double> entry : prompts.entrySet()) {
            Label new_label = new Label(entry.getKey() + ":");
            new_label.setStyle("-fx-text-fill: #D3DAE4;");
            coordsPrompts.add(new_label);
            String value = String.format("%.1f", entry.getValue() / scale);
            coordsInputs.add(new TextField(value.replace(",", ".")));
        }
        this.label.setText(label + ":");

        update();
    }

    public void setLabel(String label) {
        this.label.setText(label + ":");
    }

    public ComboBox<LineType> setLineType(double thickness, List<Double> dashSpace) {
        lineTypePrompts.clear();
        lineTypeInputs.clear();

        if (lineTypes == null)
            lineTypes = new ComboBox<>();

        this.thickness = new TextField(String.valueOf(thickness));
        Label thic_label = new Label("Толщина линии:");
        thic_label.setStyle("-fx-text-fill: #D3DAE4;");
        lineTypePrompts.add(thic_label);
        lineTypeInputs.add(this.thickness);
        toolBar.getItems().add(this.thickness);

        if (!dashSpace.isEmpty()) {
            Label dash_label = new Label("Длина штриха:");
            dash_label.setStyle("-fx-text-fill: #D3DAE4;");
            lineTypePrompts.add(dash_label);
            Label space_label = new Label("Длина пробела:");
            space_label.setStyle("-fx-text-fill: #D3DAE4;");
            lineTypePrompts.add(space_label);
            lineTypeInputs.add(new TextField(String.valueOf(dashSpace.get(0))));
            lineTypeInputs.add(new TextField(String.valueOf(dashSpace.get(1))));
        }

        update();

        return lineTypes;
    }

    public List<TextField> getCoordsInputs() {
        return coordsInputs;
    }

    public List<Double> readInputValues() {
        List<Double> values = new ArrayList<>();
        for (TextField field : coordsInputs) {
            String text = field.getText();
            values.add(Double.parseDouble(text.replace(",", ".")));
        }
        return values;
    }

    private void update() {
        toolBar.getItems().clear();

        if (!modes.getItems().isEmpty())
            toolBar.getItems().add(modes);
        toolBar.getItems().add(label);

        setStyle(coordsPrompts, coordsInputs);

        if (lineTypes != null) {
            toolBar.getItems().add(lineTypes);
            setStyle(lineTypePrompts, lineTypeInputs);
        }

        if (applyBtn != null)
            toolBar.getItems().add(applyBtn);
    }

    private void setStyle(List<Label> prompts, List<TextField> inputs) {
        for (int i = 0; i < prompts.size(); i++) {
            HBox hBox = new HBox(prompts.get(i), inputs.get(i));
            toolBar.getItems().add(hBox);

            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER_LEFT);
            inputs.get(i).setMaxWidth(50);
        }
    }

    public ComboBox<DrawingMode> addModeSelection() {
        toolBar.getItems().addFirst(modes);
        return modes;
    }

    public Button addApplyButton() {
        applyBtn = new Button("Применить");
        toolBar.getItems().addLast(applyBtn);
        return applyBtn;
    }

    public double getThicknessValue() {
        return Double.parseDouble(thickness.getText().replace(",", "."));
    }

    public List<Double> getDashSpace() {
        if (lineTypeInputs.size() < 2)
            return List.of();

        double dash = Double.parseDouble(lineTypeInputs.get(lineTypeInputs.size() - 2).getText().replace(",", "."));
        double space = Double.parseDouble(lineTypeInputs.getLast().getText().replace(",", "."));
        return List.of(dash, space);
    }
}