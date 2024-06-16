package source;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class City extends Polygon{
    public final Point center;
    private String name;
    private boolean isPort = false;
    Set<Resource.Type> resources;

    public City(Point center, String name, double length) {
        super(calculateCityCorners(center, length));
        this.center = center;
        this.name = name;
    }

    public Set<Resource.Type> getResources() {
        return resources;
    }

    public String getName() {
        return name;
    }

    private static List<Point> calculateCityCorners(Point center, double length) {
        List<Point> corners = new ArrayList<Point>();
        double halfLength = length / 2;
        corners.add(new Point(center.x - halfLength, center.y - halfLength));
        corners.add(new Point(center.x + halfLength, center.y - halfLength));
        corners.add(new Point(center.x - halfLength, center.y + halfLength));
        corners.add(new Point(center.x + halfLength, center.y + halfLength));
        return corners;
    }

    public void setPort(Land land) {
        for(Point point : land.getPoints()) {
            if(!land.inside(point)) {
                isPort = true;
            }
        }
    }

    void addResourcesInRange(List<Resource> resources, double range) {
        for(Resource resource : resources) {
            if(range <= Math.hypot(this.center.x - resource.localization.x, this.center.y - resource.localization.y)) {
                if(!resource.type.equals(Resource.Type.Fish)) {
                    this.resources.add(resource.type);
                } else {
                    if(this.isPort) {
                        this.resources.add(resource.type);
                    }
                }
            }
        }
    }
}
