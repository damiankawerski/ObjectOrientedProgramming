package OutputClasses;

import Figures.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SvgScene {
    private static ArrayList<Shape> shapes = new ArrayList<Shape>();
    private static SvgScene instance = null;

    public String[] defs = new String[0];

    public void addDef(String def) {
        defs = Arrays.copyOf(defs, defs.length + 1);
        defs[defs.length - 1] = def;
    }

    public static SvgScene getInstance() {
        if(instance == null) {
            instance = new SvgScene();
        }
        return instance;
    }

    public SvgScene addShape(Shape shape) {
        shapes.add(shape);
        return this;
    }

    private static String svgImageBuilder() {
        StringBuilder imageBuilder = new StringBuilder("<svg height=\"1000\" width=\"1000\">");
        for(int i = 0 ; i < instance.defs.length; i++) {
            imageBuilder.append(instance.defs[i]);
        }
        for(Shape shapeLooper : shapes) {
            imageBuilder.append(shapeLooper.toSvg(""));
        }
        imageBuilder.append("</svg>");
        return imageBuilder.toString();
    }

    public static void saveHTML(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(svgImageBuilder());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
