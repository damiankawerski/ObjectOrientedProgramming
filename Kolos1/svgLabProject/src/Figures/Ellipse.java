package Figures;

import java.util.Locale;

public class Ellipse implements Shape {
    private final Vec2 center;
    private final double radiusX;
    private final double radiusY;

    public Ellipse(Vec2 center, double radiusX, double radiusY) {
        this.center = center;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    @Override
    public String toSvg(String mod) {
        return String.format(Locale.ENGLISH, "<ellipse cx=\"%f\" cy=\"%f\" rx=\"%f\" ry=\"%f\" %s", center.x, center.y, radiusX, radiusY, mod);
    }
}

