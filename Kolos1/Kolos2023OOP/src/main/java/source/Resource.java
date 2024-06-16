package source;

public class Resource {
    public enum Type {
        Coal,
        Wood,
        Fish
    }

    public Type type;
    public Point localization;

    public Resource(Type type, Point localization) {
        this.type = type;
        this.localization = localization;
    }
}
