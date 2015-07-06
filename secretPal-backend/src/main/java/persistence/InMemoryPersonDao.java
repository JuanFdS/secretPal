package persistence;

import model.Person;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class InMemoryPersonDao implements AbstractRepository<Person> {

    private static List<Person> persons = Arrays.asList(new Person("Roman", "Rizzi", LocalDate.of(1994,05,21)));

    @Override
    public List<Person> retrieveAll() {
        return this.persons;
    }

    @Override
    public void save(Person element) {
    }
}
