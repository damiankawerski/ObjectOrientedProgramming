package Person;

import Exceptions.AmbiguousPersonException;
import Exceptions.NegativeLifespanException;
import Exceptions.ParentingAgeException;
import Exceptions.UndefinedParentException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Person implements Serializable {
    public final String name;
    public final LocalDate birth, death;
    private List<Person> parents = new ArrayList<Person>();

    public Person(String name, LocalDate birth, LocalDate death) {
        this.name = name;
        this.birth = birth;
        this.death = death;
    }

    public static Person fromCsvLine(String line) {
        String[] fields = line.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String birthDate = fields[1];
        String deathDate = fields[2];
        LocalDate birth = null, death = null;
        if(!birthDate.isEmpty()) {
            birth = LocalDate.parse(birthDate, formatter);
        }
        if(!deathDate.isEmpty()) {
            death = LocalDate.parse(deathDate, formatter);
        }

        return new Person(fields[0], birth, death);
    }

    public static List<Person> fromCsv(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        List<Person> result = new ArrayList<Person>();
        List<PersonWithParentNames> resultWithParents = new ArrayList<>();
        String line;
        br.readLine();

        try {
            while((line = br.readLine()) != null) {
                PersonWithParentNames personWithNames = PersonWithParentNames.fromCsvLine(line);
                personWithNames.person.validateAmbiguity(result);
                personWithNames.person.validateLifespan();
                resultWithParents.add(personWithNames);
                result.add(personWithNames.person);
            }
            PersonWithParentNames.linkRelatives(resultWithParents);

            try {
                for(Person person : result) {
                    person.validateParentingAge();
                }
            }
            catch(ParentingAgeException e) {
                Scanner scanner  = new Scanner(System.in);
                System.out.println(e.getMessage());
                System.out.println("Please confirm [Y/N]");
                String response = scanner.nextLine();
                if(!response.equals("Y") && !response.equals("y")) {
                    result.remove(e.person);
                }
            }

        } catch (NegativeLifespanException | AmbiguousPersonException | UndefinedParentException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public void addParent(Person person) {
        parents.add(person);
    }

    private void validateLifespan() throws NegativeLifespanException {
        if(this.death != null && this.birth.isAfter(death)) {
            throw new NegativeLifespanException(this);
        }
    }

    private void validateAmbiguity(List<Person> personSoFar) throws AmbiguousPersonException {
        for(Person person : personSoFar) {
            if(person.name.equals(this.name)) {
                throw new AmbiguousPersonException(this.name);
            }
        }
    }

    private void validateParentingAge() throws ParentingAgeException {
        for(Person parent : parents) {
            if (birth.isBefore(parent.birth.minusYears(15)) || ( parent.death != null && birth.isAfter(parent.death))) {
                throw new ParentingAgeException(this, parent);
            }
        }
    }

    public static void toBinary(List<Person> people, String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(people);
        }
    }

    public static List<Person> fromBinary(String path) throws IOException, ClassNotFoundException {
        try(FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (List<Person>) ois.readObject();
        }
    }

    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birth=" + birth +
                ", death=" + death +
                ", parents=" + parents +
                '}';
    }

    public String toUML(Function<String, String> postProcess) {
        StringBuilder objects = new StringBuilder();
        StringBuilder relations = new StringBuilder();

        Function<String, String> replaceSpaces = str -> str.replaceAll(" ", "");

        objects.append("object " + replaceSpaces.apply(name) + "\n");
        for(Person parent : parents) {
            objects.append("object " + replaceSpaces.apply(parent.name) + "\n");
            relations.append(replaceSpaces.apply(parent.name) + " <-- " + replaceSpaces.apply(name) + "\n");
        }

        return String.format("@startuml\n%s%s@enduml", objects, relations);
    }


    public static String toUML(List<Person> people, Predicate<Person> condtition, Function<String, String> postProcess) {
        String result = "@startuml\n%s\n%s\n@enduml";
        Function<String, String> replaceSpaces = str -> str.replaceAll(" ", "");
        Function<String, String> objectLine = str -> String.format("object \"%s\" as %s", str, replaceSpaces.apply(str));
        Function<String, String> afterPostProcess  = objectLine.andThen(postProcess);

        Map<Boolean, List<Person>> groupedPeople = people.stream()
                .collect(Collectors.partitioningBy(condtition));

        Set<String> objects = groupedPeople.get(true).stream()
                .map(person -> person.name)
                .map(afterPostProcess)
                .collect(Collectors.toSet());
        objects.addAll(groupedPeople.get(false).stream()
                .map(person -> person.name)
                .map(objectLine)
                .collect(Collectors.toSet()));

        Set<String> relations = people.stream()
                .flatMap(person -> person.parents.stream()
                        .map(parent -> replaceSpaces.apply(parent.name) + "<--" + replaceSpaces.apply(person.name)))
                .collect(Collectors.toSet());

        String objectResult = String.join("\n", objects);
        String relationResult = String.join("\n", relations);

        return String.format(result, objectResult, relationResult);
    }

    public static List<Person> filterByString(List<Person> people, String subString) {
        List<Person> result = people
                .stream()
                .filter(name -> name.name.contains(subString))
                .collect(Collectors.toList());
        return result;
    }

    public static List<Person> sortByBirth(List<Person> people) {
        List<Person> result = people
                .stream()
                .sorted((person1, person2) -> person1.birth.compareTo(person2.birth))
                .collect(Collectors.toList());
        return result;
    }

    public static List<Person> deadPeople(List<Person> people) {
        List<Person> result = people
                .stream()
                .filter(dead -> dead.death != null)
                .sorted((person1, person2) -> Long.compare(person2.birth.until(person2.death, ChronoUnit.DAYS), person1.birth.until(person1.death, ChronoUnit.DAYS)))
                .collect(Collectors.toList());
        return result;
    }

    public static Person oldestPerson(List<Person> people) {
        List<Person> result = people
                .stream()
                .filter(alive -> alive.death == null)
                .sorted((person1, person2) -> person2.birth.compareTo(person1.birth))
                .collect(Collectors.toList());
        return result.getLast();
    }
}


