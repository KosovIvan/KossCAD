package mai.geomod.kosscad.util;

import mai.geomod.kosscad.figures.MyPoint;

public class Math {
    public static double PointsDistance(MyPoint point1, MyPoint point2) {
        double x = java.lang.Math.abs(point1.getX() - point2.getX());
        double y = java.lang.Math.abs(point1.getY() - point2.getY());
        return java.lang.Math.sqrt(x * x + y * y);
    }

    public static MyPoint getMiddlePoint(MyPoint p1, MyPoint p2) {
        double x = p1.getX() + 0.5 * (p2.getX() - p1.getX());
        double y = p1.getY() + 0.5 * (p2.getY() - p1.getY());
        return new MyPoint(x, y);
    }
}
