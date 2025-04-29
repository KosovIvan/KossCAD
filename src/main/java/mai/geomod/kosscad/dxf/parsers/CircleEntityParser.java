package mai.geomod.kosscad.dxf.parsers;

import javafx.scene.layout.Pane;
import mai.geomod.kosscad.figures.MyCircle;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.Map;

public class CircleEntityParser extends AbstractEntityParser {
    @Override
    public boolean canParse(String entityType) {
        return "CIRCLE".equals(entityType);
    }

    @Override
    public void parse(Map<Integer, String> entityData, WorkSpace space) {
        double centerX = getDoubleValue(entityData, 10, "0");
        double centerY = getDoubleValue(entityData, 20, "0");
        double radius = getDoubleValue(entityData, 40, "0");
        String lineType = getStringValue(entityData, 6, "CONTINUOUS");
        double thickness = getDoubleValue(entityData, 370, "100") / 100.0;

        double scale = space.getScale();
        MyPoint center = transformPoint(centerX, centerY, space);
        double sceneRadius = transformDistance(radius, scale);

        MyCircle circle = new MyCircle(center, sceneRadius);
        applyProperties(circle, lineType, thickness, scale);
        space.getWorkSpace().getChildren().add(circle);
    }
} 