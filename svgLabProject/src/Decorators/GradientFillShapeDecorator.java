package Decorators;
import Figures.*;
import OutputClasses.SvgScene;

import java.util.ArrayList;
import java.util.Locale;

public class GradientFillShapeDecorator extends ShapeDecorator {

    private ArrayList<Double> offsets = new ArrayList<Double>();
    private ArrayList<String> colors = new ArrayList<String>();

    private static final int index = ShapeDecorator.getFreeIndex();

    public static int getIndex() {
        return index;
    }

    public GradientFillShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    private void buildGradient() {
        StringBuilder gradient = new StringBuilder(String.format(Locale.ENGLISH, "<defs>\n" +
                "\t<linearGradient id=\"g%d\" >\n", index));
        for(int i = 0; i < offsets.size(); i++) {
            gradient.append(String.format(Locale.ENGLISH, "\t\t<stop offset=\"%f\" style=\"stop-color:%s\" />\n", offsets.get(i), colors.get(i)));
        }
        gradient.append("\t</linearGradient>\n" +
                "</defs>");
        SvgScene.getInstance().addDef(gradient.toString());
    }

    public String toSvg(String mod) {
        return decoratedShape.toSvg(String.format(Locale.ENGLISH, "fill=\"url(#g%d)\" %s", index, mod));
    }

    public static class Builder {
        private Shape shape;
        private ArrayList<Double> offsets = new ArrayList<Double>();
        private ArrayList<String> colors = new ArrayList<String>();

        public Builder addShape(Shape shape) {
            this.shape = shape;
            return this;
        }

        public Builder addOffset(double offset) {
            offsets.add(offset);
            return this;
        }

        public Builder addColor(String color) {
            colors.add(color);
            return this;
        }

        public GradientFillShapeDecorator build() {
            GradientFillShapeDecorator decorator = new GradientFillShapeDecorator(shape);
            decorator.offsets = offsets;
            decorator.colors = colors;
            decorator.buildGradient();
            return decorator;
        }
    }
}
