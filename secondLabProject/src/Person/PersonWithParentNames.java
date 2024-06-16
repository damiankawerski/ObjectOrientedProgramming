package Person;

import Exceptions.UndefinedParentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonWithParentNames {
    public final Person person;
    public final String[] parentNames;

    public PersonWithParentNames(Person person, String[] parentNames) {
        this.person = person;
        this.parentNames = parentNames;
    }

    public static PersonWithParentNames fromCsvLine(String line) {
        Person person = Person.fromCsvLine(line);
        String[] fields = line.split(",", -1);
        String[] parents = new String[2];
        for(int i = 3 ; i < 5 ; i++) {
            if (!fields[i].isEmpty()) {
                parents[i - 3] = fields[i];
            }
        }
        return new PersonWithParentNames(person, parents);
    }

    public static void linkRelatives(List<PersonWithParentNames> people) throws UndefinedParentException {
        Map<String, PersonWithParentNames> peopleMap = new HashMap<>();
        for(PersonWithParentNames personWithNames : people) {
            peopleMap.put(personWithNames.person.name, personWithNames);
        }

        for(PersonWithParentNames personWithNames : people) {
            Person person = personWithNames.person;
            for(int i = 0 ; i < 2 ; i++) {
                String parentName = personWithNames.parentNames[i];
                if(parentName != null) {
                    if (peopleMap.containsKey(parentName)) {
                        person.addParent(peopleMap.get(parentName).person);
                    } else
                        throw new UndefinedParentException(person, parentName);

                }
            }
        }
    }
}
