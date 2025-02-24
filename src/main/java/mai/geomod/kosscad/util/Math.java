package mai.geomod.kosscad.util;

import mai.geomod.kosscad.figures.MyPoint;

public class Math {
    public static double PointsDistance(MyPoint point1, MyPoint point2) {
        double x = java.lang.Math.abs(point1.getX() - point2.getX());
        double y = java.lang.Math.abs(point1.getY() - point2.getY());
        return java.lang.Math.sqrt(x * x + y * y);
    }
}
