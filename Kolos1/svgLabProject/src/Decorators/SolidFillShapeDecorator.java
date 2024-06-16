package Decorators;
import Figures.*;
import java.util.Locale;
public class SolidFillShapeDecorator extends ShapeDecorator {
    String color;

    public SolidFillShapeDecorator(Shape decoratedShape, String color) {
        super(decoratedShape);
        this.color = color;
    }

    public String toSvg(String mod) {
        return decoratedShape.toSvg(String.format(Locale.ENGLISH, "fill=\"%s\" %s ", color, mod));
    }
}
