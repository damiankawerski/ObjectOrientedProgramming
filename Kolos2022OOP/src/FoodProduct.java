import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class FoodProduct extends Product {
    Map<String, Double[]> prices = new HashMap<>();

    private FoodProduct(String name, Map<String, Double[]> prices) {
        super(name);
        this.prices = prices;
    }

    public static FoodProduct fromCsv(Path path) {
        String name;
        Map<String, Double[]> prices = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
            name = br.readLine();
            String line = br.readLine();
            while((line = br.readLine()) != null) {
                String[] tokens = line.split(";", 2);
                Double[] price = Arrays.stream(tokens[1].split(";")).map(value -> value.replaceAll(",", ".")).map(Double::parseDouble).toArray(Double[]::new);
                prices.put(tokens[0], price);
            }
            return new FoodProduct(name, prices);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public double getPrice(int year, int month, String province) {
        int numberOfMonths = (2022 - 2010)*12 + 3; //rok końcowy - rok początkowy + 3 miesiące do marca
        int lookedDateIndex = (year - 2010)*12 - 1 + month;
        if((lookedDateIndex >= numberOfMonths || lookedDateIndex < 0) || month > 12) {
            throw new IndexOutOfBoundsException();
        }
        if(!prices.containsKey(province)) {
            throw new IndexOutOfBoundsException();
        }
        Double[] value = prices.get(province);
        System.out.println(lookedDateIndex);
        return value[lookedDateIndex];
    }

    @Override
    public double getPrice(int year, int month) {
        int numberOfMonths = (2022 - 2010)*12 + 3; //rok końcowy - rok początkowy + 3 miesiące do marca
        int lookedDateIndex = (year - 2010)*12 - 1 + month;
        if((lookedDateIndex >= numberOfMonths || lookedDateIndex < 0) || month > 12) {
            throw new IndexOutOfBoundsException();
        }
        int counter = 0;
        double sum = 0;
        for(Double[] arr : prices.values()) {
           sum += arr[lookedDateIndex];
           counter++;
        }
        return sum/counter;
    }
}
