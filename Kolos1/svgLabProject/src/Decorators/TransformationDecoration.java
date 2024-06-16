package Decorators;
import Figures.*;
import java.util.Locale;
public class TransformationDecoration extends ShapeDecorator{
    //****************************
    private boolean translate = false;
    private Vec2 translateVector;
    //****************************
    private boolean rotate = false;
    private double rotateAngle;
    private Vec2 rotateCenter;
    //****************************
    private boolean scale = false;
    private Vec2 scaleVector;
    //****************************

    public TransformationDecoration(Shape decoratedShape) {
        super(decoratedShape);
    }

    public String toSvg(String mod) {
        StringBuilder transform = new StringBuilder();
        if (translate) {
            transform.append(String.format(Locale.ENGLISH,"translate(%f, %f) ", translateVector.x, translateVector.y));
        }
        if(rotate) {
            transform.append(String.format(Locale.ENGLISH,"rotate(%f, %f, %f) ", rotateAngle, rotateCenter.x, rotateCenter.y));
        }
        if(scale) {
            transform.append(String.format(Locale.ENGLISH,"scale(%f, %f) ", scaleVector.x, scaleVector.y));
        }
        return decoratedShape.toSvg(String.format("transform=\"%s\" %s", transform.toString(), mod));
    }
    public static class Builder {
        private boolean translate = false;
        private Vec2 translateVector;
        private boolean rotate = false;
        private double rotateAngle;
        private Vec2 rotateCenter;
        private boolean scale = false;
        private Vec2 scaleVector;
        Shape shape;

        public  Builder setShape(Shape shape) {
            this.shape = shape;
            return this;
        }
        public Builder setTranslateVector(Vec2 translateVector) {
            this.translate = true;
            this.translateVector = translateVector;
            return this;
        }
        public Builder setRotateAngleAndCenter(double rotateAngle, Vec2 rotateCenter) {
            this.rotate = true;
            this.rotateAngle = rotateAngle;
            this.rotateCenter = rotateCenter;
            return this;
        }
        public Builder setScaleVector(Vec2 scaleVector) {
            this.scale = true;
            this.scaleVector = scaleVector;
            return this;
        }

        public TransformationDecoration build() {
            TransformationDecoration transformationDecoration = new TransformationDecoration(shape);
            transformationDecoration.translate = this.translate;
            transformationDecoration.translateVector = this.translateVector;
            transformationDecoration.rotate = this.rotate;
            transformationDecoration.rotateAngle = this.rotateAngle;
            transformationDecoration.rotateCenter = this.rotateCenter;
            transformationDecoration.scale = this.scale;
            transformationDecoration.scaleVector = this.scaleVector;
            return transformationDecoration;
        }
    }
}
