import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Product {
    private String name;
    private static List<Product> products = new ArrayList<>();

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static Product getProducts (String prefix) throws AmbigiousProductException {
        List<Product> result = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (Product product : products) {
            if (product.name.startsWith(prefix)) {
                result.add(product);
                names.add(product.name);
            }
        }
        if (result.size() == 0) {
            throw new IndexOutOfBoundsException();
        } else if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new AmbigiousProductException(names);
        }

    }

    public abstract double getPrice(int year, int month);

    public static void clearProducts() {
        products.clear();
    }

    public static void addProduct(Function<Path, Product> fromCsv, Path path) {
        File dir = new File(path.toString());
        File[] fileList = dir.listFiles();
        assert fileList != null;
        for (File file : fileList) {
            if (file.isFile()) {
                products.add(fromCsv.apply(file.toPath()));
            }
        }
    }
}
