package mai.geomod.kosscad.modes;

public enum DrawingMode {
    BY_2_POINTS("2 точки"),
    BY_3_POINTS("3 точки"),
    SIDES("Стороны"),
    RADIUS("Радиус"),
    ANGLE_LENGTH("Угол и длина"),
    INSCRIBED_IN_CIRCLE("Вписанный"),
    CIRCUMSCRIBED_AROUND_CIRCLE("Описанный"),
    CHORD("Хорда"),
    QUAD_SPLINE("Квадратичный"),
    BEZIER("Безье");

    DrawingMode(String name) {
        this.name = name;
    }

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
