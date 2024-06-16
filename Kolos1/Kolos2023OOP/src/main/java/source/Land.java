package source;

import java.util.ArrayList;
import java.util.List;

public class Land extends Polygon{

    private List<City> cities = new ArrayList<City>();

    public void addCity(City c){
        try {
            cities.add(c);
            c.setPort(this);
            centerOnLand(c);
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public Land(List<Point> points) {
        super(points);
    }

    private void centerOnLand(City c) throws RuntimeException {
        if(!this.inside(c.center)) {
            throw new RuntimeException(c.getName());
        }
    }
}
