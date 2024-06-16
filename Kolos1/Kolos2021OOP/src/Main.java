import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static final String deaths = "C:\\UserFiles\\GIT\\Kolos2021OOP\\assets\\deaths.csv";
    public static final String confirmed = "C:\\UserFiles\\GIT\\Kolos2021OOP\\assets\\confirmed_cases.csv";
    public static final String output = "C:\\UserFiles\\GIT\\Kolos2021OOP\\assets\\output.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();

        try {
            Country.setFiles(deaths, confirmed);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        Country.saveToDataFile(Paths.get(output));
    }
}