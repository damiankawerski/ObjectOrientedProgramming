import java.util.ArrayList;
import java.util.List;

public class Cart {
    List<Product> products = new ArrayList<Product>();

    public void addProduct(Product product, int amount) {
        for(int i = 0 ; i < amount ; i++) {
            products.add(product);
        }
    }

    public double getPrice(int year, int month) {
        double price = 0;
        for(Product product : products) {
            price += product.getPrice(year, month);
        }
        return price;
    }

    public double getInflation(int year1, int month1, int year2, int month2) {
        if(year1 * 12 + month1 > year2 * 12 + month2) {
            return -1;
        }
        double price1 = getPrice(year1, month1);
        double price2 = getPrice(year2, month2);
        int months = (year2 * 12 + month2) - (year1 * 12 + month1);
        return (price2 - price1) / price1 * 100 / months * 12;
    }
}
