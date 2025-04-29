package mai.geomod.kosscad.dxf.parsers;

import javafx.scene.layout.Pane;
import mai.geomod.kosscad.figures.MyArc;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.Map;

public class ArcEntityParser extends AbstractEntityParser {
    @Override
    public boolean canParse(String entityType) {
        return "ARC".equals(entityType);
    }

    @Override
    public void parse(Map<Integer, String> entityData, WorkSpace space) {
        double centerX = getDoubleValue(entityData, 10, "0");
        double centerY = getDoubleValue(entityData, 20, "0");
        double radius = getDoubleValue(entityData, 40, "0");
        double startAngle = getDoubleValue(entityData, 50, "0");
        double endAngle = getDoubleValue(entityData, 51, "0");
        String lineType = getStringValue(entityData, 6, "CONTINUOUS");
        double thickness = getDoubleValue(entityData, 370, "100") / 100.0;

        double scale = space.getScale();
        MyPoint center = transformPoint(centerX, centerY, space);
        double sceneRadius = transformDistance(radius, scale);

        double startRadians = Math.toRadians(startAngle);
        double endRadians = Math.toRadians(endAngle);
        double midRadians = startRadians + (endRadians - startRadians) / 2;
        if (startRadians > endRadians)
            midRadians = startRadians + (endRadians + 2 * Math.PI - startRadians) / 2;

        MyPoint point1 = new MyPoint(
                center.getX() + sceneRadius * Math.cos(startRadians),
                center.getY() - sceneRadius * Math.sin(startRadians)
        );
        MyPoint point2 = new MyPoint(
                center.getX() + sceneRadius * Math.cos(midRadians),
                center.getY() - sceneRadius * Math.sin(midRadians)
        );
        MyPoint point3 = new MyPoint(
                center.getX() + sceneRadius * Math.cos(endRadians),
                center.getY() - sceneRadius * Math.sin(endRadians)
        );

        MyArc arc = new MyArc(point1, point2, point3);
        applyProperties(arc, lineType, thickness, scale);
        space.getWorkSpace().getChildren().add(arc);
    }
} 