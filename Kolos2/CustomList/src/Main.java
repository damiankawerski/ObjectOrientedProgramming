import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
        Collection<Integer> collection1 = Arrays.asList(1, 2, 3, 4, 5);
        Collection<Integer> collection2 = Arrays.asList(6, 7, 8, 9, 10);

        // Porównywanie sum dwóch kolekcji za pomocą compareSums
        Comparator<Integer> comparator = CustomList.compareSums(collection1, collection2);

        // Sprawdzanie, która suma jest większa
        int result = comparator.compare(0, 0); // Przyjmuje dowolne wartości, ponieważ porównujemy sumy

        if (result < 0) {
            System.out.println("Suma pierwszej kolekcji jest mniejsza od sumy drugiej kolekcji.");
        } else if (result > 0) {
            System.out.println("Suma pierwszej kolekcji jest większa od sumy drugiej kolekcji.");
        } else {
            System.out.println("Sumy obu kolekcji są równe.");
        }
    }
}