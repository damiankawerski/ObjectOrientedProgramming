package Main;
import Figures.*;
import OutputClasses.SvgScene;
import Decorators.*;

public class Main {
    public static void main(String[] args) {
        Vec2 p1 = new Vec2(100, 100);
        Vec2 p2 = new Vec2(300, 300);
        Segment s = new Segment(p1, p2);
        Shape myShape = new GradientFillShapeDecorator.Builder()
                .addShape(Polygon.Square(s))
                .addColor("red")
                .addOffset(0.5)
                .addColor("blue")
                .addOffset(1)
                .build();
        SvgScene scene = new SvgScene();
        scene.addShape(myShape);
        SvgScene.saveHTML("output.html");
    }
}


