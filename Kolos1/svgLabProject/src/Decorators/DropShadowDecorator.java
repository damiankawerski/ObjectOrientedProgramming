package Decorators;
import Figures.Shape;
import OutputClasses.SvgScene;
import java.util.Locale;

public class DropShadowDecorator extends ShapeDecorator {

    private static final int index = ShapeDecorator.getFreeIndex();

    public static int getIndex() {
        return index;
    }

    public DropShadowDecorator(Shape decoratedShape) {
        super(decoratedShape);
        buildFilter();
    }

    public String toSvg(String mod) {
        return decoratedShape.toSvg(String.format(Locale.ENGLISH, "filter=\"url(#f%d)\" %s", index, mod));
    }

    private void buildFilter() {
        String shadow = String.format(Locale.ENGLISH, "<defs>\n" +
                "\t<filter id=\"f%d\" x=\"-100%%\" y=\"-100%%\" width=\"300%%\" height=\"300%%\">\n" +
                "\t\t<feOffset result=\"offOut\" in=\"SourceAlpha\" dx=\"5\" dy=\"5\" />\n" +
                "\t\t<feGaussianBlur result=\"blurOut\" in=\"offOut\" stdDeviation=\"5\" />\n" +
                "\t\t<feBlend in=\"SourceGraphic\" in2=\"blurOut\" mode=\"normal\" />\n" +
                "\t</filter>\n" +
                "</defs>", index);
        SvgScene.getInstance().addDef(shadow);
    }
}
