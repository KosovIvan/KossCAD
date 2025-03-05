package mai.geomod.kosscad.configurators;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import mai.geomod.kosscad.figures.MyCircle;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.figures.MyPolygon;
import mai.geomod.kosscad.modes.DrawingMode;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.List;

public class PolygonConfigurator extends BaseConfigurator{
    private int n = 4;

    public PolygonConfigurator(WorkSpace space) {
        super(space);
        modes.getItems().addAll(DrawingMode.INSCRIBED_IN_CIRCLE, DrawingMode.CIRCUMSCRIBED_AROUND_CIRCLE);
        modes.setValue(DrawingMode.INSCRIBED_IN_CIRCLE);
    }

    @Override
    public BaseConfigurator Activate() {
        inputBuilder.setPrompts("Укажите координаты центральной точки", "X", "Y", "Количество сторон");
        inputBuilder.getCoordsInputs().getLast().setText(String.valueOf(n));
        switch (modes.getValue()) {
            case INSCRIBED_IN_CIRCLE:
                draw(DrawingMode.INSCRIBED_IN_CIRCLE);
                break;
            case CIRCUMSCRIBED_AROUND_CIRCLE:
                draw(DrawingMode.CIRCUMSCRIBED_AROUND_CIRCLE);
                break;
        }
        return this;
    }

    private void draw(DrawingMode mode) {
        space.getWorkSpace().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (!points.isEmpty()) {
                    setFirstActionPrompts();
                    space.removeObject(points.getFirst());
                    space.getWorkSpace().getChildren().remove(points.getFirst());
                    MyPoint point = new MyPoint(e.getX(), e.getY());
                    MyPolygon poly = new MyPolygon(points.getFirst(), point, n, mode);
                    space.addObject(poly);
                    poly.Draw(space);
                    points.clear();
                } else {
                    MyPoint point = new MyPoint(e.getX(), e.getY());
                    points.add(point);
                    space.addObject(point);
                    point.Draw(space);
                    setSecondActionPrompts();
                }
            }
        });

        space.getInputTool().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                List<Double> inputs = inputBuilder.readInputValues();
                if (!points.isEmpty()) {
                    double radius = inputs.getFirst() * space.getScale();
                    setFirstActionPrompts();
                    space.removeObject(points.getFirst());
                    space.getWorkSpace().getChildren().remove(points.getFirst());
                    MyPolygon poly = new MyPolygon(points.getFirst(), radius, n, mode);
                    space.addObject(poly);
                    poly.Draw(space);
                    points.clear();
                } else {
                    double x = (space.getCoords().getPoint().getX() + inputs.get(0) * space.getScale());
                    double y = (space.getCoords().getPoint().getY() - inputs.get(1) * space.getScale());
                    MyPoint point = new MyPoint(x, y);
                    points.add(point);
                    space.addObject(point);
                    point.Draw(space);
                    setSecondActionPrompts();
                }
            }
        });
    }

    protected void setFirstActionPrompts() {
        if (inputBuilder.getCoordsInputs().getLast().getText() != null)
            n = Integer.parseInt(inputBuilder.getCoordsInputs().getLast().getText());
        inputBuilder.setPrompts("Укажите координаты центральной точки", "X", "Y", "Количество сторон");
        inputBuilder.getCoordsInputs().getLast().setText(String.valueOf(n));
    }

    protected void setSecondActionPrompts() {
        if (inputBuilder.getCoordsInputs().getLast().getText() != null)
            n = Integer.parseInt(inputBuilder.getCoordsInputs().getLast().getText());
        inputBuilder.setPrompts("Укажите радиус", "R", "Количество сторон");
        inputBuilder.getCoordsInputs().getLast().setText(String.valueOf(n));
    }
}
