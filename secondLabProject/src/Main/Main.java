package Main;
import Person.Person;
import UML.PlantUMLRunner;

import java.io.IOException;
import java.util.List;

public class Main {
    public static final String path = "family.csv";
    public static final String binPath = "family.bin";
    public static final String jarPath = "C:\\UserFiles\\GIT\\secondLabProject\\plantuml-1.2024.3.jar";
    public static final String umlOutputPath = "C:\\UserFiles\\GIT\\secondLabProject\\UMLOutput";

    public static void main(String[] args) {
        List<Person> people;
        PlantUMLRunner.setJarPath(jarPath);
        List<Person> test;
        try {
            people = Person.fromCsv(path);
            for(Person p : people) {
                //System.out.println(p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String data = people.get(0).toUML();
        PlantUMLRunner.generate(data, umlOutputPath, "uml");
    }
}