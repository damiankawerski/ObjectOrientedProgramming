package source;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class PolygonTest {

    @Test
    void testPointInsidePolygon() {
        // Tworzenie punktów wielokąta
        List<Point> polygonPoints = new ArrayList<>();
        polygonPoints.add(new Point(0, 0));
        polygonPoints.add(new Point(0, 4));
        polygonPoints.add(new Point(4, 4));
        polygonPoints.add(new Point(4, 0));
        Polygon polygon = new Polygon(polygonPoints);

        // Sprawdzenie punktu znajdującego się wewnątrz wielokąta
        Point insidePoint = new Point(2, 2);
        assertTrue(polygon.inside(insidePoint));
    }

    @Test
    void testPointOutsidePolygon() {
        // Tworzenie punktów wielokąta
        List<Point> polygonPoints = new ArrayList<>();
        polygonPoints.add(new Point(0, 0));
        polygonPoints.add(new Point(0, 4));
        polygonPoints.add(new Point(4, 4));
        polygonPoints.add(new Point(4, 0));
        Polygon polygon = new Polygon(polygonPoints);

        // Sprawdzenie punktu znajdującego się na zewnątrz wielokąta
        Point outsidePoint = new Point(6, 2);
        assertFalse(polygon.inside(outsidePoint));
    }

    @Test
    void testPointOnEdgeOfPolygon() {
        // Tworzenie punktów wielokąta
        List<Point> polygonPoints = new ArrayList<>();
        polygonPoints.add(new Point(0, 0));
        polygonPoints.add(new Point(0, 4));
        polygonPoints.add(new Point(4, 4));
        polygonPoints.add(new Point(4, 0));
        Polygon polygon = new Polygon(polygonPoints);

        // Sprawdzenie punktu znajdującego się na krawędzi wielokąta
        Point edgePoint = new Point(2, 4);
        assertFalse(polygon.inside(edgePoint));
    }
}
