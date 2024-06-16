package Decorators;
import java.util.Locale;
import Figures.*;
public class StrokeShapeDecorator extends ShapeDecorator {
    String color;
    double width;

        public StrokeShapeDecorator(Shape decoratedShape, String color, double width) {
            super(decoratedShape);
            this.color = color;
            this.width = width;
        }

        public String toSvg(String mod) {
            return decoratedShape.toSvg(String.format(Locale.ENGLISH, "stroke=\"%s\" stroke-width=\"%f\" %s ", color, width, mod));
        }
}
