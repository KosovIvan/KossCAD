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

    public static double[] getCenterAndRadius(MyPoint point1, MyPoint point2, MyPoint point3) {
        double[][] a = {
                {2 * point1.getX(), 2 * point1.getY(), 1},
                {2 * point2.getX(), 2 * point2.getY(), 1},
                {2 * point3.getX(), 2 * point3.getY(), 1}
        };
        double[] b = {
                point1.getX() * point1.getX() + point1.getY() * point1.getY(),
                point2.getX() * point2.getX() + point2.getY() * point2.getY(),
                point3.getX() * point3.getX() + point3.getY() * point3.getY()
        };
        double[] x = getSolution(a, b);

        double centerX = x[0];
        double centerY = x[1];
        double radius = java.lang.Math.sqrt(x[0] * x[0] + x[1] * x[1] + x[2]);

        return new double[]{centerX, centerY, radius};
    }

    public static double[] getSolution(double[][] a, double[] b) {
        int n = b.length;
        double[] x = new double[n];

        for (int i = 0; i < n; i++) {
            for (int k = i + 1; k < n; k++) {
                double coef = a[k][i] / a[i][i];
                for (int j = i; j < n; j++)
                    a[k][j] = a[k][j] - coef * a[i][j];
                b[k] = b[k] - coef * b[i];
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++)
                sum += a[i][j] * x[j];
            x[i] = (b[i] - sum) / a[i][i];
        }

        return x;
    }
}
