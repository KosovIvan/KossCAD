package mai.geomod.kosscad.dxf;

import mai.geomod.kosscad.figures.*;
import mai.geomod.kosscad.modes.LineType;
import mai.geomod.kosscad.util.WorkSpace;

import java.io.BufferedWriter;
import java.io.IOException;


public class FigureWriter {
    private final WorkSpace workspace;
    private final MyPoint coordsCenter;
    private final double scale;
    private BufferedWriter bufferedWriter;

    public FigureWriter(WorkSpace space) {
        workspace = space;
        scale = workspace.getScale();
        coordsCenter = workspace.getCoords().getPoint();
    }

    public void write(BufferedWriter bufferedWriter) throws IOException {
        this.bufferedWriter = bufferedWriter;
        bufferedWriter.write("  0\r\n");
        bufferedWriter.write("SECTION\r\n");
        bufferedWriter.write("  2\r\n");
        bufferedWriter.write("ENTITIES\r\n");

        workspace.getWorkSpace().getChildren().stream()
                .filter(elem -> elem instanceof Figure)
                .map(node -> (Figure) node)
                .forEach(this::writeFigure);

        bufferedWriter.write("  0\r\n");
        bufferedWriter.write("ENDSEC\r\n");
        bufferedWriter.write("  0\r\n");
        bufferedWriter.write("EOF\r\n");
    }

    private void writeFigure(Figure figure) {
        try {
            bufferedWriter.write("  0\r\n");
            bufferedWriter.write(figure.getDXFName() + "\r\n");

            // Слой
            bufferedWriter.write("  8\r\n");
            bufferedWriter.write("0\r\n");

            if (figure instanceof MyLine line)
                writeLineEntity(line);

            else if (figure instanceof MyCircle circle)
                writeCircleEntity(circle);

            else if (figure instanceof MyArc arc)
                writeArcEntity(arc);

            else if (figure instanceof MyRect rectangle)
                writePolylineEntity(rectangle.getPoints());

            else if (figure instanceof MyPolygon polygon)
                writePolylineEntity(polygon.getPoints());

            // Тип линии
            bufferedWriter.write("  6\r\n");
            bufferedWriter.write(getDxfLineType(figure.getLineType()) + "\r\n");

            // Толщина линии
            bufferedWriter.write("  370\r\n");
            bufferedWriter.write(figure.getThickness() * 100 + "\r\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeLineEntity(MyLine line) throws IOException {
        writePoint(line.getStartPoint());
        writePoint2(line.getEndPoint());
    }

    private void writeCircleEntity(MyCircle circle) throws IOException {
        writePoint(circle.getCenter());
        writeRadius(circle.getRadius());
    }

    private void writeArcEntity(MyArc arc) throws IOException {
        writePoint(arc.getCenter());
        writeRadius(arc.getRadius());

        double startAngle = normalizeAngle(arc.getStartAngle());
        double endAngle = normalizeAngle(arc.getStartAngle() + arc.getLength());

        if (arc.getLength() < 0) {
            double temp = startAngle;
            startAngle = endAngle;
            endAngle = temp;
        }

        bufferedWriter.write("  50\r\n");
        bufferedWriter.write(startAngle + "\r\n");

        bufferedWriter.write("  51\r\n");
        bufferedWriter.write(endAngle + "\r\n");
    }

    private double normalizeAngle(double angle) {
        angle = angle % 360;
        if (angle < 0)
            angle += 360;
        return angle;
    }

    private void writePolylineEntity(MyPoint[] points) throws IOException {
        // Флаг closed
        bufferedWriter.write("  70\r\n");
        bufferedWriter.write("1\r\n");

        for (MyPoint point : points) {
            bufferedWriter.write("  0\r\n");
            bufferedWriter.write("VERTEX\r\n");
            bufferedWriter.write("  8\r\n");
            bufferedWriter.write("0\r\n");

            writePoint(point);
        }
        bufferedWriter.write("  0\r\n");
        bufferedWriter.write("SEQEND\r\n");
    }

    private void writePoint(MyPoint point) throws IOException {
        double x = (point.getX() - coordsCenter.getX()) / scale;
        double y = (coordsCenter.getY() - point.getY()) / scale;

        bufferedWriter.write("  10\r\n");
        bufferedWriter.write(x + "\r\n");
        bufferedWriter.write("  20\r\n");
        bufferedWriter.write(y + "\r\n");
        bufferedWriter.write("  30\r\n");
        bufferedWriter.write("0.0\r\n");
    }

    private void writePoint2(MyPoint point) throws IOException {
        double x = (point.getX() - coordsCenter.getX()) / scale;
        double y = (coordsCenter.getY() - point.getY()) / scale;

        bufferedWriter.write("  11\r\n");
        bufferedWriter.write(x + "\r\n");
        bufferedWriter.write("  21\r\n");
        bufferedWriter.write(y + "\r\n");
        bufferedWriter.write("  31\r\n");
        bufferedWriter.write("0.0\r\n");
    }

    private void writeRadius(double radius) throws IOException {
        bufferedWriter.write("  40\r\n");
        bufferedWriter.write((radius / scale) + "\r\n");
    }

    private String getDxfLineType(LineType lineType) {
        return switch (lineType) {
            case DASHED -> "DASHED";
            case DASH_DOT -> "DASHDOT";
            case DASH_DOT_DOT -> "DIVIDE";
            default -> "CONTINUOUS";
        };
    }
}
