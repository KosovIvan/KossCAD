package mai.geomod.kosscad.figures;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ModifiableFigure extends Figure{
    public abstract void setValuesFromInputs(List<Double> values, MyPoint center);
    public abstract Map<String, Double> getValuesForOutput(MyPoint center);

    public Map<String, Double> getCenterRadiusForOutput(MyPoint coordsCenter, MyPoint figureCenter, double radius) {
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("Центр [X]", figureCenter.getX() - coordsCenter.getX());
        map.put("Центр [Y]", coordsCenter.getY() - figureCenter.getY());
        map.put("Радиус", radius);
        return map;
    }
}
