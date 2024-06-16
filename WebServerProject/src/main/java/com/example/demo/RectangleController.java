package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
public class RectangleController {
    private List<Rectangle> rectangles = new ArrayList<>();

    @PostMapping("addRectangle")
    public int addRectangle(@RequestBody Rectangle rectangle) {
        rectangles.add(rectangle);
        return rectangles.size();
    }

    @GetMapping("rectangle")
    public Rectangle getRectangle() {
        return new Rectangle(20, 10, 100, 213, "red");
    }

    @GetMapping("rectangles")
    public List<Rectangle> getRectangles() {
        return this.rectangles;
    }

    @GetMapping("GET/{index}")
    public Rectangle GET(@PathVariable Long index) {
        if(index < 0 || index > rectangles.size()) {
            throw new IndexOutOfBoundsException();
        }
        return rectangles.get(index.intValue());
    }

    @PutMapping("PUT")
    public int putRectangle(@RequestBody Rectangle rectangle, int index) {
        rectangles.set(index, rectangle);
        return rectangles.size();
    }

    @DeleteMapping("DELETE")
    public int deleteRectangle(@RequestBody int index) {
        rectangles.remove(index);
        return rectangles.size();
    }

    public static Point[] getVertices(Rectangle rectangle) {
        Point[] vertices = new Point[4];

        int halfWidth = rectangle.getWidth() / 2;
        int halfHeight = rectangle.getHeight() / 2;

        vertices[0] = new Point((rectangle.getX() - halfWidth), (rectangle.getY() - halfHeight)); // Lewy górny
        vertices[1] = new Point((rectangle.getX() + halfWidth), (rectangle.getY() - halfHeight)); // Prawy górny
        vertices[2] = new Point((rectangle.getX() + halfWidth), (rectangle.getY() + halfHeight)); // Prawy dolny
        vertices[3] = new Point((rectangle.getX() - halfWidth), (rectangle.getY() + halfHeight)); // Lewy dolny

        return vertices;
    }

    private static String toSvg(Rectangle rectangle) {
        String color = rectangle.getColor();
        Point[] vertices = getVertices(rectangle);
        StringBuilder values = new StringBuilder();
        for(Point p : vertices) {
            values.append(p.x).append(",").append(p.y).append(" ");
        }
        return String.format(Locale.ENGLISH, "<polygon points=\"%s\" style=\"fill:%s;stroke:black;stroke-width:3\" />", values.toString(), color);
    }

    @GetMapping("rectanglesSVG")
    public String toSVG() {
        StringBuilder result = new StringBuilder();
        result.append("<svg width=\"800\" height=\"600\" xmlns=\"http://www.w3.org/2000/svg\">");
        for (Rectangle rectangle : rectangles) {
            result.append(toSvg(rectangle));
        }
        result.append("</svg>");
        return result.toString();
    }

}