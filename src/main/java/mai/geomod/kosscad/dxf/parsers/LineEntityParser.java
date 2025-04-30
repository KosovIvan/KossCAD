package mai.geomod.kosscad.dxf.parsers;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import mai.geomod.kosscad.figures.MyLine;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.Map;

public class LineEntityParser extends AbstractEntityParser {
    @Override
    public boolean canParse(String entityType) {
        return "LINE".equals(entityType);
    }

    @Override
    public void parse(Map<Integer, String> entityData, WorkSpace space) {
        double x1 = getDoubleValue(entityData, 10, "0");
        double y1 = getDoubleValue(entityData, 20, "0");
        double x2 = getDoubleValue(entityData, 11, "0");
        double y2 = getDoubleValue(entityData, 21, "0");
        String lineType = getStringValue(entityData, 6, "CONTINUOUS");
        double thickness = getDoubleValue(entityData, 370, "100") / 100.0;

        double scale = space.getScale();
        MyPoint point1 = transformPoint(x1, y1, space);
        MyPoint point2 = transformPoint(x2, y2, space);

        MyLine line = new MyLine(point1, point2);
        applyProperties(line, lineType, thickness, scale);
        line.getStartPoint().Draw(space);
        line.getEndPoint().Draw(space);
        line.Draw(space);
        space.addObjects(new Group[] {line.getStartPoint(), line.getEndPoint()});
        space.addObject(line);
    }
}