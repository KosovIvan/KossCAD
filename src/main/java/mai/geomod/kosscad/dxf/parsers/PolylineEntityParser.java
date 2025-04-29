package mai.geomod.kosscad.dxf.parsers;

import javafx.scene.layout.Pane;
import mai.geomod.kosscad.dxf.parsers.AbstractEntityParser;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.figures.MyPolygon;
import mai.geomod.kosscad.figures.MyRect;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.List;
import java.util.Map;

public class PolylineEntityParser extends AbstractEntityParser {
    @Override
    public boolean canParse(String entityType) {
        return "POLYLINE".equals(entityType);
    }

    @Override
    public void parse(Map<Integer, String> entityData, WorkSpace space) {
    }

    public static void createPolyline(List<MyPoint> points, String dxfLineType, double thickness, WorkSpace space) {
        double scale = space.getScale();
        AbstractEntityParser parser = new AbstractEntityParser() {
            @Override
            public boolean canParse(String entityType) {
                return false;
            }

            @Override
            public void parse(Map<Integer, String> entityData, WorkSpace space) {
            }
        };
        
        if (isRectangle(points)) {
            MyRect rect = new MyRect(points);
            parser.applyProperties(rect, dxfLineType, thickness, scale);
            space.getWorkSpace().getChildren().add(rect);
        } else {
            MyPoint center = calculateCenter(points);
            MyPolygon polygon = new MyPolygon(center, points);
            parser.applyProperties(polygon, dxfLineType, thickness, scale);
            space.getWorkSpace().getChildren().add(polygon);
        }
    }

    private static boolean isRectangle(List<MyPoint> points) {
        double[] xs = points.stream().mapToDouble(MyPoint::getX).distinct().toArray();
        double[] ys = points.stream().mapToDouble(MyPoint::getY).distinct().toArray();
        return xs.length == 2 && ys.length == 2;
    }

    private static MyPoint calculateCenter(List<MyPoint> points) {
        double sumX = 0, sumY = 0;
        for (MyPoint p : points) {
            sumX += p.getX();
            sumY += p.getY();
        }
        return new MyPoint(sumX / points.size(), sumY / points.size());
    }
}