package mai.geomod.kosscad.dxf.parsers;

import javafx.scene.paint.Color;
import mai.geomod.kosscad.figures.Figure;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.Map;

public abstract class AbstractEntityParser implements EntityParser {
    
    protected MyPoint transformPoint(double x, double y, WorkSpace space) {
        MyPoint coordsCenter = space.getCoords().getPoint();
        double scale = space.getScale();
        
        double sceneX = coordsCenter.getX() + x * scale;
        double sceneY = coordsCenter.getY() - y * scale;
        
        return new MyPoint(sceneX, sceneY);
    }
    
    protected double transformDistance(double distance, double scale) {
        return distance * scale;
    }
    
    protected void applyProperties(Figure figure, String dxfLineType, double thickness, double scale) {
        LineType lineType = getLineType(dxfLineType);
        figure.setLineType(lineType, scale);
        figure.setThickness(thickness);
        figure.setColor(Color.WHITE);
    }
    
    protected LineType getLineType(String lineType) {
        return switch (lineType) {
            case "DASHED" -> LineType.DASHED;
            case "DASHDOT" -> LineType.DASH_DOT;
            case "DIVIDE" -> LineType.DASH_DOT_DOT;
            default -> LineType.SOLID;
        };
    }
    
    protected double getDoubleValue(Map<Integer, String> entityData, int code, String defaultValue) {
        return Double.parseDouble(entityData.getOrDefault(code, defaultValue));
    }
    
    protected String getStringValue(Map<Integer, String> entityData, int code, String defaultValue) {
        return entityData.getOrDefault(code, defaultValue);
    }
} 