package Figures;

import java.util.Locale;

public class Segment implements Shape {
    private Vec2 point1;
    private Vec2 point2;

    public Vec2 getPoint1() {
        return point1;
    }

    public Vec2 getPoint2() {
        return point2;
    }

    public Segment(Vec2 point1, Vec2 point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public double getDistance() {
        return Math.sqrt(Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2));
    }

    public String toSvg(String mod) {
        return String.format(Locale.ENGLISH,"<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" \n" +
                "  style=\"stroke:red;stroke-width:2\" />", point1.x, point1.y, point2.x, point2.y);
    }
    static Segment[] normalToSegment(Vec2 p, Segment segment) {
        double normalA = -1 / ((segment.point2.y - segment.point1.y) / (segment.point2.x - segment.point1.x));
        double normalB = p.y - (normalA * p.x);
        double tempX = p.x + (segment.getDistance() / Math.sqrt(1 + Math.pow(normalA, 2)));
        double tempY = tempX * normalA + normalB;
        Vec2 firstPoint = new Vec2(tempX, tempY);
        tempX = p.x - (segment.getDistance() / Math.sqrt(1 + Math.pow(normalA, 2)));
        tempY = tempX * normalA + normalB;
        Vec2 secondPoint = new Vec2(tempX, tempY);
        return new Segment[]{
                new Segment(p, firstPoint),
                new Segment(p, secondPoint)
        };
    }
}