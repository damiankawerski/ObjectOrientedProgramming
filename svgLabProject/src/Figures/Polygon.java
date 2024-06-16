package Figures;

import java.util.Arrays;
import java.util.Locale;

public class Polygon implements Shape {

    private Vec2[] vertices;

    public Polygon(int amountOfVertices) {
        vertices = new Vec2[amountOfVertices];
    }

    public Polygon(Vec2[] vertices) {
        this.vertices = vertices;
    }

    public Polygon(Polygon other) {
        vertices = Arrays.copyOf(other.getVertices(), other.getVertices().length);
    }

    public void setPoint(int index, Vec2 point) {
        vertices[index] = point;
    }

    public void setPoints(Vec2[] points) {
        vertices = points;
    }


    public String toSvg(String mod) {
        StringBuilder points = new StringBuilder();
        for (Vec2 vertex : vertices) {
            points.append(vertex.x).append(",").append(vertex.y).append(" ");
        }
        return String.format(Locale.ENGLISH, "<polygon points=\"%s\" %s />", points.toString(), mod);
    }


    public Vec2[] getVertices() {
        return vertices;
    }

    public static Polygon Square(Segment diag) {
        Vec2 middleOfDiag = new Vec2((diag.getPoint2().x + diag.getPoint1().x)/ 2, (diag.getPoint2().y + diag.getPoint1().y )/ 2);
        Segment[] perpendiculars = Segment.normalToSegment(middleOfDiag, diag);
        Vec2 secondDiagStart = new Vec2((perpendiculars[0].getPoint2().x + perpendiculars[0].getPoint1().x) / 2, (perpendiculars[0].getPoint2().y + perpendiculars[0].getPoint1().y) / 2);
        Vec2 secondDiagEnd = new Vec2((perpendiculars[1].getPoint2().x + perpendiculars[1].getPoint1().x) / 2, (perpendiculars[1].getPoint2().y + perpendiculars[1].getPoint1().y) / 2);
        Segment secondDiag = new Segment(secondDiagStart, secondDiagEnd);
        Vec2 results[] = {
            diag.getPoint1(),
                secondDiagStart,
            diag.getPoint2(),
                secondDiagEnd
        };
        return new Polygon(results);
    }
}

