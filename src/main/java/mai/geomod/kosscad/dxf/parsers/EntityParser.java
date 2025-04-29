package mai.geomod.kosscad.dxf.parsers;

import javafx.scene.layout.Pane;
import mai.geomod.kosscad.util.WorkSpace;

import java.util.Map;

public interface EntityParser {
    boolean canParse(String entityType);
    void parse(Map<Integer, String> entityData, WorkSpace space);
}