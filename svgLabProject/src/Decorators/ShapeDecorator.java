package Decorators;
import Figures.Shape;

import java.util.Locale;

public class ShapeDecorator implements Shape {
    protected Shape decoratedShape;
    String modifier;
    private static Integer freeIndex = null;

    public static Integer getFreeIndex() {
        if(freeIndex == null) {
            freeIndex = 0;
            return freeIndex;
        }
        freeIndex++;
        return freeIndex;
    }

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
        modifier = "";
    }

    public ShapeDecorator(Shape decoratedShape, String modifier) {
        this.decoratedShape = decoratedShape;
        this.modifier = modifier;
    }

    public String toSvg(String mod) {
        return decoratedShape.toSvg(String.format(Locale.ENGLISH, "%s %s", modifier, mod));
    }
}
