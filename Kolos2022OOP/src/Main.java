import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final String nonFoodPath = "/home/damian/GitHub/Kolos2021OOp/data/nonfood";
    public static final String foodPath = "/home/damian/GitHub/Kolos2021OOp/data/food";
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String lineName = scanner.nextLine();
//        String path = foodPath + "/" + lineName + ".csv";
//        FoodProduct p = FoodProduct.fromCsv(Paths.get(path));
//        System.out.println(p.getName());
//        System.out.println(p.getPrice(2010, 1));
//        System.out.println(p.getPrice(2010, 1, "LUBELSKIE"));
        Product.addProduct(FoodProduct::fromCsv, Paths.get(foodPath));
        Product.addProduct(NonFoodProduct::fromCsv, Paths.get(nonFoodPath));
//        try {
//            System.out.println(Product.getProducts("J").getName());
//        } catch (AmbigiousProductException e) {
//            e.printStackTrace();
//        }
        Cart cart = new Cart();
        try {
            cart.addProduct(Product.getProducts("Jaja"), 5);
            cart.addProduct(Product.getProducts("Bu"), 2);
            cart.addProduct(Product.getProducts("Garni"), 1);
        } catch (AmbigiousProductException e) {
            e.printStackTrace();
        }
        for(Product product : cart.products) {
            System.out.println(product.getName());
        }
        double price = cart.getPrice(2010, 2);
        System.out.println(price);
        double inflation = cart.getInflation(2010, 2, 2012, 2);
        System.out.println(inflation);
    }
}