package mai.geomod.kosscad.dxf;

import javafx.scene.layout.Pane;
import mai.geomod.kosscad.dxf.parsers.*;
import mai.geomod.kosscad.figures.MyPoint;
import mai.geomod.kosscad.util.WorkSpace;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FigureReader {
    private final WorkSpace workspace;
    private final MyPoint coordsCenter;
    private final double scale;
    private BufferedReader reader;
    private final java.util.List<EntityParser> parsers;
    private String currentLine;

    public FigureReader(WorkSpace space) {
        workspace = space;
        this.scale = workspace.getScale();
        this.coordsCenter = space.getCoords().getPoint();
        this.parsers = java.util.List.of(
                new LineEntityParser(),
                new CircleEntityParser(),
                new ArcEntityParser(),
                new PolylineEntityParser()
        );
    }

    public void read(BufferedReader reader) throws IOException {
        this.reader = reader;
        findEntitiesSection();
    }

    private String readLine() throws IOException {
        if (currentLine != null) {
            String line = currentLine;
            currentLine = null;
            return line;
        }
        return reader.readLine();
    }

    private void pushBackLine(String line) {
        currentLine = line;
    }

    private void findEntitiesSection() throws IOException {
        String line;
        while ((line = readLine()) != null) {
            line = line.trim();
            if (line.equals("SECTION")) {
                String sectionCode = readLine().trim();
                String sectionName = readLine().trim();
                if (sectionName.equals("ENTITIES")) {
                    readFigures();
                    return;
                }
            }
        }
    }

    private Map<Integer, String> readEntityData() throws IOException {
        Map<Integer, String> entityData = new HashMap<>();

        while (true) {
            String codeLine = readLine();
            if (codeLine == null) break;
            codeLine = codeLine.trim();

            if (codeLine.equals("0")) {
                pushBackLine(codeLine);
                break;
            }

            String valueLine = readLine();
            if (valueLine == null) break;
            valueLine = valueLine.trim();

            try {
                entityData.put(Integer.parseInt(codeLine), valueLine);
            } catch (NumberFormatException ignored) {}
        }

        return entityData;
    }

    private void readFigures() throws IOException {
        String line;
        while ((line = readLine()) != null) {
            line = line.trim();
            if (line.equals("0")) {
                String entityType = readLine().trim();

                if ("ENDSEC".equals(entityType) || "EOF".equals(entityType))
                    break;

                Map<Integer, String> entityData = new HashMap<>();
                entityData.put(0, entityType);

                if ("POLYLINE".equals(entityType)) {
                    readPolyline();
                    continue;
                }

                entityData.putAll(readEntityData());

                for (EntityParser parser : parsers) {
                    if (parser.canParse(entityType)) {
                        parser.parse(entityData, workspace);
                        break;
                    }
                }
            }
        }
    }

    private void readPolyline() throws IOException {
        java.util.List<MyPoint> points = new java.util.ArrayList<>();
        String lineType = "CONTINUOUS";
        double thickness = 1.0;

        while (true) {
            String line = readLine();
            if (line == null) break;
            line = line.trim();

            if (line.equals("0")) {
                String type = readLine().trim();

                if (type.equals("VERTEX")) {
                    double x = 0, y = 0;
                    Map<Integer, String> vertexData = readEntityData();

                    try {
                        if (vertexData.containsKey(10)) x = Double.parseDouble(vertexData.get(10));
                        if (vertexData.containsKey(20)) y = Double.parseDouble(vertexData.get(20));
                    } catch (NumberFormatException ignored) {}

                    double sceneX = coordsCenter.getX() + x * scale;
                    double sceneY = coordsCenter.getY() - y * scale;
                    points.add(new MyPoint(sceneX, sceneY));
                } else if (type.equals("SEQEND")) {
                    Map<Integer, String> seqEndData = readEntityData();

                    try {
                        if (seqEndData.containsKey(6)) lineType = seqEndData.get(6);
                        if (seqEndData.containsKey(370)) thickness = Double.parseDouble(seqEndData.get(370)) / 100.0;
                    } catch (NumberFormatException ignored) {}

                    break;
                }
            }
        }

        if (!points.isEmpty())
            PolylineEntityParser.createPolyline(points, lineType, thickness, workspace);
    }
}
